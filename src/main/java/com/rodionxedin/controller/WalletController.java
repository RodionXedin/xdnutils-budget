package com.rodionxedin.controller;

import com.rodionxedin.db.UserRepository;
import com.rodionxedin.db.WalletRepository;
import com.rodionxedin.model.User;
import com.rodionxedin.model.Wallet;
import com.rodionxedin.service.currency.CurrencyServer;
import com.rodionxedin.service.machine.TimeMachine;
import com.rodionxedin.service.machine.TimeMachineUserEntry;
import com.rodionxedin.util.ChartUtils;
import com.rodionxedin.util.SessionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rodionxedin.util.JsonUtils.success;

/**
 * Created by rodio on 11.12.2015.
 */
@RestController
public class WalletController {

    @Autowired
    private CurrencyServer currencyServer;

    @Autowired
    private TimeMachine timeMachine;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @RequestMapping(value = "/get-wallet", produces = "application/json", method = RequestMethod.GET)
    public String getWalletByName(@RequestParam(value = "name") String name) {
        Wallet wallet = walletRepository.findByName(name);
        JSONObject rates = new JSONObject();
        currencyServer.getRatesMap().forEach((s, bigDecimal) -> rates.put(s, bigDecimal.doubleValue()));
        return success().put("wallet", converWallettToJSON(wallet)).put("rates", rates).toString();
    }

    @RequestMapping(value = "/get-rates", produces = "application/json", method = RequestMethod.GET)
    public String getRates() {
        JSONObject rates = new JSONObject();
        currencyServer.getRatesMap().forEach((s, bigDecimal) -> rates.put(s, bigDecimal.doubleValue()));
        return success().put("rates", rates).toString();
    }


    @RequestMapping(value = "/get-general-chart", produces = "application/json", method = RequestMethod.GET)
    public String getGeneralChart(@RequestParam(value = "name") String name) {
        Wallet wallet = walletRepository.findByName(name);
        User user = (User) SessionUtils.getSession().getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
        TimeMachineUserEntry timeMachineUserEntry = timeMachine.getUserEntry(user);
        return success().put("chart", ChartUtils.createChart(timeMachineUserEntry.getActualWalletEntry(wallet),
                timeMachineUserEntry)).toString();
    }


    @RequestMapping(value = "/get-user-wallets", produces = "application/json", method = RequestMethod.GET)
    public String getCurrentWallets() {
        User user = (User) SessionUtils.getSession().getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
        List<Wallet> wallets = user.getWallets();
        wallets.removeIf(wallet -> wallet == null);
        JSONArray jsonArray = new JSONArray();
        wallets.forEach(wallet -> jsonArray.put(converWallettToJSON(wallet)));
        return success().put("wallets", jsonArray).toString();
    }

    //should create a valid JSON from wallet. Consider extracting it somewhere someday
    private JSONObject converWallettToJSON(Wallet wallet) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", wallet.getName());
        //TODO: rewrite to match some logics
        jsonObject.put("shortenedName", wallet.getName().length() > 10 ? wallet.getName().substring(0, 10) : wallet.getName());
        return jsonObject;
    }


    @RequestMapping(value = "/create-wallet", produces = "application/json", method = RequestMethod.GET)
    public String createWallet(@RequestParam(value = "name") String name) {
        User user = (User) SessionUtils.getSession().getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
        Wallet wallet = new Wallet(name, user.getKey());
        user.addWallet(wallet);

        walletRepository.save(wallet);
        userRepository.save(user);
        return success().put("wallet", converWallettToJSON(wallet)).toString();
    }


    //TODO: Relocate to some appropriate space
    @RequestMapping(value = "/get-general-information", produces = "application/json", method = RequestMethod.GET)
    public String getGeneralInformation(@RequestParam(value = "name") String name) {
        Wallet wallet = walletRepository.findByName(name);
        return success().put("wallet", converWallettToJSON(wallet)).toString();
    }

}
