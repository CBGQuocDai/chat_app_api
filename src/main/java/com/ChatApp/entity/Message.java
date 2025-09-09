package com.ChatApp.entity;

import com.ChatApp.enums.TypeMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "messages")
@Getter
@Setter
public class Message {
    @Id
    private String id;
    private String content;
    private Long senderId;
    private Long conversationId;
    private TypeMessage type;
    private Date sentAt;
    private Long replyToMessageId;
    private Date createdAt;
    private Date updatedAt;

}
