package com.ChatApp.config;

import com.ChatApp.dto.response.Response;
import com.ChatApp.enums.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Error error = Error.UNAUTHORIZE;
        ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        Response resp = Response.builder()
                .message(error.getMessage()).build();
        response.setContentType("application/json");
        String content = objectMapper.writeValueAsString(resp);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(content);
        response.flushBuffer();
    }
}
