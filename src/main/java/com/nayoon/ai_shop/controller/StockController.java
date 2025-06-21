package com.nayoon.ai_shop.controller;

import com.nayoon.ai_shop.controller.request.StockRequest;
import com.nayoon.ai_shop.domain.model.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nayoon.ai_shop.service.StockService;
import com.nayoon.ai_shop.service.PaymentService;
import com.nayoon.ai_shop.controller.response.PaymentResponse;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;
    private final PaymentService paymentService;

    public StockController(StockService stockService, PaymentService paymentService) {
        this.stockService = stockService;
        this.paymentService = paymentService;
    }

    @PostMapping("/buy")
    private ResponseEntity<PaymentResponse> decreaseStock(@RequestBody StockRequest stockRequest) {
        Stock stock = stockService.reserveStock(stockRequest.getProductId(), stockRequest.getQuantity());
        return ResponseEntity.ok(new PaymentResponse(stock));
    }
}
