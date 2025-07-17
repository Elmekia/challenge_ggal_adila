package com.adila.galicia.challenge.application;

import com.adila.galicia.challenge.dto.response.CartResponse;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.User;
import com.adila.galicia.challenge.exception.UserForbidenException;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.service.UserService;
import com.adila.galicia.challenge.utils.CartStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCart {

  private final UserService userService;
  private final CartService cartService;


  public CartResponse execute(Long userId, String autenticatedUserName) {
    User user = this.userService.getUser(userId);
    validateUser(user, autenticatedUserName);
    Cart cart = this.cartService.save(Cart.builder().user(user).status(CartStatus.OPEN).build());
    return CartResponse.builder().id(cart.getId()).userId(cart.getUser().getId())
        .status(cart.getStatus()).build();
  }

  private void validateUser(User user, String autenticatedUserName) {
    if (!user.getName().equals(autenticatedUserName)) {
      throw new UserForbidenException("El usuario no coincide con el autenticado");
    }
  }


}
