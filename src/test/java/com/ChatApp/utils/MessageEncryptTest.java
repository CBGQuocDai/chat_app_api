package com.ChatApp.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "/application-test.yaml")
public class MessageEncryptTest {

    @Autowired
    private MessageEncrypt messageEncrypt;

    @Test
    void encryptTest(){
        String encrypted = messageEncrypt.encrypt("test");
        log.info("encrypted: {}", encrypted);
        Assertions.assertNotEquals("test", encrypted);
        Assertions.assertEquals("test", messageEncrypt.decrypt(encrypted));
    }
}
