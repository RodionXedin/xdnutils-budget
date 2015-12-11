package com.rodionxedin.db;

import com.rodionxedin.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by rodio on 11.12.2015.
 */
@Component
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Wallet findByName(String name);
}
