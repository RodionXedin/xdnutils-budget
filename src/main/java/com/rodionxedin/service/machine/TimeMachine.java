package com.rodionxedin.service.machine;

import com.rodionxedin.db.UserRepository;
import com.rodionxedin.db.WalletRepository;
import com.rodionxedin.model.User;
import com.rodionxedin.model.Wallet;
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
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    private Map<User, TimeMachineUserEntry> timeMachineUserEntryMap = new HashMap<>();

    public Map<User, TimeMachineUserEntry> getTimeMachineUserEntryMap() {
        return timeMachineUserEntryMap;
    }


    public Wallet saveWallet(User user , Wallet wallet){
        walletRepository.save(wallet);
        timeMachineUserEntryMap.get(user).updateWallet(wallet);
        return wallet;
    }

}
