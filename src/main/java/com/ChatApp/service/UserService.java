package com.ChatApp.service;

import com.ChatApp.dto.request.*;
import com.ChatApp.dto.response.LoginResponse;
import com.ChatApp.dto.response.Response;
import com.ChatApp.dto.response.TokenResponse;
import com.ChatApp.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface UserService {
    UserResponse getUserInfo();
    InputStream getAvatar(String id) throws IOException;
    void uploadAvatar(MultipartFile file);
    List<UserResponse> searchUser(String keyword);
    UserResponse updateUserInfo(UpdateUserRequest req);
    Response changePassword(ChangePasswordRequest req);
    Response blockUser(String id);
    Response unblockUser(String id);
}
