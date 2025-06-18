package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.infrastructure.persistence.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
     void save(Stock stock);

     @Query("SELECT s FROM StockEntity s JOIN FETCH s.product p JOIN FETCH p.brand WHERE p.id = :productId")
    Optional<StockEntity> findByProductId(@Param("productId") Long productId);
}
