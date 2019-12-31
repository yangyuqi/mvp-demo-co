package com.wxjz.module_aliyun.aliyun.listener;

import android.app.Activity;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;

import java.lang.ref.WeakReference;

/**
 * Created by a on 2019/7/23.
 */

public class MySectionClickListener implements AliyunVodPlayerView.OnSectionsClickListener {
    WeakReference<Activity> weakReference;

    public MySectionClickListener(Activity aliyunActivity) {
        weakReference = new WeakReference<>(aliyunActivity);
    }

    @Override
    public void onSectionsClick() {
        Activity activity = weakReference.get();
            if (activity instanceof LandscapeVideoActivity){
               // ((LandscapeVideoActivity) activity).showSections((LandscapeVideoActivity) activity);

            }
    }
}
