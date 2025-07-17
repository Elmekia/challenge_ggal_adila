package com.adila.galicia.challenge.application;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.exception.CartItemNotFoundException;
import com.adila.galicia.challenge.exception.CartStatusException;
import com.adila.galicia.challenge.service.CartItemService;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.utils.CartStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteProductFromCart {

  private final CartItemService cartItemService;
  private final CartService cartService;

  @Transactional
  public void execute(Long idCart, Long idProduct) {

    Cart cart = this.cartService.getCartById(idCart);
    if (cart.getStatus() != CartStatus.OPEN) {
      throw new CartStatusException("El carrito no se puede procesar en su estado actual.");
    }

    boolean removed = cart.getItems().removeIf(item -> item.getProduct().getId().equals(idProduct));

    if (!removed) {
      throw new CartItemNotFoundException("El producto no está en el carrito");
    }
  }


}
