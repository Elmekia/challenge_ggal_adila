package com.adila.galicia.challenge.application;

import com.adila.galicia.challenge.dto.response.CartResponse;
import com.adila.galicia.challenge.dto.response.ItemResponse;
import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.exception.CartItemConflictException;
import com.adila.galicia.challenge.exception.CartStatusException;
import com.adila.galicia.challenge.exception.StockUnavailableException;
import com.adila.galicia.challenge.service.CartItemService;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.service.ProductService;
import com.adila.galicia.challenge.utils.CartStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddProductToCart {

  private final CartItemService cartItemService;
  private final CartService cartService;
  private final ProductService productService;

  public CartResponse execute(Long idCart, Long idProduct, int numberOfItems) {

    Cart cart = this.cartService.getCartById(idCart);

    if (cart.getStatus() != CartStatus.OPEN) {
      log.warn("El carrito no se puede procesar en su estado actual.");
      throw new CartStatusException("El carrito no se puede procesar en su estado actual.");
    }

    this.validateIfProductExists(cart, idProduct);
    Product product = this.productService.getProductById(idProduct);
    if (product.getStock() < numberOfItems) {
      log.warn("No hasy stock suficiente");
      throw new StockUnavailableException("No hay suficiente stock");
    }
    CartItem cartItem = this.cartItemService.create(cart, product, numberOfItems);
    this.addCartItem(cartItem);
    return CartResponse.builder().id(idCart).status(cartItem.getCart().getStatus())
        .items(cartItem.getCart().getItems().stream()
            .map(item -> ItemResponse.builder()
                .id(item.getProduct().getId())
                .name(item.getProduct().getName())
                .categoryName(item.getProduct().getCategory().getName())
                .price(item.getProduct().getPrice())
                .numberOfItems(item.getNumberOfItems())
                .build()
            )
            .toList()).build();
  }

  private void addCartItem(CartItem cartItem) {
    cartItem.getCart().getItems().add(cartItem);
  }

  private void validateIfProductExists(Cart cart, Long idProduct) {

    if (cart.getItems()
        .stream()
        .anyMatch(cartItem -> cartItem.getProduct().getId().equals(idProduct))) {
      throw new CartItemConflictException(
          "El producto con ID " + idProduct + " ya existe en el carrito");
    }
  }


}
