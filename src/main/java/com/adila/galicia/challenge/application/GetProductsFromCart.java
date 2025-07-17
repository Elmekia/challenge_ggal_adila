package com.adila.galicia.challenge.application;

import com.adila.galicia.challenge.dto.response.ItemResponse;
import com.adila.galicia.challenge.service.CartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductsFromCart {

  private final CartService cartService;

  public List<ItemResponse> execute(Long idCart) {
    return this.cartService.getCartById(idCart).getItems().stream()
        .map(item -> ItemResponse.builder()
            .id(item.getId())
            .name(item.getProduct().getName())
            .categoryName(item.getProduct().getCategory().getName())
            .price(item.getProduct().getPrice())
            .numberOfItems(item.getNumberOfItems())
            .build()).toList();
  }
}
