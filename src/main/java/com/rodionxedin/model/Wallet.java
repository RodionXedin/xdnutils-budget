package com.rodionxedin.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodio on 05.12.2015.
 */
public class Wallet {

    @Id
    private String key;
    private String name;
    private String owner;
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
    }

    public Wallet(String owner, List<Change> changes) {
        this.owner = owner;
        this.changes = changes;
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