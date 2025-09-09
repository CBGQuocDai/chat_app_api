package com.ChatApp.exceptions;


import com.ChatApp.enums.Error;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    public AppException(Error error) {
        super(error.getMessage());
    }
}
