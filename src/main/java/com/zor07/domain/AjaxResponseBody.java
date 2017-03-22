package com.zor07.domain;

import java.util.List;

/**
 * Created by anzor on 22.03.17.
 */
public class AjaxResponseBody {

    private String msg;
    List<Entry> result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Entry> getResult() {
        return result;
    }

    public void setResult(List<Entry> result) {
        this.result = result;
    }
}
