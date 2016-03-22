package com.rodionxedin.model;


import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodio on 08.12.2015.
 */
@Document(collection = User.COLLECTION_NAME)
public class User {

    public static final String COLLECTION_NAME = "users";
    @Id
    private String key;


    public User() {
    }

    public User(String name, String password, List<Wallet> wallets) {
        this.name = name;
        this.password = password;
        this.wallets = wallets;
    }


    public User(String name, String password, Wallet wallet) {
        this.name = name;
        this.password = password;
        wallets = new ArrayList<>();
        wallets.add(wallet);
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        wallets = new ArrayList<>();
    }

    public Wallet getWallet(String walletName) {
        for (Wallet wallet : wallets) {
            if (wallet.getName().equals(walletName)) {
                return wallet;
            }
        }
        return null;
    }

    public boolean addWallet(Wallet wallet) {
        if (this.wallets == null) {
            this.wallets = new ArrayList<>();
        }
        return wallets.add(wallet);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    private String name;
    private String password;
    private String defaultCurrency = "UAH";

    @DBRef
    private List<Wallet> wallets;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(name, user.name) &&
                Objects.equal(password, user.password);
    }



    @Override
    public int hashCode() {
        return Objects.hashCode(name, password);
    }
}
