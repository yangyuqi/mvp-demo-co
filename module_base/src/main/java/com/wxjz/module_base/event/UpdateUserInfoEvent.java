package com.wxjz.module_base.event;

/**
 * Created by a on 2019/9/23.
 */

public class UpdateUserInfoEvent {

    private boolean update;

    public UpdateUserInfoEvent(boolean update) {
        this.update = update;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
