package com.ChatApp.dto.request;

public record ChangePasswordRequest(String oldPassword, String newPassword) {
}
