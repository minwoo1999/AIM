package com.example.aim.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    MEMBER("ROLE_MEMBER"),MANAGER("ROLE_MANAGER"),ADMIN("ROLE_ADMIN");
    private final String key;
}