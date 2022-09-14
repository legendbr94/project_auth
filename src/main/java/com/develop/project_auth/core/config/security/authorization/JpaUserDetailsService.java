package com.develop.project_auth.core.config.security.authorization;

import com.develop.project_auth.domain.model.User;
import com.develop.project_auth.domain.repository.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JpaUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Usuário não encontrado com e-mail informado"));

    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(), getAuthorities(
        user));
  }

  private Collection<GrantedAuthority> getAuthorities(User user) {
    return user.getGroups().stream()
        .flatMap(group -> group.getPermissions().stream())
        .map(permission -> new SimpleGrantedAuthority(permission.getName().toUpperCase()))
        .collect(Collectors.toSet());
  }

}
