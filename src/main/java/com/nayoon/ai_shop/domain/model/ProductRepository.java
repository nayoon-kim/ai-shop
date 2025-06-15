package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.domain.model.enums.Category;
import com.nayoon.ai_shop.infrastructure.persistence.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategory(Category category);
}
