package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.controller.request.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public boolean pay(int cost) {
        return true;
    }

    public void cancel(OrderRequest request) {
    }
}
