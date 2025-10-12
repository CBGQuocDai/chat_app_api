package com.ChatApp.config;

import com.ChatApp.enums.Error;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.service.impl.UserServiceImpl;
import com.ChatApp.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");
            log.info("token: {}", token);
            if(token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                if(redisTemplate.opsForValue().get(token) == null) {
                    log.warn("token login");
                    Claims c =  jwtUtil.getClaimsFromToken(token);;
                    String email = c.getSubject();
                    UserDetails u = userService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("email: {}", email);
                }
                else {
                    log.error("token logout");
                    throw new AppException(Error.UNAUTHORIZE);
                }
            }
        } catch (Exception e) {
            log.error("Error when processing JWT filter: {}", e.getMessage());
            throw new AppException(Error.UNAUTHORIZE);
        }
        filterChain.doFilter(request, response);
    }
}
