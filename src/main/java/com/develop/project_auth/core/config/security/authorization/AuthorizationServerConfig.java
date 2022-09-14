package com.develop.project_auth.core.config.security.authorization;

import com.develop.project_auth.core.config.security.SecurityProperties;
import com.develop.project_auth.domain.model.User;
import com.develop.project_auth.domain.repository.UserRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AuthorizationServerConfig {

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    return http.formLogin(Customizer.withDefaults()).build();
  }

  @Bean
  public ProviderSettings providerSettings(SecurityProperties properties) {
    return ProviderSettings.builder()
        .issuer(properties.getProviderUrl())
        .build();
  }

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcOperations jdbcOperations) {
    return new JdbcRegisteredClientRepository(jdbcOperations);
  }
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Bean
  public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations,
      RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationService(
        jdbcOperations,
        registeredClientRepository
    );
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception {
    char[] keyStorePass = properties.getPassword().toCharArray();
    String keypairAlias = properties.getKeypairAlias();

    Resource jksLocation = properties.getJksLocation();
    InputStream inputStream = jksLocation.getInputStream();
    KeyStore keyStore = KeyStore.getInstance("JKS");
    keyStore.load(inputStream, keyStorePass);

    RSAKey rsaKey = RSAKey.load(keyStore, keypairAlias, keyStorePass);

    return new ImmutableJWKSet<>(new JWKSet(rsaKey));
  }

  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(
      UserRepository userRepository) {
    return context -> {
      Authentication authentication = context.getPrincipal();
      if (authentication
          .getPrincipal() instanceof org.springframework.security.core.userdetails.User userdetails) {

        User user = userRepository.findByEmail(userdetails.getUsername()).orElseThrow();

        Set<String> authorities = new HashSet<>();
        for (GrantedAuthority authority : userdetails.getAuthorities()) {
          authorities.add(authority.getAuthority());
        }

        context.getClaims().claim("user_id", user.getId().toString());
        context.getClaims().claim("authorities", authorities);
      }
    };
  }

}
