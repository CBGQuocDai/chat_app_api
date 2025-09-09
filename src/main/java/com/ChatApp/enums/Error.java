package com.ChatApp.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    NOT_FOUND(404, "NOT_FOUND", HttpStatus.NOT_FOUND),
    EMAIL_EXIST(1001, "email already exist", HttpStatus.BAD_REQUEST),
    LOGIN_FAIL(1002, "emails or password is incorrect", HttpStatus.BAD_REQUEST),
    BAD_REQUEST(400, "BAD_REQUEST", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus status;
    Error(int code,String message,HttpStatus status){
        this.status=status;
        this.code=code;
        this.message=message;
    }
}
