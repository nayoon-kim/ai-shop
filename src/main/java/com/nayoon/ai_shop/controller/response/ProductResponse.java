package com.nayoon.ai_shop.controller.response;

import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.enums.Category;

public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private Category category;

    public ProductResponse(Long id, String name, int price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory()
        );
    }
}

