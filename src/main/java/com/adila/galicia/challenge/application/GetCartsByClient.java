package com.adila.galicia.challenge.application;

import com.adila.galicia.challenge.dto.response.CartResponse;
import com.adila.galicia.challenge.dto.response.ItemResponse;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCartsByClient {

  private final CartService cartService;

  public List<CartResponse> execute(Long userId) {
    List<Cart> carts = this.cartService.getCartByUser(userId);

    return carts.stream().map(
        cart -> CartResponse.builder().id(cart.getId()).status(cart.getStatus())
            .total(cart.getTotal()).items(cart.getItems().stream().map(
                item -> ItemResponse.builder().id(item.getProduct().getId())
                    .name(item.getProduct().getName())
                    .categoryName(item.getProduct().getCategory().getName())
                    .price(item.getProduct().getPrice()).numberOfItems(item.getNumberOfItems())
                    .build()).toList()).build()).toList();
  }

}
