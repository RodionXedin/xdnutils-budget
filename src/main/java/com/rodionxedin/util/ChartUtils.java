package com.rodionxedin.util;

import com.rodionxedin.service.machine.TimeMachineUserEntry;
import com.rodionxedin.model.Change;
import com.rodionxedin.model.User;
import com.rodionxedin.model.Wallet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by rodion on 15.03.2016.
 */
public class ChartUtils {

    public static JSONObject createChart(Wallet wallet, TimeMachineUserEntry timeMachineUserEntry) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", new JSONObject().put("text", "Balance Chart").put("x", -20));
        jsonObject.put("xAxis", new JSONObject().put("categories", getXAxisArray(wallet.getChanges())));
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
        timeMachineUserEntry.getStates(wallet).forEach(localDateBigDecimalPair -> actualStates.put(localDateBigDecimalPair.getRight().doubleValue()));
        jsonObject.put("series", new JSONArray()
                .put(new JSONObject()
                        .put("name", "Actual State")
                        .put("data", actualStates)));
        return jsonObject;
    }

    private static JSONArray getXAxisArray(List<Change> changes) {
        JSONArray jsonArray = new JSONArray();
        // in future make main spots, for now keep to change dates
        changes.forEach(change -> jsonArray.put(change.getDateConverted()));
        return jsonArray;
    }


    private static JSONArray getYAxisArray(Wallet wallet , TimeMachineUserEntry timeMachineUserEntry) {
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
    /*
    title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });
     */
}
