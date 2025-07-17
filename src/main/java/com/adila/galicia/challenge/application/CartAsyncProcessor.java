package com.adila.galicia.challenge.application;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.CategoryDiscount;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.service.CartService;
import com.adila.galicia.challenge.service.CategoryDiscountService;
import com.adila.galicia.challenge.service.ProductService;
import com.adila.galicia.challenge.utils.CartStatus;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartAsyncProcessor {

  private final CartService cartService;
  private final ProductService productService;
  private final CategoryDiscountService categoryDiscountService;

  @Async("cartExecutor")
  public void execute(Long cartId) {
    log.info("Comienza a procesar el carrito {}", cartId);
    Cart cart = cartService.getCartByIdWithItems(cartId);
    if (cart.getItems().isEmpty()) {
      cart.setStatus(CartStatus.CANCELLED);
      cartService.save(cart);
      log.info("Cancela el proceso del carrito {} porque esta vacio", cartId);
      return;
    }
    try {
      productService.processCartItems(cart.getItems());

      cart.setStatus(CartStatus.COMPLETED);
    } catch (Exception e) {
      cart.setStatus(CartStatus.CANCELLED);
      cartService.save(cart);
      log.info("Cancela por erro al procesar el carrito {}", cartId);
      return;
    }
    cart.setTotal(this.calculateTotal(cart));

    cartService.save(cart);
    log.info("Finaliza de procesar el carrito {}", cartId);
  }

  private BigDecimal calculateTotal(Cart cart) {
    BigDecimal total = BigDecimal.ZERO;

    for (CartItem item : cart.getItems()) {
      Product product = item.getProduct();
      int numberOfItems = item.getNumberOfItems();

      BigDecimal unitPrice = product.getPrice();
      BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(numberOfItems));

      Optional<CategoryDiscount> discountOpt =
          this.categoryDiscountService.getCategoryDiscount(product.getCategory());

      if (discountOpt.isPresent()) {
        BigDecimal percentage = discountOpt.get().getPercentage();
        BigDecimal discount = subtotal.multiply(percentage).divide(BigDecimal.valueOf(100));
        subtotal = subtotal.subtract(discount);
      }

      total = total.add(subtotal);
    }

    return total;
  }


}





