package com.ChatApp.mapper;

import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public User RegisterRequestToUser(RegisterRequest req) {
        return User.builder()
                .username(req.username())
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .build();
    }
}
