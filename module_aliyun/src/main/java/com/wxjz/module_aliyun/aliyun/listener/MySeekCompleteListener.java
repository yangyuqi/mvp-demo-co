package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.aliyun.player.IPlayer;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;

import java.lang.ref.WeakReference;

/**
 * Created by a on 2019/7/23.
 */
public class MySeekCompleteListener implements IPlayer.OnSeekCompleteListener {
    WeakReference<Activity> weakReference;

    public MySeekCompleteListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onSeekComplete() {
        Activity activity = weakReference.get();
        if (activity instanceof LandscapeVideoActivity) {
            ((LandscapeVideoActivity) activity).onSeekComplete();

        }
    }
}