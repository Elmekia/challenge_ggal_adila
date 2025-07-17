package com.adila.galicia.challenge.service.impl;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.exception.CartNotFoundException;
import com.adila.galicia.challenge.exception.InternalServerException;
import com.adila.galicia.challenge.repository.CartRepository;
import com.adila.galicia.challenge.service.CartService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;

  public CartServiceImpl(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Override
  public Cart save(Cart cart) {
    Cart newCart = null;
    try {
      newCart = this.cartRepository.save(cart);
    } catch (Exception e) {
      log.error("Error inesperado al crear el carrito", e);
      throw new InternalServerException("Error inesperado al crear el carrito", e);

    }
    return newCart;
  }

  @Override
  public Cart getCartById(Long id) {
    Cart cart = null;
    try {
      cart = this.cartRepository.findById(id)
          .orElseThrow(() -> new CartNotFoundException("Carrito con ID " + id + " no encontrado"));
    } catch (CartNotFoundException cnfe) {
      throw cnfe;
    } catch (Exception e) {
      log.error("Error inesperado al buscar el carrito de id " + id, e);
      throw new InternalServerException("Error inesperado al buscar el carrito", e);
    }
    return cart;
  }

  @Override
  public List<Cart> getCartByUser(Long id) {
    try {
      return this.cartRepository.findByUserId(id)
          .orElseThrow(() -> new CartNotFoundException(
              "No se encontraron carritos para el usuario con ID " + id));
    } catch (CartNotFoundException cnfe) {
      log.error("Error al obtener carritos para el usuario con ID " + id, cnfe);
      throw cnfe;
    } catch (Exception e) {
      log.error("Error inesperado al obtener carritos para el usuario con ID " + id, e);
      throw new InternalServerException("Error inesperado al obtener carritos", e);
    }
  }

  @Override
  public Cart getCartByIdWithItems(Long cartId) {
    return this.cartRepository.findByIdWithItems(cartId).orElse(null);
  }

}
