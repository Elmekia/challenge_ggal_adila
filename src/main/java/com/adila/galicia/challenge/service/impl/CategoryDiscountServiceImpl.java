package com.adila.galicia.challenge.service.impl;

import com.adila.galicia.challenge.entity.Category;
import com.adila.galicia.challenge.entity.CategoryDiscount;
import com.adila.galicia.challenge.repository.CategoryDiscountRepository;
import com.adila.galicia.challenge.service.CategoryDiscountService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CategoryDiscountServiceImpl implements CategoryDiscountService {

  private final CategoryDiscountRepository categoryDiscountRepository;

  public CategoryDiscountServiceImpl(CategoryDiscountRepository categoryDiscountRepository) {
    this.categoryDiscountRepository = categoryDiscountRepository;
  }

  @Override
  public Optional<CategoryDiscount> getCategoryDiscount(Category category) {
    return this.categoryDiscountRepository.findByCategory(category);
  }
}
