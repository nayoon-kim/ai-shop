package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.Stock;
import com.nayoon.ai_shop.domain.model.StockRepository;

import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock reserveStock(Long productId, int quantity) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("stock not found"));
        stock.reserve(quantity);

        return stockRepository.save(stock);
    }
}
