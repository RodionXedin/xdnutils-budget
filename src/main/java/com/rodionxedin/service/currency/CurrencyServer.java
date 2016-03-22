package com.rodionxedin.service.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodio on 21.03.2016.
 */
@Service
public class CurrencyServer {

    private final Logger logger = Logger.getLogger(CurrencyServer.class);


    private static final String CURRENCY_URL =
"https://query.yahooapis.com/v1/public/yql?q=select+*+from+yahoo.finance.xchange+where+pair+=+%22" +
        "USDRUB,EURRUB,UAHRUB,RUBUAH,USDRUB,EURRUB,UAHUSD,UAHEUR,USDEUR,EURUSD,USDUAH,EURUAH" +
        "%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";


    private Map<String, BigDecimal> ratesMap = new HashMap<>();


    @Scheduled(cron = "0 0/5 * 1/1 * ?")
    @PostConstruct
    private void refreshRates() throws IOException {

        logger.info("Requesting Currency data");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(new URL(CURRENCY_URL), Map.class);

        List<Map<String, String>> rates = ((Map<String, Map<String, List<Map<String, String>>>>) map.get("query")).get("results").get("rate");

        for (Map<String, String> rate : rates) {
            ratesMap.put(rate.get("id"), BigDecimal.valueOf(Double.valueOf(rate.get("Rate"))));
        }
        logger.info("Processed Currency data");

    }

    public BigDecimal get(String key) {
        return ratesMap.get(key);
    }

    public Map<String, BigDecimal> getRatesMap() {
        return ratesMap;
    }

    public boolean containsKey(Object key) {
        return ratesMap.containsKey(key);
    }
}
