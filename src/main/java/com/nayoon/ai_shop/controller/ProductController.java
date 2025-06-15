package com.nayoon.ai_shop.controller;

import com.nayoon.ai_shop.controller.response.ProductResponse;
import com.nayoon.ai_shop.controller.response.TokenResponse;
import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> responseList = products.stream()
                .map(ProductResponse::from)
                .toList();
        return ResponseEntity.ok(responseList);
    }
}
