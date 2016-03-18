package com.rodionxedin.service.machine;

import com.rodionxedin.model.Change;
import com.rodionxedin.model.Wallet;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.LocalDate;
import org.thymeleaf.util.ListUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodio on 17.03.2016.
 */
public class TimeMachineWalletEntry {

    private Wallet wallet;
    private BigDecimal currentState = BigDecimal.ZERO;
    private BigDecimal originalState = BigDecimal.ZERO;
    private BigDecimal highestState = BigDecimal.valueOf(Integer.MIN_VALUE);
    private BigDecimal lowestState = BigDecimal.valueOf(Integer.MAX_VALUE);
    private List<Pair<LocalDate, BigDecimal>> states = new ArrayList<>();


    public TimeMachineWalletEntry(Wallet wallet) {
        this.wallet = wallet;

        List<Change> changes = wallet.getChanges();

        ListUtils.sort(changes, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));

        wallet.setChanges(changes);

        calculateStates(changes);


    }

    private void calculateStates(List<Change> changes) {
        originalState = changes.get(0).getAmount();


        for (Change change : changes) {
            currentState = currentState.add(change.getAmount().multiply(BigDecimal.valueOf(change.getType().equals(Change.Type.INCOME) ? 1 : -1)));
            if (currentState.compareTo(highestState) == 1) {
                highestState = currentState;
            } else if (currentState.compareTo(lowestState) == -1) {
                lowestState = currentState;
            }

            states.add(Pair.of(change.getDate(),currentState));


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

    public List<Pair<LocalDate, BigDecimal>> getStates() {
        return states;
    }

    public BigDecimal getHighest() {
        return highestState;
    }

    public BigDecimal getLowestState() {
        return lowestState;
    }
}

