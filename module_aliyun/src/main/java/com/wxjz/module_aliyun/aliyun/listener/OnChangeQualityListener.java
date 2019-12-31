package com.wxjz.module_aliyun.aliyun.listener;

/**
 * @ClassName OnChangeQualityListener
 * @Description TODO
 * @Author liufang
 * @Date 2019-08-28 10:16
 * @Version 1.0
 */
public interface OnChangeQualityListener {

    void onChangeQualitySuccess(String quality);

    void onChangeQualityFail(int code, String msg);
}
