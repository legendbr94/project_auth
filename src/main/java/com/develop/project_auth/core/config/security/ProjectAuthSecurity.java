package com.develop.project_auth.core.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class ProjectAuthSecurity {

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public boolean isAuthenticated() {
    return getAuthentication().isAuthenticated();
  }

  public Long getUserId() {
    Jwt jwt = (Jwt) getAuthentication().getPrincipal();
    return jwt.getClaim("user_id");
  }

  public boolean userAuthenticatedEqualTo(Long id) {
    return getUserId() != null && id != null
        && getUserId().equals(id);
  }

  public boolean hasAuthority(String authorityName) {
    return getAuthentication().getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals(authorityName));
  }

  public boolean haveWriteScope() {
    return hasAuthority("SCOPE_WRITE");
  }

  public boolean haveReadScope() {
    return hasAuthority("SCOPE_READ");
  }

}
