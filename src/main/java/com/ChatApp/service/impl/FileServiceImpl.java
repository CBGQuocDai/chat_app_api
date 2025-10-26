package com.ChatApp.service.impl;

import com.ChatApp.dto.response.Payload;
import com.ChatApp.service.FileService;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final AmazonS3 amazonS3;
    @Override
    public void saveFile(Payload payload) {
        System.out.println("payload: " + payload.getUrl());

    }
}
