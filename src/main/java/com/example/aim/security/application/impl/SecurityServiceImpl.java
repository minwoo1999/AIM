package com.example.aim.security.application.impl;

import com.example.aim.security.application.SecurityService;
import com.example.aim.security.application.dto.request.RegisterSecurityRequestDto;
import com.example.aim.security.application.dto.request.UpdateSecurityRequestDto;
import com.example.aim.security.application.dto.response.SecurityResponseDto;
import com.example.aim.security.domain.SecurityEntity;
import com.example.aim.security.domain.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityServiceImpl implements SecurityService {

    private final SecurityRepository securityRepository;

    @Transactional
    @Override
    public Long registerSecurity(RegisterSecurityRequestDto registerSecurityRequestDto) {
        SecurityEntity security = SecurityEntity.builder()
                .code(registerSecurityRequestDto.getSecurityCode())
                .name(registerSecurityRequestDto.getName())
                .price(registerSecurityRequestDto.getPrice())
                .build();
        return securityRepository.save(security).getId();
    }

    @Transactional
    @Override
    public SecurityResponseDto updatePrice(UpdateSecurityRequestDto updateSecurityReqequestDto) {
        SecurityEntity security = securityRepository.findByCode(updateSecurityReqequestDto.getSecurityCode())
                .orElseThrow(() -> new IllegalArgumentException("Security not found"));
        security.updatePrice(updateSecurityReqequestDto.getPrice());
        return SecurityResponseDto.builder()
                .id(security.getId())
                .securityCode(security.getCode())
                .name(security.getName())
                .price(security.getPrice())
                .build();
    }

    @Transactional
    @Override
    public void deleteSecurity(String securityCode) {
        SecurityEntity security = securityRepository.findByCode(securityCode)
                .orElseThrow(() -> new IllegalArgumentException("Security not found"));
        securityRepository.delete(security);
    }

    @Override
    public List<SecurityResponseDto> getAllSecurities() {
        return securityRepository.findAll().stream()
                .map(security -> SecurityResponseDto.builder()
                        .id(security.getId())
                        .securityCode(security.getCode())
                        .name(security.getName())
                        .price(security.getPrice())
                        .build())
                .collect(Collectors.toList());
    }
}