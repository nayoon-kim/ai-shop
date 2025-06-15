package com.nayoon.ai_shop.infrastructure.persistence;

import com.nayoon.ai_shop.domain.model.Product;
import com.nayoon.ai_shop.domain.model.enums.Category;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private int price;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    protected ProductEntity() {
        // JPA용 기본 생성자
    }

    public Product toDomain() {
        return new Product(id, name, category, price, registeredAt);
    }
}
