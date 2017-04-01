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
    private BigDecimal totalAmount;

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
        totalAmount = new BigDecimal(0);
        entries.stream().forEach(entry ->
            totalAmount.add(entry.getAmount())
        );
        this.entries = entries;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
