package com.wxjz.module_base.event;

/**
 * Created by a on 2019/9/17.
 */

public class ErrorItemChekEvent {
    private boolean check;
    public ErrorItemChekEvent(boolean check){
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
