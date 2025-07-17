package com.adila.galicia.challenge.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.adila.galicia.challenge.dto.response.CartResponse;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Category;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.exception.CartItemConflictException;
import com.adila.galicia.challenge.exception.CartStatusException;
import com.adila.galicia.challenge.exception.StockUnavailableException;
import com.adila.galicia.challenge.service.CartItemService;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.service.ProductService;
import com.adila.galicia.challenge.utils.CartStatus;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddProductToCartTest {

  @Mock
  private CartItemService cartItemService;

  @Mock
  private CartService cartService;

  @Mock
  private ProductService productService;

  @InjectMocks
  private AddProductToCart addProductToCart;

  @Test
  void execute_OK() {

    Long cartId = 1L;
    Long productId = 100L;
    int numberOfItems = 2;

    Category category = Category.builder().id(1L).name("Electrónica").build();
    Product product = Product.builder()
        .id(productId)
        .name("Auriculares")
        .stock(10)
        .price(BigDecimal.valueOf(3000))
        .category(category)
        .build();

    Cart cart = Cart.builder()
        .id(cartId)
        .status(CartStatus.OPEN)
        .items(new ArrayList<>())
        .build();

    CartItem cartItem = CartItem.builder()
        .id(10L)
        .product(product)
        .cart(cart)
        .numberOfItems(numberOfItems)
        .build();

    when(cartService.getCartById(cartId)).thenReturn(cart);
    when(productService.getProductById(productId)).thenReturn(product);
    when(cartItemService.create(cart, product, numberOfItems)).thenReturn(cartItem);

    CartResponse response = addProductToCart.execute(cartId, productId, numberOfItems);

    assertNotNull(response);
    assertEquals(cartId, response.getId()); // id del cartItem == cartId en CartResponse
    assertEquals(CartStatus.OPEN, response.getStatus());
    assertEquals(1, response.getItems().size());
    assertEquals("Auriculares", response.getItems().get(0).getName());
  }

  @Test
  void execute_cartNotOpen() {

    Long cartId = 1L;
    Long productId = 100L;
    int quantity = 1;

    Cart cart = Cart.builder()
        .id(cartId)
        .status(CartStatus.CANCELLED)
        .items(new ArrayList<>())
        .build();

    when(cartService.getCartById(cartId)).thenReturn(cart);

    assertThrows(CartStatusException.class, () ->
        addProductToCart.execute(cartId, productId, quantity)
    );
  }

  @Test
  void execute_productAlreadyExists() {

    Long cartId = 1L;
    Long productId = 100L;
    int quantity = 1;

    Product product = Product.builder().id(productId).build();
    CartItem existingItem = CartItem.builder().product(product).build();
    List<CartItem> items = new ArrayList<>();
    items.add(existingItem);

    Cart cart = Cart.builder()
        .id(cartId)
        .status(CartStatus.OPEN)
        .items(items)
        .build();

    when(cartService.getCartById(cartId)).thenReturn(cart);

    assertThrows(CartItemConflictException.class, () ->
        addProductToCart.execute(cartId, productId, quantity)
    );
  }

  @Test
  void execute_stockIsNotEnough() {

    Long cartId = 1L;
    Long productId = 100L;
    int numberOfItems = 5;

    Cart cart = Cart.builder()
        .id(cartId)
        .status(CartStatus.OPEN)
        .items(new ArrayList<>())
        .build();

    Product product = Product.builder()
        .id(productId)
        .stock(2)
        .build();

    when(cartService.getCartById(cartId)).thenReturn(cart);
    when(productService.getProductById(productId)).thenReturn(product);

    assertThrows(StockUnavailableException.class, () ->
        addProductToCart.execute(cartId, productId, numberOfItems)
    );
  }
}