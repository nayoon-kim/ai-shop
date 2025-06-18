package com.nayoon.ai_shop.controller.response;

import com.nayoon.ai_shop.domain.model.Stock;

public class PaymentResponse {
    private Long productId;
    private int quantity;

    public PaymentResponse(Stock stock) {
        this.productId = stock.getProduct().getId();
        this.quantity = stock.getQuantity();
    }
}
