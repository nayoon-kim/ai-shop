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

    public boolean decrease(Long productId, Long quantity) {
        int updates = stockRepository.decrease(productId, quantity);
        return updates <= 0;
    }

    public boolean reserveWithPessimisticLock(Long productId, Long quantity) {
        Stock stock = stockRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new RuntimeException("재고 없음"));

        if (stock.getQuantity() < quantity) {
            return false;
        }

        return true;
    }
}
