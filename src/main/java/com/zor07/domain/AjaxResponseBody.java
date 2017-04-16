package com.zor07.domain;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by anzor on 22.03.17.
 */
public class AjaxResponseBody {

    private String msg;
    private List<Entry> entries;
    private BigDecimal totalIncome;
    private BigDecimal totalOutcome;
    private BigDecimal balance;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        Collections.sort(entries);
        totalIncome = new BigDecimal(0);
        totalOutcome = new BigDecimal(0);
        entries.stream().forEach(entry -> {
            if (entry.getEntryType() == EntryType.INCOME){
                totalIncome = totalIncome.add(entry.getAmount());
            } else {
                totalOutcome = totalOutcome.add(entry.getAmount());
            }
        });
        balance = totalIncome.subtract(totalOutcome);
        this.entries = entries;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalOutcome() {
        return totalOutcome;
    }

    public void setTotalOutcome(BigDecimal totalOutcome) {
        this.totalOutcome = totalOutcome;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
