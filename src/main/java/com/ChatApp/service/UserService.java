package com.ChatApp.service;

import com.ChatApp.dto.request.RegisterRequest;

public interface UserService {
    String handleLogin(String email, String password);
    void handleRegistration(RegisterRequest req);
}
