package com.nayoon.ai_shop.controller;

import com.nayoon.ai_shop.controller.response.ProductResponse;
import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.enums.Category;
import com.nayoon.ai_shop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Long brandId
    ) {
        List<Product> products = productService.getProductsFiltered(category, brandId);
        List<ProductResponse> responseList = products.stream()
                .map(ProductResponse::from)
                .toList();
        return ResponseEntity.ok(responseList);
    }
}
