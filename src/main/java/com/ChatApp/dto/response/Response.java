package com.ChatApp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    @Builder.Default
    private int code=1000;
    private String message;
    private T data;
}
