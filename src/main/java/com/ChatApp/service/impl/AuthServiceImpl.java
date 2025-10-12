package com.ChatApp.service.impl;

import com.ChatApp.dto.request.LoginRequest;
import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.dto.request.VerifyOtpRequest;
import com.ChatApp.dto.response.LoginResponse;
import com.ChatApp.dto.response.Response;
import com.ChatApp.dto.response.TokenResponse;
import com.ChatApp.dto.response.UserResponse;
import com.ChatApp.entity.User;
import com.ChatApp.enums.Error;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.mapper.UserMapper;
import com.ChatApp.repository.UserRepository;
import com.ChatApp.service.AuthService;
import com.ChatApp.utils.EmailUtil;
import com.ChatApp.utils.JwtUtil;
import com.ChatApp.utils.OtpUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailUtil emailUtil;
    private final JwtUtil jwtUtil;
    private final org.springframework.data.redis.core.RedisTemplate<String, Object> redisTemplate;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    @Override
    public LoginResponse handleLogin(LoginRequest req) {
        log.info("req: {}", req.email());
        User u= userRepository.getUsersByEmail(req.email());
        if(u == null) {
            log.info("user not exist");
            throw new AppException(com.ChatApp.enums.Error.LOGIN_FAIL);
        }
        if(passwordEncoder.matches(req.password(), u.getPassword())) {
            String token = jwtUtil.generateToken(req.email());
            UserResponse user = userMapper.fromUserToUserResponse(u);
            return new LoginResponse(token, user);
        }
        else {
            log.info("password not match");
            throw new AppException(com.ChatApp.enums.Error.LOGIN_FAIL);
        }
    }

    @Override
    public void handleRegistration(RegisterRequest req) {
        User newUser = userMapper.RegisterRequestToUser(req);
        User u = userRepository.findUsersByEmail(newUser.getEmail());
        if(u != null) {
            if(u.getActive()) {
                throw new AppException(com.ChatApp.enums.Error.EMAIL_EXIST);
            }
            userRepository.delete(u);
        }
        log.info("newUser: {}", newUser);
        newUser.setActive(false);
        newUser.setAvatar("default.png");
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        userRepository.save(newUser);
        String otp = OtpUtil.generateOtp();
        emailUtil.sendEmail(newUser.getEmail(), "ChatApp OTP", otp);
        redisTemplate.opsForValue().set(newUser.getEmail(), otp,120 , TimeUnit.SECONDS );
    }

    @Override
    public LoginResponse verifyOTP(VerifyOtpRequest req) {
        String otpInRedis = (String) redisTemplate.opsForValue().get(req.email());
        if(otpInRedis == null) {
            throw new AppException(com.ChatApp.enums.Error.OTP_EXPIRED);
        }
        if(!otpInRedis.equals(req.otp())) {
            throw new AppException(com.ChatApp.enums.Error.OTP_INCORRECT);
        }
        redisTemplate.delete(req.email());
        User u = userRepository.getUsersByEmail(req.email());
        if(u == null) {
            throw new AppException(Error.USER_NOT_EXIST);
        }
        u.setActive(true);
        u.setUpdatedAt(new Date());
        UserResponse userResponse = userMapper.fromUserToUserResponse(userRepository.save(u));
        String token = jwtUtil.generateToken(req.email());
        return new LoginResponse(token, userResponse);
    }

    @Override
    public void logout(String token) {
        try {
            Claims c= jwtUtil.getClaimsFromToken(token);
            redisTemplate.opsForValue().set(token, c.getSubject() ,
                    c.getExpiration().getTime() - new Date().getTime(),
                    TimeUnit.MICROSECONDS);
        } catch (Exception e) {
            log.error("Error when logout: {}", e.getMessage());
        }

    }
}
