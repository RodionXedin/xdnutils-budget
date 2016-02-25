package com.rodionxedin.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodio on 05.12.2015.
 */
public class Wallet {

    enum Information {
        CURRENT_AMOUNT("currentAmount");

        private String informationKey;

        Information(String informationKey) {
            this.informationKey = informationKey;
        }
        }

    @Id
    private String key;
    private String name;
    private String owner;
    private Map<Information, BigDecimal> information;
    private List<Change> changes;


    public String getName() {
        return name;
    }

    public Wallet() {
    }

    public Wallet(String name, String owner) {
        this.name = name;
        this.owner = owner;
        this.changes = new ArrayList<>();

    }

    public Wallet(String owner, Change change) {
        this.owner = owner;
        this.changes = new ArrayList<>();
        changes.add(change);

        information = new HashMap<>();
        information.put(Information.CURRENT_AMOUNT, change.getAmount());

    }

    public Wallet(String owner, List<Change> changes) {
        this.owner = owner;
        this.changes = changes;

        BigDecimal amount = BigDecimal.ZERO;

        for (Change change : this.changes) {
            amount = amount.add(change.getAmount());
        }

        information = new HashMap<>();
        information.put(Information.CURRENT_AMOUNT, amount);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Change> getChanges() {
        return changes;
    }

    public void setChanges(List<Change> changes) {
        this.changes = changes;
    }
}