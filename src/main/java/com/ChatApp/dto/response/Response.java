package com.ChatApp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String message;
}
