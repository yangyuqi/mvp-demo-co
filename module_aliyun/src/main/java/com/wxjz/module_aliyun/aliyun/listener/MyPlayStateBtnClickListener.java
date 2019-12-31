package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by a on 2019/7/23.
 */

public class MyPlayStateBtnClickListener implements AliyunVodPlayerView.OnPlayStateBtnClickListener {
    WeakReference<Activity> weakReference;

    public MyPlayStateBtnClickListener(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }


    @Override
    public void onPlayBtnClick(int playerState) {
        Activity activity = weakReference.get();
        if (activity instanceof LandscapeVideoActivity) {
            ((LandscapeVideoActivity) activity).onPlayStateSwitch(playerState);

        }
    }
}
