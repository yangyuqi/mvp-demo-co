/*
 * Copyright (c) 2017. wisedu.com
 */

package com.wxjz.module_base.util;

import android.util.Log;

import com.wxjz.module_base.BuildConfig;

/**
 * 日志工具
 * Created by wjj on 2016/08/30 14:27:59.
 */
public class LogUtils {
    private static boolean flag = BuildConfig.DEBUG;

    public static void i(String tag, String msg) {
        if (flag) {
            Log.i(tag, msg + "");
        }
    }

    public static void d(String tag, String msg) {
        if (flag) {
            Log.d(tag, msg + "");
        }
    }

    public static void e(String tag, String msg) {
        if (flag) {
            Log.e(tag, msg + "");
        }
    }

    public static void w(String tag, String msg) {
        if (flag) {
            Log.w(tag, msg + "");
        }
    }
}
