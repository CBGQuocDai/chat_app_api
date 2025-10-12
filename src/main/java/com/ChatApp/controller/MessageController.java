package com.ChatApp.controller;

import com.ChatApp.dto.response.Response;
import com.ChatApp.service.MessageService;
import com.ChatApp.service.impl.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageServiceImpl messageService;
    @DeleteMapping("/{messageId}")
    public Response deleteMessage(@PathVariable String messageId) {
        log.info("deleteMessage: {}", messageId);
        messageService.deleteMessage(messageId);
        return Response.builder().message("Message deleted successfully").build();
    }
}
