package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/25
 * Time: 16:04
 */
public class MyAnalysisClickListener implements AliyunVodPlayerView.OnAnalysisClickListener {

    WeakReference<Activity> weakReference;

    public MyAnalysisClickListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onAnalysisClick() {
        Activity activity = weakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity)activity).onTerminology();
            }
        }
    }
}
