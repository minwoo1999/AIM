package com.example.aim.consultant.application.dto.request;

import com.example.aim.portfolio.domain.PortfolioType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ConsultationRequestDto {

    @NotNull
    private PortfolioType portfolioType; // 포트폴리오 유형 (HIGH_RISK, MEDIUM_RISK)
}