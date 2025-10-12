package com.ChatApp.service;

import com.ChatApp.dto.request.LoginRequest;
import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.dto.request.VerifyOtpRequest;
import com.ChatApp.dto.response.LoginResponse;
import com.ChatApp.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginResponse handleLogin(LoginRequest req);
    void handleRegistration(RegisterRequest req);
    LoginResponse verifyOTP(VerifyOtpRequest req);
    void logout(String token);
}
