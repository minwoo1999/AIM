package com.example.aim.security.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "Securities")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SecurityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    public void updatePrice(Double price) {
        this.price = BigDecimal.valueOf(price);
    }
}