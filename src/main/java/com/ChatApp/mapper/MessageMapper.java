package com.ChatApp.mapper;

import com.ChatApp.dto.response.MessageResponse;
import com.ChatApp.dto.response.UserResponse;
import com.ChatApp.entity.Message;
import com.ChatApp.utils.MessageEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageMapper {
//    private final MessageEncrypt messageEncrypt;
    public MessageResponse fromMessageToMessageResponse(Message msg, UserResponse sender) {
        return new MessageResponse(
                msg.getId(),
                msg.isDeleted() ? "message is deleted" : msg.getContent(),
                sender,
                msg.getConversationId(),
                msg.getType(),
                msg.getSentAt(),
                msg.getReplyToMessageId()
        );
    }
}
