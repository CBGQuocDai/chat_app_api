package com.ChatApp.service.impl;

import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.entity.User;
import com.ChatApp.enums.Error;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.mapper.UserMapper;
import com.ChatApp.repository.UserRepository;
import com.ChatApp.service.UserService;
import com.ChatApp.utils.EmailUtil;
import com.ChatApp.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailUtil emailUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String handleLogin(String email, String password) {
        try {
            User u= userRepository.getUsersByEmail(email);
            if(passwordEncoder.matches(password, u.getPassword())) {
                return "success";
            }
            else {
                throw new AppException(Error.LOGIN_FAIL);
            }
        } catch (AppException e) {
            throw new AppException(Error.LOGIN_FAIL);
        }
    }

    @Override
    public void handleRegistration(RegisterRequest req) {
        User newUser = userMapper.RegisterRequestToUser(req);
        User u = userRepository.findUsersByEmail(newUser.getEmail());
        if(u != null) {
            if(u.isActive()) {
                throw new AppException(Error.EMAIL_EXIST);
            }
            userRepository.delete(u);
        }
        newUser.setActive(false);
        newUser.setAvatar("default.png");
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        userRepository.save(newUser);
        String otp = OtpUtil.generateOtp();
        emailUtil.sendEmail(newUser.getEmail(), "ChatApp OTP", otp);
    }
}
