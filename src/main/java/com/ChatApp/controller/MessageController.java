package com.ChatApp.controller;

import com.ChatApp.dto.request.SendFileRequest;
import com.ChatApp.dto.response.MessageResponse;
import com.ChatApp.dto.response.Response;
import com.ChatApp.enums.Error;
import com.ChatApp.enums.TypeMessage;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.service.impl.MessageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageServiceImpl messageService;
    private final ObjectMapper objectMapper;
    @DeleteMapping("/{messageId}")
    public Response deleteMessage(@PathVariable String messageId) {
        log.info("deleteMessage: {}", messageId);
        messageService.deleteMessage(messageId);
        return Response.builder().message("Message deleted successfully").build();
    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("conversationId") String conversationId,
                           @RequestParam("type") TypeMessage type) {
        SendFileRequest req = new SendFileRequest(conversationId, file, type);
        MessageResponse msg = messageService.uploadFile(req);
        log.info("uploadFile: {}", msg);
        try {
//            messagingTemplate.convertAndSend("/user/queue/"+msg.getConversationId()+"/messages",
//                    objectMapper.writer().writeValueAsString(msg));
            messagingTemplate.convertAndSend("/queue/"+conversationId,
                    objectMapper.writer().writeValueAsString(msg));
        } catch (Exception e) {
            log.error("Error when sending message: {}", e.getMessage());
            throw new AppException(Error.SERVER_ERROR);
        }
    }
}
