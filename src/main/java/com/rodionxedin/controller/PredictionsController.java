//package com.rodionxedin.controller;
//
//import com.mongodb.util.JSON;
//import com.rodionxedin.model.Change;
//import com.rodionxedin.model.User;
//import com.rodionxedin.model.Wallet;
//import com.rodionxedin.service.currency.CurrencyServer;
//import com.rodionxedin.util.JsonUtils;
//import com.rodionxedin.util.SessionUtils;
//import org.apache.log4j.Logger;
//import org.joda.time.LocalDate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
//import static com.rodionxedin.util.JsonUtils.success;
//
///**
// * Created by rodion.shkrobot on 8/29/2016.
// */
//@RestController
//public class PredictionsController {
//    private final Logger logger = Logger.getLogger(PredictionsController.class);
//
//    @Autowired
//    private CurrencyServer currencyServer;
//
//    @RequestMapping(value = "/predictions", produces = "application/json", method = RequestMethod.GET)
//    public String createIncome(@RequestParam Map<String, String> params) {
//
//
//        User user = (User) SessionUtils.getSession().getAttribute(SessionUtils.SessionAttributes.USER_ATTIBUTE.getAttribute());
//        Wallet wallet = user.getWallet(params.get(JsonUtils.WALLET_KEY));
//
//        Change change = new Change(Change.Type.INCOME, Change.TimeType.ONE_TIME,
//                BigDecimal.valueOf(Double.parseDouble(params.get("amount"))),
//                LocalDate.parse(params.get("date")), Change.Currency.valueOf(params.get("currency")), null, params.get("name"), null);
//
//
//        Change changeSaved = changeRepository.save(change);
//        wallet.addChange(changeSaved);
//        timeMachine.saveWallet(user, wallet);
//        return success().put("data", JSON.serialize(params).toString()).toString();
//    }
//
//
//
//}
