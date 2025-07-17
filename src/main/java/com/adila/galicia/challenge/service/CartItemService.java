package com.adila.galicia.challenge.service;

import com.adila.galicia.challenge.entity.Cart;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Product;

public interface CartItemService {

  CartItem create(Cart cart, Product product, int numberOfItems);

}
