package com.example.aim.member.presentation;

import com.example.aim.auth.cookie.CookieUtil;
import com.example.aim.member.application.MemberService;
import com.example.aim.member.application.dto.request.MemberRegisterRequestDto;
import com.example.aim.shared.dto.CommonResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResDto<?>> register(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto){
        Boolean result = memberService.userRegister(memberRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResDto<>(1,"회원가입에 성공하였습니다",result));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResDto<?>> logout(HttpServletResponse response, HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            CookieUtil.deleteRefreshTokenCookie(request,response);
            new SecurityContextLogoutHandler().logout(request, response, auth);
            memberService.saveLogoutHistory(auth.getName());
        }
        return new ResponseEntity<>(
                new CommonResDto<>(1,"회원로그아웃성공",""),HttpStatus.OK
        );
    }

    @GetMapping("/balance")
    public ResponseEntity<CommonResDto<?>> getBalance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new CommonResDto<>(0, "인증되지 않은 사용자입니다.", null)
            );
        }

        String username = auth.getName();
        BigDecimal balance = memberService.getBalance(username);

        return ResponseEntity.ok(new CommonResDto<>(1, "잔고 조회 성공", balance));
    }

}