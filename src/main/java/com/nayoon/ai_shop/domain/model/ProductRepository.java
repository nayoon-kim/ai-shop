package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.domain.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    List<Product> findByBrandId(Long brandId);

    List<Product> findByCategoryAndBrandId(Category category, Long brandId);
}
