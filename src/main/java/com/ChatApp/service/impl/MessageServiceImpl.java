package com.ChatApp.service.impl;

import com.ChatApp.entity.Message;
import com.ChatApp.entity.User;
import com.ChatApp.enums.Error;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.repository.MessageRepository;
import com.ChatApp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    @Override
    public void deleteMessage(String messageId) {
        Message m = messageRepository.getById(messageId);
        SecurityContext context = SecurityContextHolder.getContext();
        User u = (User) context.getAuthentication().getPrincipal();
        if(m.getSenderId().equals(u.getId())) {
            m.setDeleted(true);
            messageRepository.save(m);
        } else {
            throw new AppException(Error.NOT_HAVE_PERMISSION);
        }
    }
    @Override
    public void sendMessage() {

    }

    @Override
    public void sendFile() {

    }
}
