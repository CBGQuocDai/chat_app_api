package com.ChatApp.dto.response;

import com.ChatApp.enums.TypeMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payload {
    private String url;
    private String fileName;
    private String fileSize;
    private TypeMessage mineType;
}
