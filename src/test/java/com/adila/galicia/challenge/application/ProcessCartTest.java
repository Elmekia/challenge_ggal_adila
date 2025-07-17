package com.adila.galicia.challenge.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.User;
import com.adila.galicia.challenge.exception.CartStatusException;
import com.adila.galicia.challenge.exception.UserForbidenException;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.utils.CartStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ProcessCartTest {

  private CartService cartService;
  private CartAsyncProcessor cartAsyncProcessor;
  private ProcessCart processCart;

  @BeforeEach
  void setUp() {
    cartService = mock(CartService.class);
    cartAsyncProcessor = mock(CartAsyncProcessor.class);
    processCart = new ProcessCart(cartService, cartAsyncProcessor);
  }

  @Test
  void processCartOK() {
    Long cartId = 1L;
    String userName = "john";

    User user = User.builder().name(userName).build();
    Cart cart = Cart.builder().id(cartId).user(user).status(CartStatus.OPEN).build();

    when(cartService.getCartById(cartId)).thenReturn(cart);
    when(cartService.save(any(Cart.class))).thenReturn(cart);

    String response = processCart.execute(cartId, userName);

    assertEquals("Su carrito se está procesando.", response);
    assertEquals(CartStatus.PROCESSING, cart.getStatus());

    verify(cartService).save(cart);
    verify(cartAsyncProcessor).execute(cartId);
  }

  @Test
  void processNotOkCartIsNotOpen() {
    Long cartId = 1L;
    String userName = "john";

    User user = User.builder().name(userName).build();
    Cart cart = Cart.builder().id(cartId).user(user).status(CartStatus.CANCELLED).build();

    when(cartService.getCartById(cartId)).thenReturn(cart);

    assertThrows(CartStatusException.class, () -> processCart.execute(cartId, userName));
    verify(cartService, never()).save(any());
    verify(cartAsyncProcessor, never()).execute(any());
  }

  @Test
  void processNotOkUserDoesNotMatch() {
    Long cartId = 1L;
    String userName = "john";
    String actualUserName = "mary";

    User user = User.builder().name(actualUserName).build();
    Cart cart = Cart.builder().id(cartId).user(user).status(CartStatus.OPEN).build();

    when(cartService.getCartById(cartId)).thenReturn(cart);

    assertThrows(UserForbidenException.class, () -> processCart.execute(cartId, userName));
    verify(cartService, never()).save(any());
    verify(cartAsyncProcessor, never()).execute(any());
  }
}