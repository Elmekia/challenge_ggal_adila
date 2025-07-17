package com.adila.galicia.challenge.controller;

import com.adila.galicia.challenge.application.CreateCart;
import com.adila.galicia.challenge.application.GetCartsByClient;
import com.adila.galicia.challenge.application.ProcessCart;
import com.adila.galicia.challenge.dto.request.CreateCartRequest;
import com.adila.galicia.challenge.dto.request.GetCartsByClientRequest;
import com.adila.galicia.challenge.dto.response.CartResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carritos")
public class CartController {

  private final CreateCart createCart;
  private final GetCartsByClient getCartsByClient;
  private final ProcessCart processCart;


  public CartController(CreateCart createCart, GetCartsByClient getCartsByClient,
      ProcessCart processCart) {
    this.createCart = createCart;
    this.getCartsByClient = getCartsByClient;
    this.processCart = processCart;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CartResponse createCart(@Valid @RequestBody CreateCartRequest createCartRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    return this.createCart.execute(createCartRequest.getUserId(), userDetails.getUsername());
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  List<CartResponse> getCartsByClient(
      @Valid @RequestBody GetCartsByClientRequest getCartsByClientRequest) {
    return this.getCartsByClient.execute(getCartsByClientRequest.getUserId());
  }

  @PostMapping("/{idCart}/procesar")
  @ResponseStatus(HttpStatus.ACCEPTED)
  String processCart(@PathVariable Long idCart, @AuthenticationPrincipal UserDetails userDetails) {
    return this.processCart.execute(idCart, userDetails.getUsername());
  }

}
