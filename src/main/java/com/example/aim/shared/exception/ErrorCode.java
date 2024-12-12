package com.example.aim.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "REQ_001", "접근 권한이 없습니다."),
    ALREADY_MEMBER_CREATED_EXCEPTION(HttpStatus.BAD_REQUEST, "REQ_002", "이미 계정이 생성되어 있습니다.");



    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status, final String code, final String message){
        this.status = status;
        this.message = message;
        this.code = code;
    }
}