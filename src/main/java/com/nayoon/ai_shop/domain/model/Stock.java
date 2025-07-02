package com.nayoon.ai_shop.domain.model;

import com.nayoon.ai_shop.exception.SoldOutException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name = "stocks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    @Version
    private Long version;

    public void decrease(Long quantity) {
        if (this.quantity < quantity) {
            throw new SoldOutException(
                    "Not enough stock to reserve. Available: " + this.quantity + ", Requested: " + quantity);
        }

        this.quantity -= quantity;
    }
}
