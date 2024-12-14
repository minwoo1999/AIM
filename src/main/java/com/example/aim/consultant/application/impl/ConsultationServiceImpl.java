package com.example.aim.consultant.application.impl;

import com.example.aim.consultant.application.ConsultationService;
import com.example.aim.consultant.application.dto.request.ConsultationRequestDto;
import com.example.aim.consultant.domain.ConsultationEntity;
import com.example.aim.consultant.domain.repository.ConsultationRepository;
import com.example.aim.consultant.exception.error.BusinessException;
import com.example.aim.member.application.MemberService;
import com.example.aim.member.domain.UserEntity;
import com.example.aim.portfolio.domain.PortfolioType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final MemberService memberService;
    private final ConsultationRepository consultationRepository;

    @Transactional
    public Long requestConsultation(ConsultationRequestDto requestDto,String username) {
        // 사용자 검증
        UserEntity user = memberService.findMemberByUserId(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 잔고 검증 및 포트폴리오 위험도별 처리
        if (requestDto.getPortfolioType() == PortfolioType.HIGH_RISK) {
            if (user.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BusinessException("잔고가 부족합니다.");
            }
            user.withdraw(user.getBalance());
        } else if (requestDto.getPortfolioType() == PortfolioType.MEDIUM_RISK) {
            BigDecimal halfBalance = user.getBalance().divide(BigDecimal.valueOf(2));
            if (user.getBalance().compareTo(halfBalance) < 0) {
                throw new BusinessException("잔고가 부족합니다.");
            }
            user.withdraw(halfBalance);
        } else {
            throw new BusinessException("유효하지 않은 포트폴리오 유형입니다.");
        }

        // 자문 요청 저장
        ConsultationEntity consultation = ConsultationEntity.builder()
                .user(user)
                .portfolioType(requestDto.getPortfolioType())
                .build();

        consultationRepository.save(consultation);
        return consultation.getId();
    }
}
