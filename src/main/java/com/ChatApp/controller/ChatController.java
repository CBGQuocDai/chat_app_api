package com.ChatApp.controller;


import com.ChatApp.dto.request.SendFileRequest;
import com.ChatApp.dto.request.SendMessageRequest;
import com.ChatApp.dto.response.MessageResponse;
import com.ChatApp.entity.Message;
import com.ChatApp.entity.User;
import com.ChatApp.enums.TypeMessage;
import com.ChatApp.mapper.UserMapper;
import com.ChatApp.service.impl.FileServiceImpl;
import com.ChatApp.service.impl.MessageServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageServiceImpl messageService;
    private final UserMapper userMapper;
    private final FileServiceImpl fileService;
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload SendMessageRequest message, Principal principal) throws JsonProcessingException {
        log.info("chat: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        if(message.type()== TypeMessage.TEXT){
            MessageResponse msg = MessageResponse.builder()
                .content(message.content())
                .sendAt(new Date())
                .type(message.type())
                .replyToMessageId(message.replyToMessageId())
                .conversationId(message.conversationId())
                .build();
            if(principal instanceof UsernamePasswordAuthenticationToken) {
                User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
                msg.setSender(userMapper.fromUserToUserResponse(user));
                Message newMessage  =  messageService.sendMessage(message, user);
                msg.setId(newMessage.getId());
                System.out.println(msg);
                messagingTemplate.convertAndSend("/queue/"+msg.getConversationId(),
                        objectMapper.writer().writeValueAsString(msg));
            }
        }
    }
}
