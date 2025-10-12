package com.ChatApp.service;

import com.ChatApp.dto.request.LoginRequest;
import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.dto.request.VerifyOtpRequest;
import com.ChatApp.dto.response.TokenResponse;
import com.ChatApp.dto.response.UserResponse;
import com.ChatApp.entity.User;
import com.ChatApp.enums.Error;
import com.ChatApp.enums.Purpose;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.mapper.UserMapper;
import com.ChatApp.repository.UserRepository;
import com.ChatApp.service.impl.UserServiceImpl;
import com.ChatApp.utils.EmailUtil;
import com.ChatApp.utils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private UserMapper userMapper;
    @MockitoBean
    private EmailUtil emailUtil;
    @MockitoBean
    private JwtUtil jwtUtil;
    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;
    @MockitoBean
    private ValueOperations<String, Object> valueOperations;

    @MockitoBean
    private Authentication authentication;

}
