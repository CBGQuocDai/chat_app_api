package com.ChatApp.service;

import com.ChatApp.dto.request.ConversationRequest;
import com.ChatApp.dto.response.ConversationResponse;
import com.ChatApp.dto.response.MessageResponse;

import java.util.List;

public interface ConversationService {
    List<ConversationResponse> getConversationList();
    ConversationResponse createConversation(ConversationRequest req);
    List<MessageResponse> getMessages(String conversationId);
    void leaveConversation(String conversationId);
    void clearConversationHistory(String conversationId);
    ConversationResponse getConversation(String conversationId);
}
