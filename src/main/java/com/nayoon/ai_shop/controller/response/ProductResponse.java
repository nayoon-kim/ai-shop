package com.nayoon.ai_shop.controller.response;

import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private Category category;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory()
        );
    }
}

