package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.aliyun.player.IPlayer;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;

import java.lang.ref.WeakReference;

/**
 * 播放完成监听
 */

public class MyCompletionListener implements IPlayer.OnCompletionListener {

    private WeakReference<Activity> activityWeakReference;

    public MyCompletionListener(Activity skinActivity) {
        activityWeakReference = new WeakReference<Activity>(skinActivity);
    }

    @Override
    public void onCompletion() {

        Activity activity = activityWeakReference.get();
        if (activity instanceof LandscapeVideoActivity) {
            ((LandscapeVideoActivity) activity).onCompletion();
        }
    }
}



