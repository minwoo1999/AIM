package com.example.aim.member.application;

import com.example.aim.member.application.dto.request.MemberRegisterRequestDto;
import com.example.aim.member.domain.UserEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface MemberService {
    Optional<UserEntity> findByUsername(String username);

    Boolean userRegister(MemberRegisterRequestDto memberRegisterRequestDto);

    UserEntity findMemberByUserId(String userId);

    void saveLoginHistory(String username);

    void saveLogoutHistory(String username);

    BigDecimal getBalance(String username);
}
