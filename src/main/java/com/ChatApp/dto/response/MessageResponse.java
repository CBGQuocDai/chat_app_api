package com.ChatApp.dto.response;

import com.ChatApp.enums.TypeMessage;
import java.util.Date;

public record MessageResponse(
        String id,
        String content,
        UserResponse sender,
        String conversationId,
        TypeMessage type,
        Date sendAt,
        String replyToMessageId
) {
}
