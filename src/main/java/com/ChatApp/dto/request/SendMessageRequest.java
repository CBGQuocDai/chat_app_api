package com.ChatApp.dto.request;

import com.ChatApp.dto.response.Payload;
import com.ChatApp.enums.TypeMessage;

public record SendMessageRequest(String conversationId, String content, TypeMessage type, String replyToMessageId, Payload payload) {
}
