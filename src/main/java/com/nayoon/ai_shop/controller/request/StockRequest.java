package com.nayoon.ai_shop.controller.request;

public class StockRequest {
    private Long productId;
    private Long quantity;

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
