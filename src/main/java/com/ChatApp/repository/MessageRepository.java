package com.ChatApp.repository;

import com.ChatApp.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> getByConversationId(String conversationId);

    Message getById(String messageId);
}
