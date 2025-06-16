package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.domain.model.enums.Category;

import java.time.LocalDateTime;

public class Product {
    private final Long id;
    private final String name;
    private final Category category;
    private final Brand brand;
    private final int price;
    private final LocalDateTime registeredAt;

    public Product(Long id, String name, Category category, Brand brand, int price, LocalDateTime registeredAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
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

    public Brand getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
}
