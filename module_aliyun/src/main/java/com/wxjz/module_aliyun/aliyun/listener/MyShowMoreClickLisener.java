package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.view.control.ControlView;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by a on 2019/7/23.
 */

public class MyShowMoreClickLisener implements AliyunVodPlayerView.OnShowMoreClickListener {
    WeakReference<Activity> weakReference;

    public MyShowMoreClickLisener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void showMore() {
        Activity activity = weakReference.get();
        if (activity instanceof LandscapeVideoActivity) {
            ((LandscapeVideoActivity) activity).showMore((LandscapeVideoActivity) activity);

        }

    }
}