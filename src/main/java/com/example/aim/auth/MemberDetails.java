package com.example.aim.auth;

import com.example.aim.member.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class MemberDetails implements UserDetails {
    private final UserEntity member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("MemberDetails.getAuthorities");
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> member.getRole().name());
        return collect;
    }
    @Override
    public String getPassword() {
        return member.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO
        return true;
    }

}
