package com.wxjz.module_base.event;

/**
 * Created by a on 2019/9/23.
 */

public class UpdateHeadEvent {

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    private String headUrl;

    public UpdateHeadEvent(String headUrl) {
        this.headUrl  = headUrl;
    }


}
