package com.adila.galicia.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CartStatusException extends RuntimeException {

  public CartStatusException(String message) {
    super(message);
  }
}