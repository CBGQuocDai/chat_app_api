package com.ChatApp.dto.response;

import com.ChatApp.entity.LastMessage;

import java.util.List;

public record ConversationResponse(
        String id,
        String name,
        boolean isGroup,
        String avatar,
        List<UserResponse> participants,
        LastMessage lastMessage,
        int unreadCount
) {
}
