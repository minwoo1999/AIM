package com.example.aim.member.presentation;

import com.example.aim.member.application.MemberService;
import com.example.aim.member.application.dto.request.MemberRegisterRequestDto;
import com.example.aim.shared.dto.CommonResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}