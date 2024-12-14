package com.example.aim.consultant.presentation;
import com.example.aim.consultant.application.ConsultationService;
import com.example.aim.consultant.application.dto.request.ConsultationRequestDto;
import com.example.aim.shared.dto.CommonResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping("")
    public ResponseEntity<CommonResDto<Long>> requestConsultation(@RequestBody ConsultationRequestDto requestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new CommonResDto<>(0, "인증되지 않은 사용자입니다.", null)
            );
        }

        String username = auth.getName();
        Long consultationId = consultationService.requestConsultation(requestDto,username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResDto<>(1, "자문 요청이 성공적으로 처리되었습니다.", consultationId));
    }
}