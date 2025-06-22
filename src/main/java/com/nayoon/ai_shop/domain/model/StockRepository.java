package com.nayoon.ai_shop.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

     @Query("SELECT s FROM Stock s JOIN FETCH s.product p JOIN FETCH p.brand WHERE p.id = :productId")
     Optional<Stock> findByProductId(@Param("productId") Long productId);

     @Modifying(clearAutomatically = true)
     @Query("UPDATE Stock s SET s.quantity = s.quantity - :quantity WHERE s.product.id = :productId AND s.quantity >= :quantity")
     int decrease(@Param("productId") Long productId, @Param("quantity") Long quantity);
}
