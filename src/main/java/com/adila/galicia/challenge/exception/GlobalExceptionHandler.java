package com.adila.galicia.challenge.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "Usuario no encontrado",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(CartNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCartNotFound(CartNotFoundException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "Carrito no encontrado",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "Producto no encontrado",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(CartItemNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCartItemNotFound(CartItemNotFoundException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        "El producto no esta en el carrito",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(CartItemConflictException.class)
  public ResponseEntity<ErrorResponse> handleCartItemConflict(CartItemConflictException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.CONFLICT.value(),
        "El producto existe en el carrito",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(StockUnavailableException.class)
  public ResponseEntity<ErrorResponse> handleStockUnavailable(StockUnavailableException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.CONFLICT.value(),
        "Error de stock",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(UserForbidenException.class)
  public ResponseEntity<ErrorResponse> handleUserForbiden(UserForbidenException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.FORBIDDEN.value(),
        "Error de autenticacion",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<ErrorResponse> handleInternalServer(InternalServerException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Error inesperado",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(CartStatusException.class)
  public ResponseEntity<ErrorResponse> handleCartStatus(CartStatusException ex,
      HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.CONFLICT.value(),
        "Error en el estado del carrito",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> {
          String jsonField = FIELD_NAME_MAP.getOrDefault(error.getField(), error.getField());
          return jsonField + ": " + error.getDefaultMessage();
        })
        .toList();

    return new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
        "Errores de validación", errors.toString(), request.getRequestURI());

  }


  private static final Map<String, String> FIELD_NAME_MAP = Map.of(
      "numberOfItems", "cantidad",
      "id", "id"
  );

}
