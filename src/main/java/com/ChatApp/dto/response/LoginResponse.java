package com.ChatApp.dto.response;

public record LoginResponse(String accessToken, UserResponse user) {
}
