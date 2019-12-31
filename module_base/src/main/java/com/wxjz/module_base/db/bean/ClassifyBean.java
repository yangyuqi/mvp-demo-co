package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by a on 2019/9/3.
 */

public class ClassifyBean extends LitePalSupport {
    private int id;
    private String checked_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChecked_id() {
        return checked_id;
    }

    public void setChecked_id(String checked_id) {
        this.checked_id = checked_id;
    }
}
