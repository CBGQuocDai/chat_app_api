package com.ChatApp.controller;

import com.ChatApp.dto.response.Response;
import com.ChatApp.enums.Error;
import com.ChatApp.service.impl.UserServiceImpl;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequestMapping("/api/avatars")
@RequiredArgsConstructor
@RestController
public class AvatarController {
    private final UserServiceImpl userService;

    @GetMapping("/{file}")
    public void getAvatar(@PathVariable String file, HttpServletResponse response) throws IOException {

        try {
            S3ObjectInputStream in = userService.getAvatar(file);
            response.setContentType("image/png");
            in.transferTo(response.getOutputStream());
//            response.flushBuffer();
            in.close();
            log.info("getAvatar: {}", file);
        } catch (IOException e) {
            log.error("Error when getting avatar: {}", e.getMessage());
            Response resp = Response.<Void>builder()
                    .message(Error.SERVER_ERROR.getMessage()).build();
            ObjectMapper objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(resp);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(content);
        }
    }
}
