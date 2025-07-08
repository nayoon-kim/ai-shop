package com.nayoon.ai_shop.controller.response;

import com.nayoon.ai_shop.domain.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {
    private Long productId;
    private Long quantity;

    public PaymentResponse(Stock stock) {
        this.productId = stock.getProductId();
        this.quantity = stock.getQuantity();
    }
}
