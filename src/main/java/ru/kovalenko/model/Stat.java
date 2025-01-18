package ru.kovalenko.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Stat {
    Integer balance = 0;

    Integer generalIncome = 0;

    Integer generalExpense = 0;

    Map<UUID, Integer> summaryIncome = new HashMap<>();

    Map<UUID, Integer> summaryExpense = new HashMap<>();

    public Stat() {}

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getGeneralIncome() {
        return generalIncome;
    }

    public void setGeneralIncome(Integer generalIncome) {
        this.generalIncome = generalIncome;
    }

    public Integer getGeneralExpense() {
        return generalExpense;
    }

    public void setGeneralExpense(Integer generalExpense) {
        this.generalExpense = generalExpense;
    }

    public Map<UUID, Integer> getSummaryIncome() {
        return summaryIncome;
    }

    public void setSummaryIncome(Map<UUID, Integer> summaryIncome) {
        this.summaryIncome = summaryIncome;
    }

    public Map<UUID, Integer> getSummaryExpense() {
        return summaryExpense;
    }

    public void setSummaryExpense(Map<UUID, Integer> summaryExpense) {
        this.summaryExpense = summaryExpense;
    }
}
