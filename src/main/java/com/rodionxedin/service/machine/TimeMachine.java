package com.rodionxedin.service.machine;

import com.rodionxedin.db.UserRepository;
import com.rodionxedin.db.WalletRepository;
import com.rodionxedin.model.User;
import com.rodionxedin.model.Wallet;
import com.rodionxedin.service.currency.CurrencyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodio on 17.03.2016.
 */
@Service
public class TimeMachine {

    @Autowired
    private CurrencyServer currencyServer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TimeMachineUserEntryFactory timeMachineUserEntryFactory;

    private Map<User, TimeMachineUserEntry> timeMachineUserEntryMap = new HashMap<>();


    public TimeMachineUserEntry createUserEntry(User user){
        TimeMachineUserEntry timeMachineUserEntry = timeMachineUserEntryFactory.createTimeMachineUserEntry(user);
        timeMachineUserEntryMap.put(user, timeMachineUserEntry);
        return timeMachineUserEntry;
    }

    public TimeMachineUserEntry getUserEntry(Object key) {
        return timeMachineUserEntryMap.get(key);
    }

    public Wallet saveWallet(User user, Wallet wallet) {
        walletRepository.save(wallet);
        timeMachineUserEntryMap.get(user).updateWallet(wallet);
        return wallet;
    }

}
