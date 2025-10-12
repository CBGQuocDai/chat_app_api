package com.ChatApp.service.impl;

import com.ChatApp.dto.request.*;
import com.ChatApp.dto.response.LoginResponse;
import com.ChatApp.dto.response.Response;
import com.ChatApp.dto.response.TokenResponse;
import com.ChatApp.dto.response.UserResponse;
import com.ChatApp.entity.User;
import com.ChatApp.enums.Error;
import com.ChatApp.enums.Purpose;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.mapper.UserMapper;
import com.ChatApp.repository.UserRepository;
import com.ChatApp.service.UserService;
import com.ChatApp.utils.EmailUtil;
import com.ChatApp.utils.JwtUtil;
import com.ChatApp.utils.OtpUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AmazonS3 amazonS3;
    @Value("${s3.bucket}")
    private String bucketName;
    @Override
    public UserResponse getUserInfo() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User u = userRepository.getUsersByEmail(user.getEmail());
        log.info("u: {}", u);
        if (u == null) {
            throw new AppException(Error.USER_NOT_EXIST);
        }
        return userMapper.fromUserToUserResponse(u);
    }
    @Override
    public S3ObjectInputStream getAvatar(String file) {
        try {
            S3Object avatar = amazonS3.getObject(bucketName, file);
            return  avatar.getObjectContent();
        } catch (Exception e) {
            throw new AppException(Error.USER_NOT_EXIST);
        }

    }

    @Override
    public void uploadAvatar(MultipartFile file) {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getUsersByEmail(u.getEmail());
        try {
            InputStream inputStream = file.getInputStream();
            String avatarName = user.getId() + "_" + new Date().getTime() + ".png";
            amazonS3.putObject(bucketName, avatarName, inputStream, null);
            user.setAvatar(avatarName);
            userRepository.save(user);
        } catch (IOException e) {
            log.error("Error when uploading avatar: {}", e.getMessage());
            throw new AppException(Error.SERVER_ERROR);
        }
    }

    @Override
    public List<UserResponse> searchUser(String keyword) {
        return List.of();
    }
    @Override
    public UserResponse updateUserInfo(UpdateUserRequest req) {
        return null;
    }

    @Override
    public Response changePassword(ChangePasswordRequest req) {
        return null;
    }

    @Override
    public Response blockUser(String id) {
        return null;
    }

    @Override
    public Response unblockUser(String id) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails u =  userRepository.getUsersByEmailAndActive(username,true);
        if(u == null) {
            throw new AppException(Error.USER_NOT_EXIST);
        }
        return u;
    }
}
