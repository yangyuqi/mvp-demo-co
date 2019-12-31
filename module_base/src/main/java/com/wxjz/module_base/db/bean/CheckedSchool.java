package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by a on 2019/9/19.
 */

public class CheckedSchool extends LitePalSupport {
    private int id;
    private int schId;
    private String schName;
    private String lxdh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchId() {
        return schId;
    }

    public void setSchId(int schId) {
        this.schId = schId;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }
}
