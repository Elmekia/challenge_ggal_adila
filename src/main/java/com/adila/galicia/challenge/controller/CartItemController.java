package com.adila.galicia.challenge.controller;

import com.adila.galicia.challenge.application.AddProductToCart;
import com.adila.galicia.challenge.application.DeleteProductFromCart;
import com.adila.galicia.challenge.application.GetProductsFromCart;
import com.adila.galicia.challenge.dto.request.AddProductRequest;
import com.adila.galicia.challenge.dto.response.CartResponse;
import com.adila.galicia.challenge.dto.response.ItemResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carritos")
public class CartItemController {

  private final AddProductToCart addProductToCart;
  private final DeleteProductFromCart deleteProductFromCart;
  private final GetProductsFromCart getProductsFromCart;

  public CartItemController(AddProductToCart addProductToCart,
      DeleteProductFromCart deleteProductFromCart, GetProductsFromCart getProductsFromCart) {
    this.addProductToCart = addProductToCart;
    this.deleteProductFromCart = deleteProductFromCart;
    this.getProductsFromCart = getProductsFromCart;
  }


  @PostMapping("/{idCart}/productos")
  @ResponseStatus(HttpStatus.CREATED)
  CartResponse addProductoToCart(@PathVariable Long idCart,
      @Valid @RequestBody AddProductRequest addProductRequest) {
    return this.addProductToCart.execute(idCart, addProductRequest.getProductId(),
        addProductRequest.getNumberOfItems());
  }

  @DeleteMapping("/{idCart}/productos/{idProducto}")
  @ResponseStatus(HttpStatus.OK)
  void deleteProductFromCart(@PathVariable Long idCart, @PathVariable Long idProducto) {
    this.deleteProductFromCart.execute(idCart, idProducto);
  }

  @GetMapping("/{idCart}/productos")
  @ResponseStatus(HttpStatus.OK)
  List<ItemResponse> getProductsFromCart(@PathVariable Long idCart) {
    return this.getProductsFromCart.execute(idCart);
  }
}
