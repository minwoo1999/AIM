package com.example.aim.member.exception.error;


public class AlreadyCreatedMemberException extends RuntimeException{
    public AlreadyCreatedMemberException() {
        super();
    }
    public AlreadyCreatedMemberException(String message, Throwable cause) {
        super(message, cause);
    }
    public AlreadyCreatedMemberException(String message) {
        super(message);
    }
    public AlreadyCreatedMemberException(Throwable cause) {
        super(cause);
    }
}