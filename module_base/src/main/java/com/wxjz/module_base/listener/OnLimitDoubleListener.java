package com.wxjz.module_base.listener;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/25
 * Time: 16:22
 */
public abstract class OnLimitDoubleListener implements View.OnClickListener {
    //最后一次
    private static final int MIN_CLICK_TIME = 2000;
    //记录所有绑定View的最后一次点击时间
    private SparseArray<Long> lastClickViewArray = new SparseArray<>();

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        long lastClickTime = lastClickViewArray.get(v.getId(), -1L);
        if (currentTime - lastClickTime > MIN_CLICK_TIME) {
            lastClickViewArray.put(v.getId(), currentTime);
            onLimitDouble(v);
        }
    }

    /**
     * 限制View双击事件
     */
    public abstract void onLimitDouble(View v);
}
