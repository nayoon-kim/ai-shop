package com.nayoon.ai_shop.domain.model;

public class Stock {
    private final Long id;
    private final Product product;
    private final int quantity;

    public Stock(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Stock reserve(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException(
                    "Not enough stock to reserve. Available: " + this.quantity + ", Requested: " + quantity);
        }
        return new Stock(id, product, this.quantity - quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
