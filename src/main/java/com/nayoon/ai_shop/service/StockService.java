package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.Stock;
import com.nayoon.ai_shop.domain.model.StockRepository;
import com.nayoon.ai_shop.infrastructure.persistence.StockEntity;

import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock reserveStock(Long productId, int quantity) {
        Stock stock = stockRepository.findByProductId(productId).map(StockEntity::toDomain)
                .orElseThrow(() -> new RuntimeException("stock not found"));

        stock = stock.reserve(quantity);
        stockRepository.save(stock);
        return stock;
    }
}
