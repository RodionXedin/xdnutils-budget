package com.rodionxedin.model.ticker;

import com.rodionxedin.model.Change;
import com.rodionxedin.util.RuleUtils;
import org.joda.time.LocalDate;

/**
 * Created by rodion.shkrobot on 9/13/2016.
 */
public class Rule {
    private String textRule;
    private String cronRule;
    private long delay;


    public Rule(String textRule) {
        this.textRule = textRule;
        this.cronRule = RuleUtils.convertTextToCron(textRule);
        this.delay = RuleUtils.convertToDelay(textRule);
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getTextRule() {
        return textRule;
    }

    public void setTextRule(String textRule) {
        this.textRule = textRule;
    }

    public String getCronRule() {
        return cronRule;
    }

    public void setCronRule(String cronRule) {
        this.cronRule = cronRule;
    }


}
