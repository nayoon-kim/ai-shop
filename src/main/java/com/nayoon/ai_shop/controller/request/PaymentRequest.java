package com.nayoon.ai_shop.controller.request;

import com.nayoon.ai_shop.domain.model.enums.PaymentMethod;
import lombok.Getter;

@Getter
public class PaymentRequest {
    private PaymentMethod method;
    private String cardToken;
}
