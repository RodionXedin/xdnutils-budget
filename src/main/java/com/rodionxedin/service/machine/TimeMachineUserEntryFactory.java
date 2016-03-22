package com.rodionxedin.service.machine;

import com.rodionxedin.model.User;
import com.rodionxedin.service.currency.CurrencyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by rodio on 22.03.2016.
 */
@Component
public class TimeMachineUserEntryFactory {

    @Autowired
    private CurrencyServer currencyServer;

    public TimeMachineUserEntry createTimeMachineUserEntry(User user){
        return new TimeMachineUserEntry(user,currencyServer);
    }

}
