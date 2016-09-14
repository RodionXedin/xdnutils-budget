package com.rodionxedin.model.ticker;

import com.rodionxedin.model.Change;
import com.rodionxedin.model.User;
import com.rodionxedin.model.Wallet;
import com.rodionxedin.service.ticker.TickerProcessor;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodion.shkrobot on 9/13/2016.
 */
@Document(collection = Ticker.COLLECTION_NAME)
public class Ticker {

    @Transient
    private final Logger logger = Logger.getLogger(Ticker.class);

    public static final String COLLECTION_NAME = "tickers";

    @Id
    private String key;


    private boolean predictionDatesFilled;

    @DBRef
    private Change change;
    @DBRef
    private User user;
    @DBRef
    private Wallet wallet;
    private Rule rule;

    private List<String> filledDates = new ArrayList<>();

    private Ticker() {

    }

    public void init() {
        logger.debug("initializing ticker");
        register(this);
    }

    public Ticker(Change change, User user, Wallet wallet, Rule rule) {
        this.change = change;
        this.user = user;
        this.wallet = wallet;
        this.rule = rule;
        register(this);
    }

    private Ticker(Change change, User user, Wallet wallet, Rule rule, boolean withoutRegistering) {
        this.change = change;
        this.user = user;
        this.wallet = wallet;
        this.rule = rule;

    }


    private void register(Ticker newTicker) {
        logger.info("Registering new ticker : "
                + newTicker.getChange().getName() + " from : " + newTicker.getUser().getName());
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setThreadNamePrefix("Ticker");
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.scheduleAtFixedRate(new Runnable() {
            private Ticker ticker;

            public Ticker getTicker() {
                return ticker;
            }

            public void setTicker(Ticker ticker) {
                this.ticker = ticker;
            }

            @Override
            public void run() {
                ticker = newTicker;
                LocalDate predictionEnd = LocalDate.now().plusDays(wallet.getPredictionPeriodInDays());
                LocalDate startDate = change.getDate();
                if (!ticker.predictionDatesFilled) {
                    while (predictionEnd.isAfter(startDate)) {
                        Change newChange = new Change(change);
                        newChange.setDate(startDate);
                        ticker.add(startDate.toString());
                        TickerProcessor.addToRun(new Ticker(newChange, user, wallet, rule, true));
                        startDate = startDate.plusDays((int) TimeUnit.MILLISECONDS.toDays(rule.getDelay()));
                    }
                    predictionDatesFilled = true;
                }
                if (ticker.getFilledDates().contains(predictionEnd.toString())) {
                    Change predictionChange = new Change(change);
                    predictionChange.setDate(predictionEnd);
                    TickerProcessor.addToRun(new Ticker(predictionChange, user, wallet, rule, true));
                    ticker.add(predictionEnd.toString());
                }
                TickerProcessor.addToSave(ticker);
            }

        }, DateTime.now().toDate(), rule.getDelay());
    }

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wallet getWallet() {
        return wallet;
    }


    public boolean isPredictionDatesFilled() {
        return predictionDatesFilled;
    }

    public void setPredictionDatesFilled(boolean predictionDatesFilled) {
        this.predictionDatesFilled = predictionDatesFilled;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public List<String> getFilledDates() {
        return filledDates;
    }

    public void setFilledDates(List<String> filledDates) {
        this.filledDates = filledDates;
    }

    public boolean add(String s) {
        return filledDates.add(s);
    }
}
