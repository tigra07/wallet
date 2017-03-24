package com.zor07.domain;

import java.util.Set;

/**
 * Created by anzor on 22.03.17.
 */
public class AjaxResponseBody {

    private String msg;
    Set<Entry> entries;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }
}
