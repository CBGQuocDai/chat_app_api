package com.ChatApp.repository;


import com.ChatApp.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
     List<Conversation> findByIdMembersContaining(String id);

     Conversation getById(String conversationId);
}
