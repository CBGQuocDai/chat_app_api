package com.ChatApp.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LastMessage {
    private String content;
    private String username;
    private Date sentAt;
    private String type;
}
