package com.ChatApp.mapper;

import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.dto.response.UserResponse;
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
    public UserResponse fromUserToUserResponse(User u ){
        String url = "http://localhost:8080/api/avatars/"+u.getAvatar();
        return new UserResponse(u.getId(),u.getEmail(), u.getUsername(), url);
    }
}
