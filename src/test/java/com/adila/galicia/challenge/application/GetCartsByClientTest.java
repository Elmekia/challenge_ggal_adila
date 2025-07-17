package com.adila.galicia.challenge.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.adila.galicia.challenge.dto.response.CartResponse;
import com.adila.galicia.challenge.dto.response.ItemResponse;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Category;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.utils.CartStatus;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCartsByClientTest {

  @Mock
  private CartService cartService;

  @InjectMocks
  private GetCartsByClient getCartsByClient;

  @Test
  void getCartsByUser() {
    Long userId = 1L;

    Category category = Category.builder().name("Electrónica").build();
    Product product = Product.builder().id(10L).name("Celular").category(category)
        .price(BigDecimal.valueOf(200)).build();
    CartItem item = CartItem.builder().product(product).numberOfItems(2).build();

    Cart cart = Cart.builder()
        .id(5L)
        .status(CartStatus.OPEN)
        .total(BigDecimal.valueOf(400))
        .items(List.of(item))
        .build();

    when(cartService.getCartByUser(userId)).thenReturn(List.of(cart));

    List<CartResponse> responses = getCartsByClient.execute(userId);

    assertEquals(1, responses.size());
    CartResponse response = responses.get(0);
    assertEquals(5L, response.getId());
    assertEquals(CartStatus.OPEN, response.getStatus());
    assertEquals(1, response.getItems().size());

    ItemResponse itemResponse = response.getItems().get(0);
    assertEquals("Celular", itemResponse.getName());
    assertEquals("Electrónica", itemResponse.getCategoryName());
    assertEquals(2, itemResponse.getNumberOfItems());
  }
}