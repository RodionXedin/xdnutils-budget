package com.rodionxedin.util;

import com.rodionxedin.model.Change;
import com.rodionxedin.model.Wallet;
import com.rodionxedin.service.machine.TimeMachineUserEntry;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by rodion on 15.03.2016.
 */
public class ChartUtils {

    public static JSONObject createChart(Wallet wallet, TimeMachineUserEntry timeMachineUserEntry) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", new JSONObject().put("text", "Balance Chart").put("x", -20));
        jsonObject.put("chart", new JSONObject().put("type", "spline"));
        jsonObject.put("xAxis", new JSONObject().put("type", "datetime"));
        jsonObject.put("yAxis", new JSONObject().put("plotLines", new JSONArray().put(new JSONObject()
                .put("value", 0)
                .put("width", 1)
                .put("color", "#808080")
        )));
        jsonObject.put("legend", new JSONObject()
                .put("layout", "vertical")
                .put("align", "right")
                .put("verticalAlign", "middle")
                .put("borderWidth", 0));


        JSONArray actualStates = new JSONArray();
        timeMachineUserEntry.getStates(wallet).forEach(localDateBigDecimalPair -> actualStates.put(
                new JSONArray().put(localDateBigDecimalPair.getLeft().toDateTime(LocalTime.MIDNIGHT)
                        .plus(TimeZone.getDefault().getOffset(localDateBigDecimalPair.getLeft().toDateTime(LocalTime.MIDNIGHT).getMillis()))
                        .getMillis()).put(localDateBigDecimalPair.getRight().doubleValue())));
        jsonObject.put("series", new JSONArray()
                .put(new JSONObject()
                        .put("name", "Actual State")
                        .put("data", actualStates)));
        return jsonObject;
    }

    private static JSONArray getXAxisArray(List<Change> changes) {
        JSONArray jsonArray = new JSONArray();
        // in future make main spots, for now keep to change dates
        return jsonArray;


    }


    private static JSONArray getYAxisArray(Wallet wallet, TimeMachineUserEntry timeMachineUserEntry) {
        JSONArray jsonArray = new JSONArray();
        BigDecimal highestState = timeMachineUserEntry.getHighestState(wallet);
        BigDecimal lowestState = timeMachineUserEntry.getLowestState(wallet);

        BigDecimal diff = highestState.subtract(lowestState);


        while (lowestState.compareTo(highestState) < 1) {
            lowestState = lowestState.add(diff.divide(BigDecimal.TEN));
            jsonArray.put(lowestState);
        }

        return jsonArray;
    }


    /**
     * Unfinished!
     *
     * @param changes
     * @return
     */
    @Deprecated
    private JSONArray getYAxisArrayForBarChart(List<Change> changes) {
        JSONArray jsonArray = new JSONArray();
        //find lowest and highest
        BigDecimal lowest = new BigDecimal(Integer.MAX_VALUE);
        BigDecimal highest = new BigDecimal(Integer.MIN_VALUE);


        for (Change change : changes) {
            if (change.getAmount().compareTo(highest) == 1) {
                highest = change.getAmount();
            }
            if (change.getAmount().compareTo(lowest) == -1) {
                lowest = change.getAmount();
            }
        }


        //those are just changes !

        //get difference

        return jsonArray;
    }
}
