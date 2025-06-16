package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.enums.Category;
import com.nayoon.ai_shop.infrastructure.persistence.ProductEntity;
import com.nayoon.ai_shop.domain.model.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByBrandId(Long brandId) {
        return productRepository.findByBrandId(brandId)
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByCategoryAndBrandId(Category category, Long brandId) {
        return productRepository.findByCategoryAndBrandId(category, brandId)
                .stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Product> getProductsFiltered(Category category, Long brandId) {
        if (category == null && brandId == null) {
            return getAllProducts();
        } else if (category == null) {
            return getProductsByBrandId(brandId);
        } else if (brandId == null) {
            return getProductsByCategory(category);
        } else {
            return getProductsByCategoryAndBrandId(category, brandId);
        }
    }
}
