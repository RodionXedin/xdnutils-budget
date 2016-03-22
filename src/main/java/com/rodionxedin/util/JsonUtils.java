package com.rodionxedin.util;

import com.google.gson.GsonBuilder;
import com.rodionxedin.model.Change;
import com.rodionxedin.model.User;
import org.json.JSONObject;

/**
 * Created by rodio on 09.12.2015.
 */
public class JsonUtils {


    private static final String RESULT = "result";
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String USER_NAME_JSON_ATTRIBUTE = "userName";
    private static final String WALLETS_USER_JSON_ATTRIBUTE = "wallets";
    private static final String NEW_USER_JSON_ATTRIBUTE = "newUser";
    public static final String ERROR_JSON_ATTRIBUTE = "error";
    public static final String WALLET_KEY = "wallet";
    private static final String USER_DEFAULT_CURRENCY = "defaultCurrency";


    public static JSONObject success() {
        return new JSONObject().put(RESULT, SUCCESS);
    }


    public static JSONObject failure() {
        return new JSONObject().put(RESULT, FAILURE);
    }

    public static JSONObject addBasicUserInfo(JSONObject jsonObject, User user) {

        return jsonObject.put(USER_NAME_JSON_ATTRIBUTE, user.getName())
                .put(WALLETS_USER_JSON_ATTRIBUTE, new GsonBuilder().create().toJson(user.getWallets()))
                .put(NEW_USER_JSON_ATTRIBUTE, false)
                .put(USER_DEFAULT_CURRENCY, user.getDefaultCurrency());
    }

    public static JSONObject addBasicUserInfo(JSONObject jsonObject, User user, boolean newUser) {
        return jsonObject.put(USER_NAME_JSON_ATTRIBUTE, user.getName())
                .put(WALLETS_USER_JSON_ATTRIBUTE, new GsonBuilder().create().toJson(user.getWallets()))
                .put(NEW_USER_JSON_ATTRIBUTE, newUser)
                .put(USER_DEFAULT_CURRENCY, user.getDefaultCurrency());
    }

    public static JSONObject addError(JSONObject jsonObject, String text) {
        return jsonObject.put(ERROR_JSON_ATTRIBUTE, text);
    }

    public static JSONObject convertChangeToJson(Change change) {
        JSONObject jsonObject = new JSONObject();


        jsonObject.put("amount", change.getAmount());
        jsonObject.put("currency", change.getCurrency());
        jsonObject.put("date", change.getDate());
        jsonObject.put("dateConverted", change.getDateConverted());
        jsonObject.put("key", change.getKey());
        jsonObject.put("periodRule", change.getPeriodRule());
        jsonObject.put("timeType", change.getTimeType().toString());
        jsonObject.put("type", change.getType().toString());
        jsonObject.put("name", change.getName());
        jsonObject.put("explanation", change.getExplanation());

        return jsonObject;
    }

//
//    public static JSONObject addAdditionalWalletInfo(JSONObject jsonObject, Wallet wallet){
//
//    }
}
