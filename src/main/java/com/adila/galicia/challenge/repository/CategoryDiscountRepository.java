package com.adila.galicia.challenge.repository;

import com.adila.galicia.challenge.entity.Category;
import com.adila.galicia.challenge.entity.CategoryDiscount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDiscountRepository extends JpaRepository<CategoryDiscount, Long> {

  Optional<CategoryDiscount> findByCategory(Category category);

}
