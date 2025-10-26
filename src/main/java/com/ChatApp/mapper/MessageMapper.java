package com.ChatApp.mapper;

import com.ChatApp.dto.request.SendMessageRequest;
import com.ChatApp.dto.response.MessageResponse;
import com.ChatApp.dto.response.Payload;
import com.ChatApp.dto.response.UserResponse;
import com.ChatApp.entity.Message;
import com.ChatApp.entity.User;
import com.ChatApp.enums.TypeMessage;
import com.ChatApp.utils.MessageEncrypt;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final MessageEncrypt messageEncrypt;
    private final AmazonS3 amazonS3;
    @Value("${s3.bucket}")
    private String bucketName;
    public MessageResponse fromMessageToMessageResponse(Message msg, UserResponse sender) {
        if(msg.getType()== TypeMessage.TEXT|| msg.isDeleted()){
            return MessageResponse.builder()
                    .id(msg.getId())
                    .content(msg.isDeleted() ? "tin nhắn đã bị xóa" : messageEncrypt.decrypt(msg.getContent()))
                    .sender(sender)
                    .conversationId(msg.getConversationId())
                    .type(msg.getType())
                    .sendAt(msg.getSentAt())
                    .replyToMessageId(msg.getReplyToMessageId())
                    .build();
        } else {
            Payload p =new Payload();
            StringBuilder fileName = new StringBuilder();
            String messageDecrypt = messageEncrypt.decrypt(msg.getContent());
            fileName.append(msg.getId()).append("_").append(messageDecrypt);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName.toString());
            req.setExpiration(java.util.Date.from(java.time.Instant.now().plusSeconds(1000)));
            req.setMethod(com.amazonaws.HttpMethod.GET);
            p.setUrl(amazonS3.generatePresignedUrl(req).toString());
            p.setFileName(messageDecrypt);

            return MessageResponse.builder()
                    .id(msg.getId())
                    .content(msg.isDeleted() ? "tin nhắn đã bị xóa" : messageDecrypt)
                    .sender(sender)
                    .type(msg.getType())
                    .sendAt(msg.getSentAt())
                    .conversationId(msg.getConversationId())
                    .replyToMessageId(msg.getReplyToMessageId())
                    .payload(p)
                    .build();
        }
    }
    public Message fromSendMessageRequestToMessage(SendMessageRequest req, User u) {
        return Message.builder()
                .content(messageEncrypt.encrypt(req.content()))
                .conversationId(req.conversationId())
                .senderId(u.getId())
                .type(req.type())
                .createdAt(new java.util.Date())
                .replyToMessageId(req.replyToMessageId())
                .sentAt(new java.util.Date())
                .updatedAt(new java.util.Date())
                .build();
    }
}
