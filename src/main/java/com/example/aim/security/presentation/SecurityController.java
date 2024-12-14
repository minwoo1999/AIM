package com.example.aim.security.presentation;

import com.example.aim.security.application.SecurityService;
import com.example.aim.security.application.dto.request.RegisterSecurityRequestDto;
import com.example.aim.security.application.dto.request.UpdateSecurityRequestDto;
import com.example.aim.security.application.dto.response.SecurityResponseDto;
import com.example.aim.shared.dto.CommonResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/securities")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity<CommonResDto<?>> registerSecurity(
            @Valid @RequestBody RegisterSecurityRequestDto registerSecurityRequestDto) {
        Long securityId = securityService.registerSecurity(registerSecurityRequestDto);
        return ResponseEntity.ok(new CommonResDto<>(1, "증권등록성공", securityId));
    }

    @PutMapping("/update-price")
    public ResponseEntity<CommonResDto<?>> updatePrice(
            @Valid @RequestBody UpdateSecurityRequestDto updateSecurityRequestDto) {
        SecurityResponseDto updatedSecurity = securityService.updatePrice(updateSecurityRequestDto);
        return ResponseEntity.ok(new CommonResDto<>(1, "증권업데이트성공", updatedSecurity));

    }

    @DeleteMapping("/{securityCode}")
    public ResponseEntity<CommonResDto<?>> deleteSecurity(@PathVariable String securityCode) {
        securityService.deleteSecurity(securityCode);
        return ResponseEntity.ok(new CommonResDto<>(1, "증권삭제성공", ""));
    }

    @GetMapping("")
    public ResponseEntity<CommonResDto<?>> getAllSecurities() {
        List<SecurityResponseDto> securities = securityService.getAllSecurities();
        return ResponseEntity.ok(new CommonResDto<>(1, "증권조회성공", securities));
    }

}
