package com.ChatApp.service;

import com.ChatApp.dto.response.ConversationResponse;
import com.ChatApp.dto.response.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {
    List<ConversationResponse> getConversation();
    List<MessageResponse> getMessages(String conversationId);
    void deleteMessage(String conversationId, String messageId);
    void sendMessage(String conversationId, String message);
}
