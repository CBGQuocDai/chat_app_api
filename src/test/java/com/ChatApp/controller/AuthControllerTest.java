package com.ChatApp.controller;

import com.ChatApp.dto.request.LoginRequest;
import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.dto.request.VerifyOtpRequest;
import com.ChatApp.dto.response.TokenResponse;
import com.ChatApp.dto.response.UserResponse;
import com.ChatApp.entity.User;
import com.ChatApp.enums.Purpose;
import com.ChatApp.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserServiceImpl userService;
    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
//    private RegisterRequest registerRequest;
//    private User newUser;
//    private User u;
//    private UserResponse userResponse;
//    private LoginRequest loginRequest;
//    private VerifyOtpRequest verifyOtpRequest;

}
