package com.wxjz.module_base.event;

/**
 * Created by a on 2019/10/15.
 * 视频播放完毕，退出事件
 */

public class OnFinishVideoEvent {
    private boolean isFinish;

    public OnFinishVideoEvent(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
