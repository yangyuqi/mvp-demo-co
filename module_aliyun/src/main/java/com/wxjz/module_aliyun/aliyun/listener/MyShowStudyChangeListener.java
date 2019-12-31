package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * @ClassName MyShowStudyChangeListener
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-20 10:40
 * @Version 1.0
 */
public class MyShowStudyChangeListener implements AliyunVodPlayerView.OnStudyStatusListenr {
    WeakReference<Activity> weakReference;

    public MyShowStudyChangeListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onStudyStatus(boolean b) {
        Activity activity = weakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity) activity).onStudyChange(b);
            }
        }
    }
}
