package com.rodionxedin.service.ticker;

import com.rodionxedin.controller.ChangeController;
import com.rodionxedin.db.ChangeRepository;
import com.rodionxedin.db.TickerRepository;
import com.rodionxedin.db.UserRepository;
import com.rodionxedin.db.WalletRepository;
import com.rodionxedin.model.Change;
import com.rodionxedin.model.Wallet;
import com.rodionxedin.model.ticker.Ticker;
import com.rodionxedin.service.machine.TimeMachine;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodion.shkrobot on 9/13/2016.
 */
@Component
public class TickerProcessor {

    private final Logger logger = Logger.getLogger(TickerProcessor.class);

    @Autowired
    private TickerRepository tickerRepository;

    @Autowired
    private TimeMachine timeMachine;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ChangeRepository changeRepository;


    private static List<Ticker> tickersToRun = new ArrayList<>();

    private static List<Ticker> tickersToSave = new ArrayList<>();

    @Scheduled(cron = "0 * * * * *")
    public void saveTickers() {
        for (Ticker ticker : tickersToSave) {
            Change changeSaved = changeRepository.save(ticker.getChange());
            ticker.setChange(changeSaved);
            tickerRepository.save(ticker);
        }
    }

    @PostConstruct
    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Ticker> all = tickerRepository.findAll();
                logger.info("Got " + all.size() + " tickers from db");
                for (Ticker ticker : all) {
                    ticker.init();
                }
            }
        }).start();
    }

    @Scheduled(cron = "0 * * * * *")
    public void tick() {
        logger.info("Started the tick operation");
        List<Ticker> copy = new ArrayList<>(tickersToRun);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Ticker ticker : copy) {
                    Change change = ticker.getChange();
                    Wallet wallet = ticker.getWallet();
                    Change changeSaved = changeRepository.save(change);
                    wallet.addChange(changeSaved);
                    timeMachine.saveWallet(ticker.getUser(), wallet);
                    logger.info("Adding a change : " + change.getName() + " " + change.getDate());
                }
                tickersToRun.removeAll(copy);
            }
        }).start();
    }

    public static boolean addToSave(Ticker ticker) {
        return tickersToSave.add(ticker);
    }

    public static boolean addToRun(Ticker ticker) {
        return tickersToRun.add(ticker);
    }
}
