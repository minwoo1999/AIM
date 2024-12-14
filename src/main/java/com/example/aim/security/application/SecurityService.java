package com.example.aim.security.application;

import com.example.aim.security.application.dto.request.RegisterSecurityRequestDto;
import com.example.aim.security.application.dto.request.UpdateSecurityRequestDto;
import com.example.aim.security.application.dto.response.SecurityResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface SecurityService {
    Long registerSecurity(RegisterSecurityRequestDto registerSecurityRequestDto);

    SecurityResponseDto updatePrice(UpdateSecurityRequestDto updateSecurityReqequestDto);

    void deleteSecurity(String securityCode);

    List<SecurityResponseDto> getAllSecurities();


}
