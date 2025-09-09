package com.ChatApp.entity;

import com.ChatApp.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "users")
@Getter
@Setter
@Builder
public class User {
    @Id
    private String id;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private boolean active;
    private Status status;
    private Date createdAt;
    private Date updatedAt;
}
