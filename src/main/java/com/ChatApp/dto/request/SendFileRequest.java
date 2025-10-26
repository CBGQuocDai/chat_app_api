package com.ChatApp.dto.request;

import com.ChatApp.enums.TypeMessage;
import org.springframework.web.multipart.MultipartFile;

public record SendFileRequest(String conversationId, MultipartFile file, TypeMessage type) {
}
