package com.adila.galicia.challenge.service.impl;

import com.adila.galicia.challenge.dto.response.ProductDto;
import com.adila.galicia.challenge.entity.CartItem;
import com.adila.galicia.challenge.entity.Product;
import com.adila.galicia.challenge.exception.ProductNotFoundException;
import com.adila.galicia.challenge.repository.ProductRepository;
import com.adila.galicia.challenge.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<ProductDto> getProducts() {

    return this.productRepository.findAll().stream()
        .map(product -> new ProductDto(product.getId(), product.getName(),
            product.getCategory().getId())).toList();


  }

  @Override
  public Product getProductById(Long id) {
    return this.productRepository.findById(id).orElseThrow(
        () -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));
  }

  @Override
  public Product save(Product product) {
    return this.productRepository.save(product);
  }

  @Transactional
  @Override
  public void processCartItems(List<CartItem> cartItems) {
    for (CartItem item : cartItems) {
      int updated = productRepository.updateStockIfAvailable(
          item.getProduct().getId(),
          item.getNumberOfItems()
      );
      if (updated == 0) {
        throw new IllegalStateException(
            "No hay stock suficiente para el producto " + item.getProduct().getId());
      }
    }
  }
}
