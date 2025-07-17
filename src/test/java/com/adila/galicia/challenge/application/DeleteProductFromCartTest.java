package com.adila.galicia.challenge.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.exception.CartItemNotFoundException;
import com.adila.galicia.challenge.exception.CartStatusException;
import com.adila.galicia.challenge.service.CartItemService;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.utils.CartStatus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteProductFromCartTest {

  @Mock
  private CartItemService cartItemService;

  @Mock
  private CartService cartService;

  @InjectMocks
  private DeleteProductFromCart deleteProductFromCart;

  @Test
  void deleteProductOK() {
    Long cartId = 1L;
    Long productId = 100L;

    Product product = Product.builder().id(productId).build();
    CartItem item = CartItem.builder().product(product).build();

    Cart cart = Cart.builder()
        .id(cartId)
        .status(CartStatus.OPEN)
        .items(new ArrayList<>(List.of(item)))
        .build();

    when(cartService.getCartById(cartId)).thenReturn(cart);

    deleteProductFromCart.execute(cartId, productId);

    assertTrue(cart.getItems().isEmpty());
  }

  @Test
  void deleteProductCartNotOpen() {
    Cart cart = Cart.builder()
        .id(1L)
        .status(CartStatus.COMPLETED)
        .items(new ArrayList<>())
        .build();

    when(cartService.getCartById(1L)).thenReturn(cart);

    assertThrows(CartStatusException.class,
        () -> deleteProductFromCart.execute(1L, 10L));
  }

  @Test
  void deleteProductItemDoesNotExists() {
    Long cartId = 1L;
    Long productId = 100L;

    Product product = Product.builder().id(200L).build();
    CartItem item = CartItem.builder().product(product).build();

    Cart cart = Cart.builder()
        .id(cartId)
        .status(CartStatus.OPEN)
        .items(new ArrayList<>(List.of(item)))
        .build();

    when(cartService.getCartById(cartId)).thenReturn(cart);

    assertThrows(CartItemNotFoundException.class,
        () -> deleteProductFromCart.execute(cartId, productId));
  }
}