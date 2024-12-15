package com.example.aim.portfolio.application;

import com.example.aim.portfolio.application.dto.response.CreatePortfolioResponseDto;

import java.util.List;

public interface PortfolioService {
    List<CreatePortfolioResponseDto> createPortfolio(Long consultationId);
}
