package com.rodionxedin.controller;

import com.mongodb.util.JSON;
import com.rodionxedin.db.UserRepository;
import com.rodionxedin.db.WalletRepository;
import com.rodionxedin.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.rodionxedin.util.JsonUtils.success;

/**
 * Created by rodion on 24.02.2016.
 */
@RestController
public class ExpenseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @RequestMapping(value = "/expense-create", produces = "application/json", method = RequestMethod.POST)
    public String getWalletByName(@RequestParam Map<String, String> params) {
        return success().put("data", JSON.serialize(params).toString()).toString();
    }

}
