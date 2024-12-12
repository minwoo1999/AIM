package com.example.aim.member.application;

import com.example.aim.member.application.dto.request.MemberRegisterRequestDto;
import com.example.aim.member.domain.UserEntity;

import java.util.Optional;

public interface MemberService {
    Optional<UserEntity> findByUsername(String username);

    Boolean userRegister(MemberRegisterRequestDto memberRegisterRequestDto);
}
