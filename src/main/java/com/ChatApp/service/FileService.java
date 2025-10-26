package com.ChatApp.service;

import com.ChatApp.dto.response.Payload;
import org.springframework.stereotype.Service;

@Service
public interface FileService {
    void saveFile(Payload payload);
}
