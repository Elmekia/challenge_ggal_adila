package com.adila.galicia.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserForbidenException extends RuntimeException {

  public UserForbidenException(String message) {
    super(message);
  }
}
