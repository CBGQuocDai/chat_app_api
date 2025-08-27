package com.ChatApp.service.impl;

import com.ChatApp.entity.User;
import com.ChatApp.repository.UserRepository;
import com.ChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public String handleLogin(String email, String password) {

        return "";
    }
}
