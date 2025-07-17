package com.adila.galicia.challenge.repository;

import com.adila.galicia.challenge.entity.Cart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<List<Cart>> findByUserId(Long userId);

  @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.id = :id")
  Optional<Cart> findByIdWithItems(@Param("id") Long id);

}
