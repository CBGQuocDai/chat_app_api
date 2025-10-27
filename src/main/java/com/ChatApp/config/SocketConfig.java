package com.ChatApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class SocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    JwtInterceptor jwtInterceptor;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/queue");
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtInterceptor);
    }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
         registration.setSendBufferSizeLimit(5*1024*1024) // Tăng buffer gửi lên 5MB
                .setSendTimeLimit(20*1000)      // Thời gian chờ gửi (ms)
                .setMessageSizeLimit(5*1024*1024); // Kích thước tối đa của một tin nhắn (byte)
    }

}


