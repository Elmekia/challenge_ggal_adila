package com.adila.galicia.challenge.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.adila.galicia.challenge.dto.response.CartResponse;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.User;
import com.adila.galicia.challenge.exception.UserForbidenException;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.service.UserService;
import com.adila.galicia.challenge.utils.CartStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateCartTest {

  @Mock
  private UserService userService;

  @Mock
  private CartService cartService;

  @InjectMocks
  private CreateCart createCart;

  @Test
  void execute_user_valid_ok() {

    Long userId = 1L;
    String authenticatedUserName = "matias";

    User user = User.builder().id(userId).name(authenticatedUserName).build();
    Cart cart = Cart.builder().id(10L).user(user).status(CartStatus.OPEN).build();

    when(userService.getUser(userId)).thenReturn(user);
    when(cartService.save(any(Cart.class))).thenReturn(cart);

    CartResponse response = createCart.execute(userId, authenticatedUserName);

    assertNotNull(response);
    assertEquals(10L, response.getId());
    assertEquals(userId, response.getUserId());
    assertEquals(CartStatus.OPEN, response.getStatus());

    verify(userService).getUser(userId);
    verify(cartService).save(any(Cart.class));
  }

  @Test
  void execute_user_is_not_valid() {

    Long userId = 1L;
    String authenticatedUserName = "pepe";

    User user = User.builder().id(userId).name("matias").build();

    when(userService.getUser(userId)).thenReturn(user);

    assertThrows(UserForbidenException.class, () ->
        createCart.execute(userId, authenticatedUserName)
    );

    verify(userService).getUser(userId);
    verify(cartService, never()).save(any());
  }
}