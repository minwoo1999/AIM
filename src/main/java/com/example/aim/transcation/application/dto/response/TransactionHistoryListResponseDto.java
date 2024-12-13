package com.example.aim.transcation.application.dto.response;

import com.example.aim.transcation.domain.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHistoryListResponseDto {
    private Long transactionId;          // 트랜잭션 ID
    private TransactionType transactionType; // 트랜잭션 타입 (DEPOSIT, WITHDRAWAL)
    private BigDecimal amount;           // 거래 금액
    private LocalDateTime createdAt;     // 거래 생성 시각
    private String username;             // 유저 이름

}