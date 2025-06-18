package com.nayoon.ai_shop.infrastructure.persistence;

import com.nayoon.ai_shop.domain.model.Stock;
import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false)
    private int quantity;

    protected StockEntity() {}

    public Stock toDomain() {
        return new Stock(id, product.toDomain(), quantity);
    }
}
