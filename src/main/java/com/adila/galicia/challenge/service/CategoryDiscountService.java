package com.adila.galicia.challenge.service;

import com.adila.galicia.challenge.entity.Category;
import com.adila.galicia.challenge.entity.CategoryDiscount;
import java.util.Optional;

public interface CategoryDiscountService {

  Optional<CategoryDiscount> getCategoryDiscount(Category category);

}
