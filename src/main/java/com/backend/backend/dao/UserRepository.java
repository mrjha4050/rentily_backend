package com.backend.backend.dao;

import com.backend.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByName (String name);
    User findByEmail (String email);
}
