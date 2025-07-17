package com.adila.galicia.challenge.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.adila.galicia.challenge.dto.response.ItemResponse;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Category;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.service.CartService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetProductsFromCartTest {

  @Mock
  private CartService cartService;

  @InjectMocks
  private GetProductsFromCart getProductsFromCart;

  @Test
  void getProductsFromCart() {
    Long cartId = 1L;

    Category category = Category.builder().name("Libros").build();
    Product product = Product.builder().name("Java Básico").category(category)
        .price(BigDecimal.valueOf(50)).build();

    CartItem item = CartItem.builder()
        .id(10L)
        .product(product)
        .numberOfItems(3)
        .build();

    Cart cart = Cart.builder().id(cartId).items(List.of(item)).build();

    when(cartService.getCartById(cartId)).thenReturn(cart);

    List<ItemResponse> result = getProductsFromCart.execute(cartId);

    assertEquals(1, result.size());
    ItemResponse itemResponse = result.get(0);

    assertEquals("Java Básico", itemResponse.getName());
    assertEquals("Libros", itemResponse.getCategoryName());
    assertEquals(3, itemResponse.getNumberOfItems());
    assertEquals(BigDecimal.valueOf(50), itemResponse.getPrice());
  }
}