package com.adila.galicia.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CartItemConflictException extends RuntimeException {

  public CartItemConflictException(String message) {
    super(message);
  }
}
