package com.example.aim.transcation.domain;

import com.example.aim.member.domain.UserEntity;
import com.example.aim.shared.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "Transactions")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TransactionEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TransactionType transactionType;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;



}
