package com.example.aim.member.application.impl;

import com.example.aim.member.application.MemberService;
import com.example.aim.member.application.dto.request.MemberRegisterRequestDto;
import com.example.aim.member.domain.ActivityType;
import com.example.aim.member.domain.UserActivityLogEntity;
import com.example.aim.member.domain.UserEntity;
import com.example.aim.member.domain.repository.MemberRepository;
import com.example.aim.member.domain.repository.UserActivityLogRepository;
import com.example.aim.member.exception.error.AlreadyCreatedMemberException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;

    private final UserActivityLogRepository userActivityLogRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public Boolean userRegister(@Valid @RequestBody MemberRegisterRequestDto memberRegisterRequestDto) {
        final String password = memberRegisterRequestDto.getPassword();
        final String username = memberRegisterRequestDto.getUsername();

        if(memberRepository.existsMemberByUsername(username)){
            throw new AlreadyCreatedMemberException("이미 생성된 계정입니다(식별 정보 중복)");
        }

        UserEntity member = memberRepository.save(memberRegisterRequestDto.toEntity(username, passwordEncode(password)));

        return true;
    }

    @Override
    @Transactional
    public void saveLoginHistory(String username) {
        UserEntity userEntity = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다"));
        UserActivityLogEntity userActivityLogEntity = UserActivityLogEntity.builder()
                .user(userEntity)
                .activityType(ActivityType.LOGIN)
                .build();

        userActivityLogRepository.save(userActivityLogEntity);
    }

    @Transactional
    public void saveLogoutHistory(String username) {
        UserEntity userEntity = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다"));
        UserActivityLogEntity userActivityLogEntity = UserActivityLogEntity.builder()
                .user(userEntity)
                .activityType(ActivityType.LOGOUT)
                .build();
        userActivityLogRepository.save(userActivityLogEntity);
    }

    @Override
    public BigDecimal getBalance(String username) {
        UserEntity user = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
        return user.getBalance();
    }


    @Override
    public UserEntity findMemberByUserId(String userId) {
        return memberRepository.findByUsername(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));
    }
    private String passwordEncode(String password){
        return bCryptPasswordEncoder.encode(password);
    }

}