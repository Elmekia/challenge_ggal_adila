package com.adila.galicia.challenge.service.impl;

import com.adila.galicia.challenge.entity.User;
import com.adila.galicia.challenge.repository.UserRepository;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    return new org.springframework.security.core.userdetails.User(
        user.getName(),
        user.getPassword(),
        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
    );
  }
}
