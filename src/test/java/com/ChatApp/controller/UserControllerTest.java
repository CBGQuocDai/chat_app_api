package com.ChatApp.controller;

import com.ChatApp.ChatAppApplication;
import com.ChatApp.dto.request.RegisterRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//        classes = ChatAppApplication.class)
//@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-test.yaml")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

}
