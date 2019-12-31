package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/25
 * Time: 15:15
 */
public class MyOnHintClickListener implements AliyunVodPlayerView.OnHintClickListener {
    WeakReference<Activity> weakReference;

    public MyOnHintClickListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onHintClick() {
        Activity activity = weakReference.get();
        if (activity != null) {
            ((LandscapeVideoActivity) activity).onHint();
        }
    }
}
