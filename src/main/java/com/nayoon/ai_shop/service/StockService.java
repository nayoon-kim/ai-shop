package com.nayoon.ai_shop.service;

import com.nayoon.ai_shop.domain.model.Stock;
import com.nayoon.ai_shop.domain.model.StockRepository;
import com.nayoon.ai_shop.exception.SoldOutException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private static final int MAX_RETRY = 5;
    private static final long RETRY_WAIT_MS = 50;

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(() -> new IllegalArgumentException("재고 없음"));

        stock.decrease(quantity);
    }

    public void decreaseWithPessimisticLock(Long productId, Long quantity) {
        stockRepository.decrease(productId, quantity);
    }

    public void reserveWithPessimisticLock(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdWithPessimisticLock(productId)
                .orElseThrow(() -> new IllegalArgumentException("재고 없음"));

        if (stock.getQuantity() < quantity) throw new SoldOutException();
    }

    public void reserveWithOptimisticLock(Long productId, Long quantity) {
        int retryCount = 0;

        while(retryCount++ < MAX_RETRY) {
            try {
                Stock stock = stockRepository.findByProductIdWithOptimisticLock(productId).
                        orElseThrow(() -> new IllegalArgumentException("재고 없음"));

                stock.decrease(quantity); // 실제 재고 감소
                stockRepository.save(stock); // save 시 version 체크

                break;
            } catch(OptimisticLockException e) {
                try {
                    Thread.sleep(RETRY_WAIT_MS);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } catch(IllegalArgumentException | SoldOutException e) {
                throw e;
            }
        }

        if (retryCount >= MAX_RETRY) {
            throw new RuntimeException("재고 선점 재시도 초과");
        }
    }
}
