package com.rodionxedin.db;

import com.rodionxedin.model.Change;
import com.rodionxedin.model.ticker.Ticker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by rodion.shkrobot on 9/13/2016.
 */
@Component
public interface TickerRepository extends MongoRepository<Ticker, String> {
}
