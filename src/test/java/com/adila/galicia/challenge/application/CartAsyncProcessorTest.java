package com.adila.galicia.challenge.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Category;
import com.adila.galicia.challenge.entity.CategoryDiscount;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.service.CategoryDiscountService;
import com.adila.galicia.challenge.service.ProductService;
import com.adila.galicia.challenge.utils.CartStatus;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartAsyncProcessorTest {

  @Mock
  private CartService cartService;

  @Mock
  private ProductService productService;

  @Mock
  private CategoryDiscountService categoryDiscountService;

  @InjectMocks
  private CartAsyncProcessor cartAsyncProcessor;

  @Test
  void cancelEmptyCart() {
    Long cartId = 1L;
    Cart emptyCart = new Cart();
    emptyCart.setItems(Collections.emptyList());

    when(cartService.getCartByIdWithItems(cartId)).thenReturn(emptyCart);

    cartAsyncProcessor.execute(cartId);

    assertEquals(CartStatus.CANCELLED, emptyCart.getStatus());
    verify(cartService).save(emptyCart);
    verify(productService, never()).processCartItems(any());
  }

  @Test
  void processCartOK() {
    Long cartId = 2L;
    Product product = new Product();
    product.setPrice(BigDecimal.valueOf(100));
    product.setCategory(Category.builder().id(1L).name("ELECTRONICA").build());

    CartItem item = new CartItem();
    item.setProduct(product);
    item.setNumberOfItems(2);

    Cart cart = new Cart();
    cart.setItems(List.of(item));

    CategoryDiscount discount = new CategoryDiscount();
    discount.setPercentage(BigDecimal.valueOf(10));

    when(cartService.getCartByIdWithItems(cartId)).thenReturn(cart);
    when(categoryDiscountService.getCategoryDiscount(
        Category.builder().id(1L).name("ELECTRONICA").build()))
        .thenReturn(Optional.of(discount));

    cartAsyncProcessor.execute(cartId);

    verify(productService).processCartItems(cart.getItems());
    verify(cartService).save(cart);
    assertEquals(CartStatus.COMPLETED, cart.getStatus());

    assertEquals(BigDecimal.valueOf(180), cart.getTotal());
  }

  @Test
  void processCartError() {
    Long cartId = 3L;
    Product product = new Product();
    product.setPrice(BigDecimal.valueOf(100));
    product.setCategory(Category.builder().id(1L).name("ELECTRONICA").build());

    CartItem item = new CartItem();
    item.setProduct(product);
    item.setNumberOfItems(1);

    Cart cart = new Cart();
    cart.setItems(List.of(item));

    when(cartService.getCartByIdWithItems(cartId)).thenReturn(cart);
    doThrow(new IllegalStateException("error")).when(productService).processCartItems(any());

    cartAsyncProcessor.execute(cartId);

    verify(cartService).save(cart);
    assertEquals(CartStatus.CANCELLED, cart.getStatus());
  }
}