package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunScreenMode;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by a on 2019/7/23.
 */

public class MyOrientationChangeListener implements AliyunVodPlayerView.OnOrientationChangeListener {

    private final WeakReference<Activity> weakReference;

    public MyOrientationChangeListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void orientationChange(boolean from, AliyunScreenMode currentMode) {
        Activity activity = weakReference.get();
        if (activity instanceof LandscapeVideoActivity) {
            ((LandscapeVideoActivity) activity).hideDownloadDialog(from, currentMode);
            ((LandscapeVideoActivity) activity).hideShowMoreDialog(from, currentMode);
        }

    }
}