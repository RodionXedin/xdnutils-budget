package com.rodionxedin.model;

import com.google.common.base.Objects;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * Created by rodio on 08.12.2015.
 */

@Document(collection = Change.COLLECTION_NAME)
public class Change {
    public static final String COLLECTION_NAME = "change";


    @Id
    private String key;


    public enum Type {
        INCOME, OUTCOME
    }

    public enum TimeType {
        ONE_TIME, PERIODICAL
    }

    public enum Currency {
        USD, HRN
    }

    @PostConstruct
    private void init() {
        if (dateConverted != null) {
            date = new LocalDate(dateConverted);
        }
    }

    private Type type;
    private TimeType timeType;
    private BigDecimal amount;
    @Transient
    private LocalDate date;

    private String name;
    private String explanation;
    private String dateConverted;
    private Currency currency;
    private String periodRule;


    public Change() {
    }

    public Change(Type type, TimeType timeType, BigDecimal amount, LocalDate date, Currency currency, String periodRule, String name, String explanation) {
        this.type = type;
        this.timeType = timeType;
        this.amount = amount;
        this.date = date;
        this.dateConverted = date.toString();
        this.currency = currency;
        this.periodRule = periodRule;
        this.name = name;
        this.explanation = explanation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Type getType() {
        return type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        if (date == null && dateConverted != null) {
            date = new LocalDate(dateConverted);
        }
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.dateConverted = date.toString();
    }

    public String getPeriodRule() {
        return periodRule;
    }

    public void setPeriodRule(String periodRule) {
        this.periodRule = periodRule;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getDateConverted() {
        return dateConverted;
    }

    public void setDateConverted(String dateConverted) {
        this.dateConverted = dateConverted;
        this.date = LocalDate.parse(dateConverted);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Change change = (Change) o;
        return type == change.type &&
                timeType == change.timeType &&
                Objects.equal(amount, change.amount) &&
                Objects.equal(date, change.date) &&
                Objects.equal(name, change.name) &&
                Objects.equal(explanation, change.explanation) &&
                Objects.equal(dateConverted, change.dateConverted) &&
                currency == change.currency &&
                Objects.equal(periodRule, change.periodRule);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, timeType, amount, date, name, explanation, dateConverted, currency, periodRule);
    }
}
