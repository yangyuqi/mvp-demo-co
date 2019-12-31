package com.wxjz.module_base.event;

/**
 * Created by a on 2019/9/18.
 */

public class HaveErrorProblemEvent {
    public boolean isHaveError() {
        return haveError;
    }

    public void setHaveError(boolean haveError) {
        this.haveError = haveError;
    }

    private boolean haveError = true;

    public HaveErrorProblemEvent(boolean haveError) {
        this.haveError = haveError;
    }
}
