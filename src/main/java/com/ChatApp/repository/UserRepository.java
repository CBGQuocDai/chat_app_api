package com.ChatApp.repository;

import com.ChatApp.entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUsersByEmail(String email);

    User getUsersByEmail(String email);
}
