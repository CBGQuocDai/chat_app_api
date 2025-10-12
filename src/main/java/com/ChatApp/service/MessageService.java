package com.ChatApp.service;


public interface MessageService {
    void deleteMessage(String messageId);
    void sendMessage();
    void sendFile();
}
