package com.example.aim.portfolio.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CreatePortfolioResponseDto {

    private Long portfolioId;         // Portfolio ID
    private Long consultationId;      // Associated consultation ID
    private Long securityId;          // Security ID
    private String securityCode;      // Security code
    private String securityName;      // Security name
    private BigDecimal securityPrice; // Price of the security
    private Integer quantity;         // Quantity of the security

}