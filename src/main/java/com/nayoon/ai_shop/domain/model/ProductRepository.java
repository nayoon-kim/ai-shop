package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.domain.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p JOIN FETCH p.brand WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") Category category);

    @Query("SELECT p FROM Product p JOIN FETCH p.brand b WHERE b.id = :brandId")
    List<Product> findByBrandId(@Param("brandId") Long brandId);

    @Query("SELECT p FROM Product p JOIN FETCH p.brand b WHERE p.category = :category and b.id = :brandId")
    List<Product> findByCategoryAndBrandId(@Param("category") Category category, @Param("brandId") Long brandId);
}
