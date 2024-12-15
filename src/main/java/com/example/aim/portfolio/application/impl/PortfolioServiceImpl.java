package com.example.aim.portfolio.application.impl;

import com.example.aim.consultant.domain.ConsultationEntity;
import com.example.aim.consultant.domain.repository.ConsultationRepository;
import com.example.aim.member.domain.UserEntity;
import com.example.aim.member.domain.repository.MemberRepository;
import com.example.aim.portfolio.application.PortfolioService;
import com.example.aim.portfolio.application.dto.response.CreatePortfolioResponseDto;
import com.example.aim.portfolio.domain.PortfolioEntity;
import com.example.aim.portfolio.domain.PortfolioType;
import com.example.aim.portfolio.domain.repository.PortfolioRepository;
import com.example.aim.security.domain.SecurityEntity;
import com.example.aim.security.domain.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioServiceImpl implements PortfolioService {

    private final ConsultationRepository consultationRepository;
    private final SecurityRepository securityRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    @Override
    public List<CreatePortfolioResponseDto> createPortfolio(Long consultationId) {
        ConsultationEntity consultation = findConsultation(consultationId);
        UserEntity user = consultation.getUser();
        PortfolioType portfolioType = consultation.getPortfolioType();

        validateUserBalance(user);

        BigDecimal allocationBalance = getAllocationBalance(user.getBalance(), portfolioType);

        List<SecurityEntity> sortedSecurities = getSortedSecurities(portfolioType);

        List<PortfolioEntity> portfolios = allocatePortfolio(consultation, sortedSecurities, allocationBalance, user);

        return portfolios.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private ConsultationEntity findConsultation(Long consultationId) {
        return consultationRepository.findById(consultationId)
                .orElseThrow(() -> new IllegalArgumentException("Consultation not found"));
    }

    private void validateUserBalance(UserEntity user) {
        if (user.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("User has insufficient balance");
        }
    }

    private BigDecimal getAllocationBalance(BigDecimal totalBalance, PortfolioType portfolioType) {
        if (portfolioType == PortfolioType.HIGH_RISK) {
            return totalBalance;
        } else if (portfolioType == PortfolioType.MEDIUM_RISK) {
            return totalBalance.divide(BigDecimal.valueOf(2));
        } else {
            throw new IllegalArgumentException("Unsupported portfolio type");
        }
    }

    private List<SecurityEntity> getSortedSecurities(PortfolioType portfolioType) {
        List<SecurityEntity> securities = securityRepository.findAll();
        return securities.stream()
                .sorted(portfolioType == PortfolioType.HIGH_RISK
                        ? Comparator.comparing(SecurityEntity::getPrice).reversed()
                        : Comparator.comparing(SecurityEntity::getPrice))
                .collect(Collectors.toList());
    }

    private List<PortfolioEntity> allocatePortfolio(
            ConsultationEntity consultation,
            List<SecurityEntity> sortedSecurities,
            BigDecimal allocationBalance,
            UserEntity user
    ) {
        final AtomicReference<BigDecimal> remainingBalance = new AtomicReference<>(allocationBalance);

        List<PortfolioEntity> portfolios = sortedSecurities.stream()
                .filter(security -> remainingBalance.get().compareTo(security.getPrice()) >= 0)
                .map(security -> {
                    BigDecimal cost = security.getPrice();
                    remainingBalance.updateAndGet(balance -> balance.subtract(cost));

                    return PortfolioEntity.builder()
                            .consultation(consultation)
                            .security(security)
                            .quantity(1)
                            .build();
                })
                .collect(Collectors.toList());

        user.withdraw(allocationBalance.subtract(remainingBalance.get()));
        portfolioRepository.saveAll(portfolios);
        return portfolios;
    }

    private CreatePortfolioResponseDto toResponseDto(PortfolioEntity portfolio) {
        return CreatePortfolioResponseDto.builder()
                .portfolioId(portfolio.getId())
                .consultationId(portfolio.getConsultation().getId())
                .securityId(portfolio.getSecurity().getId())
                .securityCode(portfolio.getSecurity().getCode())
                .securityName(portfolio.getSecurity().getName())
                .securityPrice(portfolio.getSecurity().getPrice())
                .quantity(portfolio.getQuantity())
                .build();
    }
}
