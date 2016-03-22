package com.rodionxedin.service.machine;

import com.rodionxedin.model.Change;
import com.rodionxedin.model.Wallet;
import com.rodionxedin.service.currency.CurrencyServer;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.LocalDate;
import org.thymeleaf.util.ListUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by rodio on 17.03.2016.
 */
public class TimeMachineWalletEntry {

    private Wallet wallet;
    private BigDecimal currentState = BigDecimal.ZERO;
    private BigDecimal originalState = BigDecimal.ZERO;
    private BigDecimal highestState = BigDecimal.valueOf(Integer.MIN_VALUE);
    private BigDecimal lowestState = BigDecimal.valueOf(Integer.MAX_VALUE);
    private Map<LocalDate, BigDecimal> states = new LinkedHashMap<>();


    public TimeMachineWalletEntry(Wallet wallet, CurrencyServer currencyServer, String defaultCurrency) {
        this.wallet = wallet;

        List<Change> changes = wallet.getChanges();

        ListUtils.sort(changes, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        wallet.setChanges(changes);

        calculateStates(changes, currencyServer, defaultCurrency);


    }

    private void calculateStates(List<Change> changes, CurrencyServer currencyServer, String defaultCurrency) {
        Collections.sort(changes, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        originalState = changes.get(0).getAmount();


        for (Change change : changes) {
            BigDecimal changeModifierForState = change.getAmount().multiply(BigDecimal.valueOf(change.getType().equals(Change.Type.INCOME) ? 1 : -1));
            if (currencyServer.containsKey(change.getCurrency().toString() + defaultCurrency))
                changeModifierForState = changeModifierForState.multiply(currencyServer.get(change.getCurrency().toString() + defaultCurrency));
            currentState = currentState.add(changeModifierForState);
            if (currentState.compareTo(highestState) == 1) {
                highestState = currentState;
            } else if (currentState.compareTo(lowestState) == -1) {
                lowestState = currentState;
            }
            states.put(change.getDate(), currentState);
        }
    }


    public Wallet getWallet() {
        return wallet;
    }

    public BigDecimal getCurrentState() {
        return currentState;
    }

    public BigDecimal getOriginalState() {
        return originalState;
    }

    public List<Pair<LocalDate, BigDecimal>> getStatesAsList() {
        List<Pair<LocalDate, BigDecimal>> statesList = new ArrayList<>();
        states.forEach((localDate, bigDecimal) -> statesList.add(Pair.of(localDate, bigDecimal)));
        Collections.sort(statesList, (o1, o2) -> o1.getLeft().compareTo(o2.getLeft()));

        return statesList;
    }

    public BigDecimal getHighest() {
        return highestState;
    }

    public BigDecimal getLowestState() {
        return lowestState;
    }
}

