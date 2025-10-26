package com.ChatApp.service.impl;

import com.ChatApp.dto.request.SendFileRequest;
import com.ChatApp.dto.request.SendMessageRequest;
import com.ChatApp.dto.response.MessageResponse;
import com.ChatApp.dto.response.Payload;
import com.ChatApp.entity.Conversation;
import com.ChatApp.entity.LastMessage;
import com.ChatApp.entity.Message;
import com.ChatApp.entity.User;
import com.ChatApp.enums.Error;
import com.ChatApp.enums.TypeMessage;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.mapper.MessageMapper;
import com.ChatApp.mapper.UserMapper;
import com.ChatApp.repository.ConversationRepository;
import com.ChatApp.repository.MessageRepository;
import com.ChatApp.service.MessageService;
import com.ChatApp.utils.MessageEncrypt;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PresignedUrlUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final AmazonS3 amazonS3;
    private final ConversationRepository conversationRepository;
    private final MessageMapper messageMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MessageEncrypt messageEncrypt;
    @Value("${s3.bucket}")
    private String BUCKET_NAME;
    @Override
    public void deleteMessage(String messageId) {
        Message m = messageRepository.getById(messageId);
        SecurityContext context = SecurityContextHolder.getContext();
        User u = (User) context.getAuthentication().getPrincipal();
        if(m.getSenderId().equals(u.getId())) {
            m.setDeleted(true);
            messageRepository.save(m);
        } else {
            throw new AppException(com.ChatApp.enums.Error.NOT_HAVE_PERMISSION);
        }
    }
    @Override
    public Message sendMessage(SendMessageRequest req, User u) {
        System.out.println("saved message");
        Message m = messageMapper.fromSendMessageRequestToMessage(req, u);
        LastMessage lastMessage = new LastMessage();
        lastMessage.setUsername(u.getUsername());
        lastMessage.setType(String.valueOf(req.type()));
        lastMessage.setContent(m.getContent());
        lastMessage.setSentAt(new java.util.Date());
        Conversation c = conversationRepository.getById(req.conversationId());
        if(c!=null) {
            c.setLastMessage(lastMessage);
            conversationRepository.save(c);
        } else {
            throw new AppException(Error.SERVER_ERROR);
        }
        return messageRepository.save(m);
    }
    @Override
    public MessageResponse uploadFile(SendFileRequest req) {
        SecurityContext context = SecurityContextHolder.getContext();
        User u = (User) context.getAuthentication().getPrincipal();
        String fileNameOrigin = req.file().getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(req.file().getSize());
        metadata.setContentType(req.file().getContentType());
        assert fileNameOrigin != null;
        Message m = Message.builder()
                .senderId(u.getId())
                .createdAt(new java.util.Date())
                .sentAt(new java.util.Date())
                .updatedAt(new java.util.Date())
                .content(messageEncrypt.encrypt(fileNameOrigin))
                .senderId(u.getId())
                .conversationId(req.conversationId())
                .isDeleted(false)
                .type(req.type())
                .build();
        Message newMessage = messageRepository.save(m);
        StringBuilder fileName = new StringBuilder();
        fileName.append(newMessage.getId()).append("_").append(fileNameOrigin);
        try {
            amazonS3.putObject(BUCKET_NAME,fileName.toString(),
                    req.file().getInputStream(),metadata);
        } catch (IOException e) {
            throw new AppException(Error.SERVER_ERROR);
        }
        MessageResponse resp =  messageMapper.fromMessageToMessageResponse(newMessage,
                userMapper.fromUserToUserResponse(u));
        Payload p = new Payload();
        GeneratePresignedUrlRequest req2 = new GeneratePresignedUrlRequest(BUCKET_NAME, fileName.toString())
                .withExpiration(new Date(Instant.now().plusSeconds(3600).toEpochMilli()))
                .withMethod(HttpMethod.GET);
        URL url = amazonS3.generatePresignedUrl(req2);
        p.setUrl(url.toString());
        p.setFileSize(String.valueOf(req.file().getSize()));
        p.setMineType(req.type());
        p.setFileName(resp.getContent());
        resp.setPayload(p);
        LastMessage lastMessage = new LastMessage();
        String message =null;
        if(req.type()== TypeMessage.TEXT) {
            message = messageEncrypt.encrypt("vừa gửi một tệp");
        }
        else {
            message = messageEncrypt.encrypt("Đã gửi một ảnh");
        }
        lastMessage.setUsername(u.getUsername());
        lastMessage.setType(String.valueOf(req.type()));
        lastMessage.setContent(message);
        lastMessage.setSentAt(new java.util.Date());
        Conversation c = conversationRepository.getById(req.conversationId());
        if(c!=null) {
            c.setLastMessage(lastMessage);
            conversationRepository.save(c);
        }
        return resp;
    }

    @Override
    public InputStreamResource downloadFile(String messageId) {
        return null;
    }
}
