package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by a on 2019/9/2.
 */

public class HistoryBean extends LitePalSupport {


    private int id;
    private String history;


    private long time_stamp;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
}
