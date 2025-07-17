package com.adila.galicia.challenge.service.impl;

import com.adila.galicia.challenge.entity.User;
import com.adila.galicia.challenge.exception.UserNotFoundException;
import com.adila.galicia.challenge.repository.UserRepository;
import com.adila.galicia.challenge.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUser(Long id) {
    try {
      return this.userRepository.findById(id)
          .orElseThrow(() -> new UserNotFoundException("Usuario con ID " + id + " no encontrado"));
    } catch (UserNotFoundException unfe) {
      log.error("Usuario " + id + " no encontrado", unfe);
      throw unfe;
    } catch (Exception e) {
      log.error("Error inesperado al buscar el usuario con ID " + id, e);
      throw new RuntimeException("Error inesperado al procesar la solicitud", e);
    }

  }
}
