package com.rodionxedin.controller;

import com.mongodb.util.JSON;
import com.rodionxedin.db.ChangeRepository;
import com.rodionxedin.db.UserRepository;
import com.rodionxedin.db.WalletRepository;
import com.rodionxedin.model.Change;
import com.rodionxedin.model.User;
import com.rodionxedin.model.Wallet;
import com.rodionxedin.util.JsonUtils;
import com.rodionxedin.util.SessionUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.rodionxedin.util.JsonUtils.success;

/**
 * Created by rodion on 24.02.2016.
 */
@RestController
public class ChangeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ChangeRepository chageRepository;

    @RequestMapping(value = "/create-income", produces = "application/json", method = RequestMethod.POST)
    public String createIncome(@RequestParam Map<String, String> params) {
        User user = (User) SessionUtils.getSession().getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
        Wallet wallet = user.getWallet(params.get(JsonUtils.WALLET_KEY));

        Change change = new Change(Change.Type.INCOME, Change.TimeType.ONE_TIME,
                BigDecimal.valueOf(Double.parseDouble(params.get("amount"))),
                LocalDate.parse(params.get("date")), Change.Currency.HRN, null, params.get("name"), null);


        Change changeSaved = chageRepository.save(change);
        wallet.addChange(changeSaved);
        walletRepository.save(wallet);
        return success().put("data", JSON.serialize(params).toString()).toString();
    }

    @RequestMapping(value = "/create-expense", produces = "application/json", method = RequestMethod.POST)
    public String createExpense(@RequestParam Map<String, String> params) {
        User user = (User) SessionUtils.getSession().getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
        Wallet wallet = user.getWallet(params.get(JsonUtils.WALLET_KEY));



        Change change = new Change(Change.Type.OUTCOME, Change.TimeType.ONE_TIME,
                BigDecimal.valueOf(Double.parseDouble(params.get("amount"))),
                LocalDate.parse(params.get("date")), Change.Currency.HRN, null, params.get("name"), null);


        Change changeSaved = chageRepository.save(change);
        wallet.addChange(changeSaved);
        walletRepository.save(wallet);
        return success().put("data", JSON.serialize(params).toString()).toString();
    }

    @RequestMapping(value = "/get-changes", produces = "application/json", method = RequestMethod.GET)
    public String getChanges(@RequestParam String walletName, String type) {
        User user = (User) SessionUtils.getSession().getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
        Wallet wallet = user.getWallet(walletName);
        List<Change> changes = new ArrayList<>(wallet.getChanges());
        if (!type.equals("all")) {
            changes.removeIf(change -> change.getType().equals(type.equalsIgnoreCase("income") ? Change.Type.OUTCOME : Change.Type.INCOME));
        }
        JSONArray changesArray = new JSONArray();
        changes.forEach(change -> changesArray.put(JsonUtils.convertChangeToJson(change)));
        return success().put("expenses", changesArray).toString();
    }
}
