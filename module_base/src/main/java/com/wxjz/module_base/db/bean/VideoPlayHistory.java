package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by a on 2019/10/10.
 */

public class VideoPlayHistory  extends LitePalSupport{
    private String vid;
    private long position;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }
}
