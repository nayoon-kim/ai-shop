package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.domain.model.enums.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    private Long brand_id;

    @Column(nullable = false)
    private int price;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;
}
