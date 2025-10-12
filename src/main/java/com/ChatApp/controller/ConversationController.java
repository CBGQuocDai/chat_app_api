package com.ChatApp.controller;

import com.ChatApp.dto.request.ConversationRequest;
import com.ChatApp.dto.response.ConversationResponse;
import com.ChatApp.dto.response.MessageResponse;
import com.ChatApp.dto.response.Response;
import com.ChatApp.service.impl.ConversationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@Slf4j
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationServiceImpl conversationService;
    @GetMapping
    public List<ConversationResponse> getConversations() {
        return conversationService.getConversationList();
    }
    @PostMapping("/group")
    public ConversationResponse createGroup(@RequestBody ConversationRequest req) {
        return conversationService.createConversation(req);
    }
    @GetMapping("/{conversationId}/messages")
    public List<MessageResponse> getMessages(@PathVariable String conversationId) {
        System.out.println(conversationId);
        return conversationService.getMessages(conversationId);
    }
    @PostMapping("/{conversationId}/leave")
    public Response leaveConversation(@PathVariable String conversationId) {
        conversationService.leaveConversation(conversationId);
        return Response.builder().message("You have left the group.").build();
    }

}
