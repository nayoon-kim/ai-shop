package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.StockRepository;

import com.nayoon.ai_shop.exception.SoldOutException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long productId, int quantity) throws SoldOutException  {
        int updates = stockRepository.decrease(productId, quantity);

        if (updates <= 0) {
            throw new SoldOutException();
        }
    }
}
