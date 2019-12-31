package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.aliyun.player.IPlayer;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;

import java.lang.ref.WeakReference;

/**
 * Created by a on 2019/7/16.
 */

public class MyPrepareListener implements IPlayer.OnPreparedListener {
    private WeakReference<Activity> activityWeakReference;

    public MyPrepareListener(Activity skinActivity) {
        activityWeakReference = new WeakReference<Activity>(skinActivity);
    }

    @Override
    public void onPrepared() {
        Activity activity = activityWeakReference.get();
        if (activity != null) {
            if (activity instanceof LandscapeVideoActivity) {
                ((LandscapeVideoActivity) activity).onPrepared();
            }
        }
    }
}
