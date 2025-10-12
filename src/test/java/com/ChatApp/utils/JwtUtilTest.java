package com.ChatApp.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
public class JwtUtilTest {
    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;
    @MockitoBean
    private ValueOperations<String, Object> valueOperations;
    @Autowired
    private JwtUtil jwtUtil;
    @Test
    void testUtil(){
        String token = jwtUtil.generateToken("<EMAIL>");
        System.out.println(token);
        boolean verify = jwtUtil.verifyToken(token);
        Assertions.assertTrue(verify);
        String email = jwtUtil.getClaimsFromToken(token).getSubject();
        String expected = "<EMAIL>";
        Assertions.assertEquals(expected, email);
        String issuer = jwtUtil.getClaimsFromToken(token).getIssuer();
        Assertions.assertEquals("daidq", issuer);
    }

    @Test
    void testUtil_Expired() throws InterruptedException {
        String token = jwtUtil.generateToken("<EMAIL>");

        Thread.sleep(1050L);

        System.out.println(token);
        Assertions.assertFalse(jwtUtil.verifyToken(token));
    }
    @Test
    void testUtil_WrongToken(){
        String token = "<PASSWORD>";
        Assertions.assertFalse(jwtUtil.verifyToken(token));
    }
}
