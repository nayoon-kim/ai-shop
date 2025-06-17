package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.domain.model.enums.Category;
import com.nayoon.ai_shop.infrastructure.persistence.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.brand WHERE p.category = :category")
    List<ProductEntity> findByCategory(@Param("category") Category category);

    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.brand b WHERE b.id = :brandId")
    List<ProductEntity> findByBrandId(@Param("brandId") Long brandId);

    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.brand b WHERE p.category = :category and b.id = :brandId")
    List<ProductEntity> findByCategoryAndBrandId(@Param("category") Category category, @Param("brandId") Long brandId);
}
