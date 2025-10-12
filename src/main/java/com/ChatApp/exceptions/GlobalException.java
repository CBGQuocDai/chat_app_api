package com.ChatApp.exceptions;


import com.ChatApp.dto.response.Response;
import com.ChatApp.enums.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<Response> handleAppException(AppException e) {
        Error error = e.getError();
        Response resp = Response.builder()
                .message(error.getMessage())
                .build();
        return new ResponseEntity<>(resp, error.getStatus());
    }
}
