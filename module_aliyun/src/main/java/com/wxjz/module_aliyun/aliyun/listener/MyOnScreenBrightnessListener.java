package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * @ClassName MyOnScreenBrightnessListener
 * @Description TODO
 * @Author liufang
 * @Date 2019-10-12 14:28
 * @Version 1.0
 */
public class MyOnScreenBrightnessListener implements AliyunVodPlayerView.OnScreenBrightnessListener {
    WeakReference<Activity> weakReference;

    public MyOnScreenBrightnessListener(Activity activity) {
        this.weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onScreenBrightness(int Brightness) {
        Activity activity = weakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity) activity).setBrightness(Brightness);
            }
        }
    }
}
