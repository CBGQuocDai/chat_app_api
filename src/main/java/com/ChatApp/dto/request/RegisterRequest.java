package com.ChatApp.dto.request;


import jakarta.validation.constraints.Email;

public record RegisterRequest (@Email String email, String password, String username) { }
