package com.ChatApp.service.impl;

import com.ChatApp.dto.request.ConversationRequest;
import com.ChatApp.dto.response.ConversationResponse;
import com.ChatApp.dto.response.MessageResponse;
import com.ChatApp.dto.response.UserResponse;
import com.ChatApp.entity.Conversation;
import com.ChatApp.entity.Message;
import com.ChatApp.entity.User;
import com.ChatApp.enums.Error;
import com.ChatApp.exceptions.AppException;
import com.ChatApp.mapper.MessageMapper;
import com.ChatApp.mapper.UserMapper;
import com.ChatApp.repository.ConversationRepository;
import com.ChatApp.repository.MessageRepository;
import com.ChatApp.repository.UserRepository;
import com.ChatApp.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<ConversationResponse> getConversationList() {
        System.out.println( "getConversationList");
        SecurityContext context = SecurityContextHolder.getContext();
        User u = (User) context.getAuthentication().getPrincipal();
        log.info("u: {}", u.getEmail());
        System.out.println(u.getId());
        List<Conversation> conversationList = conversationRepository.findByIdMembersContaining(u.getId());
        List<ConversationResponse> result = new ArrayList<>();
        System.out.println("check1");
        for (Conversation conversation : conversationList) {
            List<UserResponse> users = new ArrayList<>();
            for(String id : conversation.getIdMembers()) {
                if(!id.equals(u.getId())) {
                    User user = userRepository.getUsersById(id);
                    users.add(userMapper.fromUserToUserResponse(user));
                }
            }
            if(!conversation.getIsGroup()) {
                UserResponse partner = users.getFirst();
                if(partner != null) {
                    conversation.setAvatarGroup(partner.avatar());
                    conversation.setName(partner.username());
                } else {
                    throw new AppException(Error.USER_NOT_EXIST);
                }
            }
            result.add(new ConversationResponse(conversation.getId(),conversation.getName(),
                    conversation.getIsGroup(),conversation.getAvatarGroup(),users,
                    conversation.getLastMessage(),
                    5));
        }

        log.info("result: {}", result.size());
        result.sort((c1, c2) -> c2.lastMessage().getSentAt().compareTo(c1.lastMessage().getSentAt()));
        return result;
    }
    @Override
    public ConversationResponse createConversation(ConversationRequest req) {
        List<UserResponse> users = new ArrayList<>();
        for(String x: req.idMembers()) {
            if(!userRepository.existsById(x)) {
                throw new AppException(Error.USER_NOT_EXIST);
            } else {
                UserResponse u =
                        userMapper.fromUserToUserResponse(userRepository.getUsersById(x));
                users.add(u);
            }
        }
        Conversation con = new Conversation();
        con.setName(req.name());
        con.setAvatarGroup("default.png");
        con.setCreatedAt(new Date());
        con.setIdMembers(List.of(req.idMembers()));
        con.setIsGroup(true);
        con = conversationRepository.save(con);
        return new ConversationResponse(con.getId(),con.getName(),
                con.getIsGroup(),con.getAvatarGroup(),users,null,
                0);
    }

    @Override
    public List<MessageResponse> getMessages(String conversationId) {
        Conversation conversation = conversationRepository.getById(conversationId);
        System.out.println("gege");
        SecurityContext context = SecurityContextHolder.getContext();
        User u = (User) context.getAuthentication().getPrincipal();
        if(conversation!=null) {
            System.out.println(u.getId());
            System.out.println("passs");
            if(!conversation.getIdMembers().contains(u.getId())) {
                throw new AppException(Error.USER_NOT_IN_CONVERSATION);
            }
            List<Message> msg=  messageRepository.getByConversationId(conversationId);
            msg.sort((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt()));
            List<MessageResponse> result = new ArrayList<>();
            for(Message m: msg) {
                UserResponse user = userMapper.
                        fromUserToUserResponse(userRepository.getUsersById(m.getSenderId()));
                result.add(messageMapper.fromMessageToMessageResponse(m,user));
            }
            return result;
        } else {
            throw new AppException(Error.SERVER_ERROR);
        }
    }

    @Override
    public void leaveConversation(String conversationId) {
        SecurityContext context = SecurityContextHolder.getContext();
        User u = (User) context.getAuthentication().getPrincipal();
        Conversation conversation = conversationRepository.getById(conversationId);
        if(conversation!=null && conversation.getIsGroup()) {
            if(!conversation.getIdMembers().contains(u.getId())) {
                throw new AppException(Error.USER_NOT_IN_CONVERSATION);
            }
            List<String> newIdMembers = new ArrayList<>();
            for(String id: conversation.getIdMembers()) {
                if(!id.equals(u.getId())) {
                    newIdMembers.add(id);
                }
            }
            conversation.setIdMembers(newIdMembers);
            conversationRepository.save(conversation);
        } else {
            throw new AppException(Error.SERVER_ERROR);
        }
    }
    @Override
    public void clearConversationHistory(String conversationId) {

    }
    @Override
    public ConversationResponse getConversation(String conversationId) {
        SecurityContext context = SecurityContextHolder.getContext();
        User u = (User) context.getAuthentication().getPrincipal();
        Conversation conversation = conversationRepository.getById(conversationId);
        if(conversation!=null) {
            if(!conversation.getIdMembers().contains(u.getId())) {
                throw new AppException(Error.USER_NOT_IN_CONVERSATION);
            }
            List<UserResponse> users = new ArrayList<>();
            for(String id : conversation.getIdMembers()) {
                User user = userRepository.getUsersById(id);
                if(user != null) {
                    users.add(userMapper.fromUserToUserResponse(user));
                }
            }
            return new ConversationResponse(conversation.getId()
                    ,conversation.getName(), conversation.getIsGroup(), conversation.getAvatarGroup(), users,
                    conversation.getLastMessage(), 0);
        }
        else {
            throw new AppException(Error.SERVER_ERROR);
        }
    }
}
