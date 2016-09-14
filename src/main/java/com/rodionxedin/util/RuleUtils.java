package com.rodionxedin.util;

/**
 * Created by rodion.shkrobot on 9/13/2016.
 */
public class RuleUtils {


    public static String convertTextToCron(String textRule) {
        //TODO: Implement
        return "";
    }

    public static long convertToDelay(String textRule) {
        String[] split = textRule.toLowerCase().trim().split(" ");
        if (split[0].trim().equals("every")) {
            switch (split[1].trim()) {
                case "day":
                    return 86400000;
                case "week":
                    return 604800000;
                case "month":
                    return 2628000000L;
                case "year":
                    return 31540000000L;
            }
        }
        return Long.MAX_VALUE;
    }
}
