package com.nayoon.ai_shop.domain.model;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

     Optional<Stock> findByProductId(@Param("productId") Long productId);

     @Modifying(clearAutomatically = true)
     @Query("UPDATE Stock s SET s.quantity = :quantity WHERE s.productId = :productId AND s.quantity >= :quantity")
     void decrease(@Param("productId") Long productId, @Param("quantity") Long quantity);

     @Lock(LockModeType.PESSIMISTIC_WRITE)
     @Query("SELECT s FROM Stock s WHERE s.productId = :productId")
     Optional<Stock> findByProductIdWithPessimisticLock(@Param("productId") Long productId);

     @Lock(LockModeType.OPTIMISTIC)
     @Query("SELECT s FROM Stock s WHERE s.productId = :productId")
     Optional<Stock> findByProductIdWithOptimisticLock(@Param("productId") Long productId);
}
