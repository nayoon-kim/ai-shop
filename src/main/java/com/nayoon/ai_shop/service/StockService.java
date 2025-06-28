package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.Stock;
import com.nayoon.ai_shop.domain.model.StockRepository;
import com.nayoon.ai_shop.exception.SoldOutException;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void decrease(Long productId, Long quantity) {
        int updates = stockRepository.decrease(productId, quantity);
        if (updates <= 0) throw new SoldOutException();
    }

    public void reserveWithPessimisticLock(Long productId, Long quantity) {
        Stock stock = stockRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new RuntimeException("재고 없음"));

        if (stock.getQuantity() <= quantity) throw new SoldOutException();
    }
}
