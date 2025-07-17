package com.adila.galicia.challenge.service.impl;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.exception.InternalServerException;
import com.adila.galicia.challenge.repository.CartItemRepository;
import com.adila.galicia.challenge.service.CartItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService {

  private final CartItemRepository cartItemRepository;

  public CartItemServiceImpl(CartItemRepository cartItemRepository) {
    this.cartItemRepository = cartItemRepository;
  }

  @Override
  public CartItem create(Cart cart, Product product, int numberOfItems) {
    CartItem cartItem = null;
    try {
      cartItem = cartItemRepository.
          save(CartItem.builder().cart(cart).product(product).numberOfItems(numberOfItems).build());
    } catch (Exception e) {
      log.error("Error al guardar entidad CartItem", e);
      throw new InternalServerException("Error inesperado al agregar item al carrito", e);
    }
    return cartItem;
  }
}
