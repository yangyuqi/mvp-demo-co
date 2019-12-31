package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/25
 * Time: 15:45
 */
public class MyExerciseClickListener implements AliyunVodPlayerView.OnExerciseClickListener {

    WeakReference<Activity> weakReference;

    public MyExerciseClickListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onExerciseClick() {
        Activity activity = weakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity)activity).onExercise();
            }
        }
    }
}
