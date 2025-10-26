package com.ChatApp.entity;

import com.ChatApp.enums.TypeMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "messages")
@Getter
@Setter
@Builder
public class Message {
    @Id
    private String id;
    private String content;
    private String senderId;
    private String conversationId;
    private TypeMessage type;
    private Date sentAt;
    private String replyToMessageId;
    private boolean isDeleted;
    private Date createdAt;
    private Date updatedAt;

}
