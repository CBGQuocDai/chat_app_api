package com.ChatApp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/chat")
    public void chat(String message) {
        log.info("chat: {}", message);
        messagingTemplate.convertAndSend("/topic/chat", message);
    }
}
