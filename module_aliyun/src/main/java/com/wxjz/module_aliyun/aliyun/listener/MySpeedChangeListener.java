package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * @ClassName MySpeedChangeListener
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-17 09:07
 * @Version 1.0
 */
public class MySpeedChangeListener implements AliyunVodPlayerView.OnSpeedChangeClickListener {
    WeakReference<Activity> weakReference;

    public MySpeedChangeListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    public void onSpeedChange() {
        Activity activity = weakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity) activity).onSpeedChange();
            }
        }
    }
}
