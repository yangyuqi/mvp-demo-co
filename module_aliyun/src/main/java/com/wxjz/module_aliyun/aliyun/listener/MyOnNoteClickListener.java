package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/25
 * Time: 14:37
 */
public class MyOnNoteClickListener implements AliyunVodPlayerView.OnNoteClicklistener {
    WeakReference<Activity> weakReference;

    public MyOnNoteClickListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onNoteClick() {
        Activity activity = weakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity) activity).takeNote();
            }
        }
    }
}
