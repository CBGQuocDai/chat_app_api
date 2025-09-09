package com.ChatApp.service;

import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.entity.User;
import com.ChatApp.mapper.UserMapper;
import com.ChatApp.repository.UserRepository;
import com.ChatApp.service.impl.UserServiceImpl;
import com.ChatApp.utils.EmailUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@TestPropertySource(locations = "/application-test.yaml")
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private UserMapper userMapper;
    @MockitoBean
    private EmailUtil emailUtil;
    private RegisterRequest registerRequest;
    private User newUser;
    private User u;


    @BeforeEach
    void setUp() {
        this.registerRequest = new RegisterRequest("abc@gmai.com", "123456", "heheh");
        this.u = User.builder()
                .email("abc@gmail.com")
                .username("abcde")
                .build();
        this.newUser = User.builder()
                .email("abc@gmail.com")
                .username("abcde")
                .password("<PASSWORD>")
                .build();
    }

    @Test
    void handleRegisterTest_Success_1() {
        Mockito.when(userMapper.RegisterRequestToUser(registerRequest)).thenReturn(newUser);
        Mockito.when(userRepository.findUsersByEmail(newUser.getEmail())).thenReturn(null);
        Mockito.doNothing().when(emailUtil).sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        userService.handleRegistration(registerRequest);
    }

    @Test
    void handleRegisterTest_Success_2() {
        u.setActive(false);
        Mockito.when(userMapper.RegisterRequestToUser(registerRequest)).thenReturn(newUser);
        Mockito.when(userRepository.findUsersByEmail(newUser.getEmail())).thenReturn(u);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(u);
        Mockito.doNothing().when(emailUtil).sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        userService.handleRegistration(registerRequest);

    }

    @Test
    void handleRegisterTest_EmailExisted() {
        u.setActive(true);
        Mockito.when(userMapper.RegisterRequestToUser(registerRequest)).thenReturn(newUser);
        Mockito.when(userRepository.findUsersByEmail(newUser.getEmail())).thenReturn(u);
    }


}
