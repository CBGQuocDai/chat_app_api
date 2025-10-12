package com.ChatApp.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    NOT_FOUND(404, "NOT_FOUND", HttpStatus.NOT_FOUND),
    EMAIL_EXIST(1001, "email already exist", HttpStatus.BAD_REQUEST),
    LOGIN_FAIL(1002, "emails or password is incorrect", HttpStatus.BAD_REQUEST),
    BAD_REQUEST(400, "BAD_REQUEST", HttpStatus.BAD_REQUEST),
    OTP_INCORRECT(1003,"otp is incorrect", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1004,"otp is expired", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1005,"account doesn't exist", HttpStatus.BAD_REQUEST),
    UNAUTHORIZE(1006,"unauthorized", HttpStatus.UNAUTHORIZED),
    USER_NOT_IN_CONVERSATION(1007,"you aren't in conversation", HttpStatus.BAD_REQUEST),
    NOT_HAVE_PERMISSION(1008,"you don't have permission", HttpStatus.BAD_REQUEST),
    SERVER_ERROR(6666,"server error", HttpStatus.INTERNAL_SERVER_ERROR),

    ;

    private final int code;
    private final String message;
    private final HttpStatus status;
    Error(int code,String message,HttpStatus status){
        this.status=status;
        this.code=code;
        this.message=message;
    }
}
