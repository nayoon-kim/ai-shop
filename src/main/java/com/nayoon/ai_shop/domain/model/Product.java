package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.domain.model.enums.Category;

import java.time.LocalDateTime;

public class Product {
    private final Long id;
    private final String name;
    private final Category category;
    private final int price;
    private final LocalDateTime registeredAt;

    public Product(Long id, String name, Category category, int price, LocalDateTime registeredAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.registeredAt = registeredAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}
