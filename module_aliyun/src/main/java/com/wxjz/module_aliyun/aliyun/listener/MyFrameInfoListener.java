package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.aliyun.player.IPlayer;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;

import java.lang.ref.WeakReference;

/**
 * 第一贞监听
 */

public class MyFrameInfoListener implements IPlayer.OnRenderingStartListener {

    private WeakReference<Activity> activityWeakReference;

    public MyFrameInfoListener(Activity skinActivity) {
        activityWeakReference = new WeakReference<Activity>(skinActivity);
    }

    @Override
    public void onRenderingStart() {

        Activity activity = activityWeakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity) activity).onFirstFrameStart();
            }
        }
    }
}
