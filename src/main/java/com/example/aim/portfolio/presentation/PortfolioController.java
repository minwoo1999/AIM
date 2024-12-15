package com.example.aim.portfolio.presentation;

import com.example.aim.portfolio.application.PortfolioService;
import com.example.aim.portfolio.application.dto.response.CreatePortfolioResponseDto;
import com.example.aim.shared.dto.CommonResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping("")
    public ResponseEntity<CommonResDto<?>> createPortfolio(@RequestParam Long consultationId) {
        List<CreatePortfolioResponseDto> portfolios = portfolioService.createPortfolio(consultationId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResDto<>(1, "포트폴리오 요청 성공", portfolios));
    }
}
