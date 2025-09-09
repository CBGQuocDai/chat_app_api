package com.ChatApp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    @Builder.Default
    private int code=1000;
    @Builder.Default
    private String message="Success";
    private T data;
}
