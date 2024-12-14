package com.example.aim.security.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class SecurityResponseDto {
    private Long id;
    private String securityCode;
    private String name;
    private BigDecimal price;
}