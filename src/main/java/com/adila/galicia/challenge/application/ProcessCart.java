package com.adila.galicia.challenge.application;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.User;
import com.adila.galicia.challenge.exception.CartStatusException;
import com.adila.galicia.challenge.exception.UserForbidenException;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.utils.CartStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessCart {

  private final CartService cartService;
  private final CartAsyncProcessor cartAsyncProcessor;

  public String execute(Long cartId, String userName) {
    Cart cart = this.cartService.getCartById(cartId);
    this.validateUser(cart.getUser(), userName);
    if (cart.getStatus() != CartStatus.OPEN) {
      log.warn("El carrito {} no se puede procesar en su estado actual", cartId);
      throw new CartStatusException("El carrito no se puede procesar en su estado actual.");
    }
    cart.setStatus(CartStatus.PROCESSING);
    this.cartService.save(cart);
    this.cartAsyncProcessor.execute(cartId);
    return "Su carrito se está procesando.";
  }

  private void validateUser(User user, String autenticatedUserName) {
    if (!user.getName().equals(autenticatedUserName)) {
      throw new UserForbidenException("El usuario no coincide con el autenticado");
    }
  }

}
