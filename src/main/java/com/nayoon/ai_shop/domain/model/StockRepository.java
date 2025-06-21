package com.nayoon.ai_shop.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
     Stock save(Stock stock);

     @Query("SELECT s FROM Stock s JOIN FETCH s.product p JOIN FETCH p.brand WHERE p.id = :productId")
    Optional<Stock> findByProductId(@Param("productId") Long productId);
}
