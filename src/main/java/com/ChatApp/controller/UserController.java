package com.ChatApp.controller;

import com.ChatApp.dto.request.*;
import com.ChatApp.dto.response.Response;
import com.ChatApp.dto.response.UserResponse;
import com.ChatApp.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserServiceImpl userService;
    @GetMapping("/me")
    public UserResponse getUserInfo() {
        return userService.getUserInfo();
    }
    @PutMapping("/me")
    public UserResponse updateUserInfo(@RequestBody UpdateUserRequest req) {
        return userService.updateUserInfo(req);
    }

    @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response uploadAvatar(@RequestParam("avatar") MultipartFile file) {
        userService.uploadAvatar(file);
        return Response.builder().message("success").build();
    }
    @PostMapping("/me/change-password")
    public Response changePassword(@RequestBody ChangePasswordRequest req) {
        return userService.changePassword(req);
    }
    @PostMapping("/{userId}/block")
    public Response blockUser(@PathVariable String userId) {
        return userService.blockUser(userId);
    }
    @DeleteMapping("/{userId}/unblock")
    public Response unblockUser(@PathVariable String userId) {
        return userService.unblockUser(userId);
    }
}
