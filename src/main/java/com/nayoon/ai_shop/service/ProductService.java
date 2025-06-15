package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.infrastructure.persistence.ProductEntity;
import com.nayoon.ai_shop.domain.model.ProductRepository;
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
}
