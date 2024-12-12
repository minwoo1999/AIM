package com.example.aim.security.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Securities")
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

}