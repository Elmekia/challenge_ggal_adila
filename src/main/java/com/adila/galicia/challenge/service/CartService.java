package com.adila.galicia.challenge.service;

import com.adila.galicia.challenge.entity.Cart;
import java.util.List;

public interface CartService {

  Cart save(Cart cart);

  Cart getCartById(Long idCart);

  List<Cart> getCartByUser(Long id);

  Cart getCartByIdWithItems(Long cartId);
}
