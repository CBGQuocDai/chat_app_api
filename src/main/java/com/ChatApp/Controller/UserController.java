package com.ChatApp.Controller;

import com.ChatApp.dto.request.RegisterRequest;
import com.ChatApp.dto.response.Response;
import com.ChatApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public Response<Void> register(@RequestBody RegisterRequest req ) {
        userService.handleRegistration(req);
        log.info("register");
        Response<Void> response = Response.<Void>builder().build();
        log.info("response: {}", response.getCode());
        return Response.<Void>builder().build();
    }
}
