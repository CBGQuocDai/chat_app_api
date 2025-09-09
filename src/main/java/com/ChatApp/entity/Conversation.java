package com.ChatApp.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(value = "conversations")
@Getter
@Setter
public class Conversation {
    @Id
    private String id;
    private Boolean isGroup;
    private String name;
    private String avatarGroup;
    private List<Long> idMembers;
    private LastMessage lastMessage;
    private Date createdAt;
    private Date updatedAt;
}
