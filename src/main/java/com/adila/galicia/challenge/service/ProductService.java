package com.adila.galicia.challenge.service;

import com.adila.galicia.challenge.dto.response.ProductDto;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Product;
import java.util.List;

public interface ProductService {

  List<ProductDto> getProducts();

  Product getProductById(Long id);

  Product save(Product product);

  public void processCartItems(List<CartItem> cartItems);
}
