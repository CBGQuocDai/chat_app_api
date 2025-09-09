package com.ChatApp.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "/application-test.yaml")
public class EmailUtilTest {
    @Autowired
    private EmailUtil emailUtil;

    @Test
    void sendEmailTest(){
        emailUtil.sendEmail("dinhquocdai0303@gmail.com", "test", "123456");
    }
}
