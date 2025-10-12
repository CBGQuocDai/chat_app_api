package com.ChatApp.controller;

import com.ChatApp.dto.request.LoginRequest;
import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.dto.request.VerifyOtpRequest;
import com.ChatApp.dto.response.LoginResponse;
import com.ChatApp.dto.response.Response;
import com.ChatApp.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthServiceImpl authService;
    @PostMapping("/register")
    public Response register(@RequestBody RegisterRequest req ) {
        authService.handleRegistration(req);
        log.info("register");
        return Response.builder()
                .message("Đăng ký thành công, vui lòng kiểm tra email để xác thực.")
                .build();
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return authService.handleLogin(req);
    }
    @PostMapping("/verify-otp")
    public LoginResponse verifyOtp(@RequestBody VerifyOtpRequest req) {
        return authService.verifyOTP(req);
    }
    @PostMapping("/logout")
    public Response logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return Response.builder().message("Đăng xuất thành công.").build();
    }
}
