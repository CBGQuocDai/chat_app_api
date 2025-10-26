package com.ChatApp.config;

import com.ChatApp.entity.User;
import com.ChatApp.enums.Error;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.service.impl.UserServiceImpl;
import com.ChatApp.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final UserServiceImpl userService;
    @Override
    public Message<?> preSend (Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(accessor != null && accessor.getNativeHeader("Authorization") != null) {
            String token  = String.valueOf(accessor.getNativeHeader("Authorization"))
                    .substring(7);
            try {
                log.info("token interceptor: {}", token);
                Claims claims = jwtUtil.getClaimsFromToken(token);
                UserDetails u = userService.loadUserByUsername(claims.getSubject());
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                accessor.setUser(authentication);
            } catch (Exception e) {
                throw new AppException(Error.UNAUTHORIZE);
            }
        }
        return message;
    }
}
