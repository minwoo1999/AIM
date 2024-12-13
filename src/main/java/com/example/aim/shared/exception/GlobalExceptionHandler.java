package com.example.aim.shared.exception;

import com.example.aim.member.exception.error.AlreadyCreatedMemberException;
import com.example.aim.member.exception.error.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 잘못된 요청인 경우
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
        log.info("IllegalArgumentException");
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_EXCEPTION;

        ErrorResponse error = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(e.getMessage())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    /**
     * 접근 권한이 없을 경우
     */
    @ExceptionHandler(UnAuthorizedException.class)
    protected ResponseEntity<?> unAuthorizedException() {
        log.info("UnAuthorizedException");
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_EXCEPTION;

        ErrorResponse error = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    /**
     * 이미 계정이 생성된 경우
     */
    @ExceptionHandler(AlreadyCreatedMemberException.class)
    protected ResponseEntity<?> alreadyCreatedMemberException() {
        log.info("alreadyCreatedMemberException");
        ErrorCode errorCode = ErrorCode.ALREADY_MEMBER_CREATED_EXCEPTION;

        ErrorResponse error = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

}