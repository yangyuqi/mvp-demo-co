package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by a on 2019/7/23.
 */
public class MySeekStartListener implements AliyunVodPlayerView.OnSeekStartListener {
    WeakReference<Activity> weakReference;

    public MySeekStartListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onSeekStart(int position) {
        Activity activity = weakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity) activity).onSeekStart(position);

            }
        }
    }
}
