package com.rodionxedin.db;

import com.rodionxedin.model.Change;
import com.rodionxedin.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by rodio on 08.12.2015.
 */
@Component
public interface ChangeRepository extends MongoRepository<Change, String> {
    Change findByName(String name);
}
