package com.ChatApp.dto.response;

import com.ChatApp.enums.TypeMessage;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class MessageResponse {
    String id;
    private String conversationId;
    private String content;
    private Date sendAt;
    private TypeMessage type;
    private UserResponse sender;
    private String replyToMessageId;
    private Payload payload;
}
