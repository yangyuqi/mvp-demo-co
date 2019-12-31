package com.wxjz.module_aliyun.event;

/**
 * Created by a on 2019/8/12.
 */

public class DialogDissmissEvent {
    private boolean dismiss;
    private int type;

    public DialogDissmissEvent(boolean dismiss) {
        this.dismiss = dismiss;
    }

    public DialogDissmissEvent(boolean dismiss, int type) {
        this.dismiss = dismiss;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isDismiss() {
        return dismiss;
    }

    public void setDismiss(boolean dismiss) {
        this.dismiss = dismiss;
    }
}
