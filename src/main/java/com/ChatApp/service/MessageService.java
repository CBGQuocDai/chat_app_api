package com.ChatApp.service;


import com.ChatApp.dto.request.SendFileRequest;
import com.ChatApp.dto.request.SendMessageRequest;
import com.ChatApp.dto.response.MessageResponse;
import com.ChatApp.entity.Message;
import com.ChatApp.entity.User;
import org.springframework.core.io.InputStreamResource;

public interface MessageService {
    void deleteMessage(String messageId);
    Message sendMessage(SendMessageRequest req, User u);
    MessageResponse uploadFile(SendFileRequest file);
    InputStreamResource downloadFile(String messageId);
}
