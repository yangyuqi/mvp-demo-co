package com.wxjz.module_aliyun.aliyun.listener;

import com.aliyun.player.source.VidSts;

/**
 * @ClassName RefreshStsCallback
 * @Description TODO
 * @Author liufang
 * @Date 2019-08-28 16:21
 * @Version 1.0
 */
public interface RefreshStsCallback {
    VidSts refreshSts(String vid, String quality, String format, String title, boolean encript);
}
