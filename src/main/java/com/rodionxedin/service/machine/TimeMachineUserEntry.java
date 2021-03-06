package com.rodionxedin.service.machine;

import com.rodionxedin.model.User;
import com.rodionxedin.model.Wallet;
import com.rodionxedin.service.currency.CurrencyServer;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodio on 17.03.2016.
 */
public class TimeMachineUserEntry {

    private User user;

    private CurrencyServer currencyServer;

    private Map<Wallet, TimeMachineWalletEntry> walletEntryMap = new HashMap<>();

    public TimeMachineUserEntry(User user, CurrencyServer currencyServer) {
        this.user = user;
        this.currencyServer = currencyServer;
    }

    public void updateWallet(Wallet wallet) {
        walletEntryMap.put(wallet, new TimeMachineWalletEntry(wallet, currencyServer, user.getDefaultCurrency()));
    }

    public Wallet getActualWalletEntry(Wallet wallet) {
        if (!walletEntryMap.containsKey(wallet)) {
            walletEntryMap.put(wallet, new TimeMachineWalletEntry(wallet, currencyServer, user.getDefaultCurrency()));
        }

        return walletEntryMap.get(wallet).getWallet();
    }

    public BigDecimal getCurrentState(Wallet wallet) {
        if (!walletEntryMap.containsKey(wallet)) {
            walletEntryMap.put(wallet, new TimeMachineWalletEntry(wallet, currencyServer, user.getDefaultCurrency()));
        }

        return walletEntryMap.get(wallet).getCurrentState();
    }

    public BigDecimal getOriginalState(Wallet wallet) {
        if (!walletEntryMap.containsKey(wallet)) {
            walletEntryMap.put(wallet, new TimeMachineWalletEntry(wallet, currencyServer, user.getDefaultCurrency()));
        }

        return walletEntryMap.get(wallet).getOriginalState();
    }


    public BigDecimal getLowestState(Wallet wallet) {
        if (!walletEntryMap.containsKey(wallet)) {
            walletEntryMap.put(wallet, new TimeMachineWalletEntry(wallet, currencyServer, user.getDefaultCurrency()));
        }

        return walletEntryMap.get(wallet).getOriginalState();
    }

    public BigDecimal getHighestState(Wallet wallet) {
        if (!walletEntryMap.containsKey(wallet)) {
            walletEntryMap.put(wallet, new TimeMachineWalletEntry(wallet, currencyServer, user.getDefaultCurrency()));
        }

        return walletEntryMap.get(wallet).getOriginalState();
    }


    public List<Pair<LocalDate, BigDecimal>> getStates(Wallet wallet) {
        if (!walletEntryMap.containsKey(wallet)) {
            walletEntryMap.put(wallet, new TimeMachineWalletEntry(wallet, currencyServer, user.getDefaultCurrency()));
        }

        return walletEntryMap.get(wallet).getStatesAsList();
    }


}
