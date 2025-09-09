package com.ChatApp.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LastMessage {
    private String content;
    private Long senderId;
    private Date sentAt;
}
