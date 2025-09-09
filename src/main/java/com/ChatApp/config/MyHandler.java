package com.ChatApp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Slf4j
public class MyHandler extends TextWebSocketHandler {
    private static final HashMap<String, List<WebSocketSession>> channelSessions
            = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Kết nối mới: " + session.getId());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Nhận được tin nhắn: " + payload);
        // Giả sử tin nhắn là một chuỗi JSON
        if (payload.contains("SUBSCRIBE")) {
            // Đây là logic xử lý lệnh SUBSCRIBE
            // Cần phân tích JSON để lấy tên channel
            String channelId = "channel-1"; // Ví dụ: lấy từ JSON
            subscribeToChannel(session, channelId);
        } else if (payload.contains("PUBLISH")) {
            // Đây là logic xử lý lệnh PUBLISH
            // Cần phân tích JSON để lấy channel và nội dung tin nhắn
            log.info("publish");
            String channelId = "channel-1"; // Ví dụ: lấy từ JSON
            String content = "Hello world"; // Ví dụ: lấy từ JSON
            distributeMessage(channelId, content);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Kết nối bị đóng: " + session.getId());
        // Khi kết nối đóng, cần xóa session này khỏi tất cả các channel
        unsubscribeFromAllChannels(session);
    }

    // --- Các phương thức xử lý chính ---

    // Thêm session vào channel tương ứng
    private void subscribeToChannel(WebSocketSession session, String channelId) {
        log.info("channel "+ channelId);
        List<WebSocketSession> sessions = channelSessions.get(channelId);
        if(sessions == null) sessions = new ArrayList<>();
        sessions.add(session);
        channelSessions.put(channelId,sessions);
        for(String key:channelSessions.keySet()) {
            System.out.println(key);
            System.out.println(channelSessions.get(key));
        }
    }

    // Gửi tin nhắn đến tất cả các session trong một channel
    private void distributeMessage(String channelId, String content) throws IOException {

        List<WebSocketSession> sessions = channelSessions.get(channelId);
        log.info("sessions: {}", sessions);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                log.info("session: {}", session.getId());
                session.sendMessage(new TextMessage(content));
            }
        }
    }

    // Xóa session khi kết nối bị đóng
    private void unsubscribeFromAllChannels(WebSocketSession session) {
        channelSessions.forEach((channel, sessions) -> sessions.remove(session.getId()));
    }
}