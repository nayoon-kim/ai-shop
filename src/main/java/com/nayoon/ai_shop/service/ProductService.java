package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.enums.Category;
import com.nayoon.ai_shop.domain.model.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByBrandId(Long brandId) {
        return productRepository.findByBrandId(brandId);
    }

    public List<Product> getProductsByCategoryAndBrandId(Category category, Long brandId) {
        return productRepository.findByCategoryAndBrandId(category, brandId);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

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
