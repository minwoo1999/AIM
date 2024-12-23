package com.example.aim.transcation.presentation;

import com.example.aim.auth.cookie.CookieUtil;
import com.example.aim.member.application.MemberService;
import com.example.aim.member.application.dto.request.MemberRegisterRequestDto;
import com.example.aim.shared.dto.CommonResDto;
import com.example.aim.transcation.application.TransactionService;
import com.example.aim.transcation.application.dto.request.TransactionDepositRequestDto;
import com.example.aim.transcation.application.dto.response.TransactionDepositResponseDto;
import com.example.aim.transcation.application.dto.response.TransactionHistoryListResponseDto;
import com.example.aim.transcation.application.dto.response.TransactionWithdrawResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {


    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<CommonResDto<?>> deposit(@RequestBody TransactionDepositRequestDto transactionDepositRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new CommonResDto<>(0, "인증되지 않은 사용자입니다.", null)
            );
        }
        String username = auth.getName();
        TransactionDepositResponseDto result = transactionService.deposit(username, transactionDepositRequestDto.getAmount());
        return ResponseEntity.ok(new CommonResDto<>(1, "입금 성공", result));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<CommonResDto<?>> withdraw(@RequestBody TransactionWithdrawResponseDto transactionWithdrawResponseDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new CommonResDto<>(0, "인증되지 않은 사용자입니다.", null)
            );
        }
        String username = auth.getName();
        TransactionWithdrawResponseDto result = transactionService.withdraw(username, transactionWithdrawResponseDto.getAmount());
        return ResponseEntity.ok(new CommonResDto<>(1, "출금 성공", result));
    }

    @GetMapping("/history")
    public ResponseEntity<CommonResDto<?>> getTransactionHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new CommonResDto<>(0, "인증되지 않은 사용자입니다.", null)
            );
        }
        String username = auth.getName();
        List<TransactionHistoryListResponseDto> result = transactionService.getTransactionHistory(username);
        return ResponseEntity.ok(new CommonResDto<>(1, "거래 내역 조회 성공", result));
    }

}