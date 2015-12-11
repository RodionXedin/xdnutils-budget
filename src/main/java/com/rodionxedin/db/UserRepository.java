package com.rodionxedin.db;

import com.rodionxedin.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by rodio on 08.12.2015.
 */
@Component
public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);
}
