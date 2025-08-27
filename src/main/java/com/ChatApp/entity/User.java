package com.ChatApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Document(value = "users")
public class User {
    @Id
    private Long id;
    private String email;
    private String username;
    private String password;
    private String avatar;
}
