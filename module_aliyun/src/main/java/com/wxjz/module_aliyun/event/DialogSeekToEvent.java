package com.wxjz.module_aliyun.event;

/**
 * @ClassName DialogSeekToEvent
 * @Description 将需要跳转的进度通过EventBus传递过来
 * @Author liufang
 * @Date 2019-09-02 14:58
 * @Version 1.0
 */
public class DialogSeekToEvent {
    private long tooSeekLocation;

    public DialogSeekToEvent(long tooSeekLocation) {
        this.tooSeekLocation = tooSeekLocation;
    }

    public long getTooSeekLocation() {
        return tooSeekLocation;
    }

    public void setTooSeekLocation(long tooSeekLocation) {
        this.tooSeekLocation = tooSeekLocation;
    }
}
