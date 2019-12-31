package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;


/**
 * 判断是否有网络的监听
 */
public class MyNetConnectedListener implements AliyunVodPlayerView.NetConnectedListener {
    WeakReference<Activity> weakReference;

    public MyNetConnectedListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onReNetConnected(boolean isReconnect) {
        Activity activity = weakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity) activity).onReNetConnected(isReconnect);

            }
        }
    }

    @Override
    public void onNetUnConnected() {
        Activity activity = weakReference.get();
        if (activity instanceof LandscapeVideoActivity) {
            ((LandscapeVideoActivity) activity).onNetUnConnected();

        }
    }
}
