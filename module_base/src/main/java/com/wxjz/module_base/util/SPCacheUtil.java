package com.wxjz.module_base.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.wxjz.module_base.BuildConfig;
import com.wxjz.module_base.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * SharedPreferences统一处理工具类
 * <br/>
 * 在使用SharedPreferences中，所用应用通用的key请在{@link SPCacheUtil}声明为静态变量
 *
 * @author ximi
 * @date 2015年3月24日上午9:22:23
 */
public class SPCacheUtil {
    /**
     * 是否是第一次登陆
     */
    public static final String FIRSTLOAD = "FIRSTLOAD";
    /**
     * 是否是第一次加载数据库，测试用，后期删除
     */
    public static final String FIRSTDATE = "FIRSTDATA";
    /**
     * 判断选择了哪个学校代码默认是0；
     */
    public static final String SCHOOL_POSITION = "SCHOOL_POSITION";
    /**
     * 判断选择了哪个学校的名称，默认是null
     */
    public static final String SCHOOL_NAME = "SCHOOL_NAME";
    /**
     * 保存Cookies;
     */
    public static final String COOKIES = "COOKIES";
    /**
     * 学校的列表
     */
    public static final String SCHOOLLIST = "SCHOOLLIST";

    private static SharedPreferences mSpCache;

    /**
     * 清理sp缓存
     *
     * @Author ximi
     * @date 2015年5月28日下午2:12:46
     */
    public static void clearSpCache() {
        if (mSpCache != null) {
            mSpCache.edit().clear().commit();
        }
    }

    /**
     * 获得的应用的sharedPreferences
     *
     * @return sharedPreferences
     */
    public static SharedPreferences getSharedPreferences() {
        if (mSpCache == null) {
            mSpCache = BaseApplication.getApplication().getSharedPreferences(BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE);
        }
        return mSpCache;
    }

    /**
     * 保存一个Boolean值
     *
     * @param key   key
     * @param value 保存值
     */
    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    /**
     * 获得一个Boolean值
     *
     * @param key      key
     * @param defValue 默认值
     * @return value
     */
    public static boolean getBoolean(String key, boolean defValue) {
        try {
            return getSharedPreferences().getBoolean(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 获得一个String值
     *
     * @param key   key
     * @param value 保存值
     */
    public static void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    /**
     * 获得一个String值
     * <br/>
     * 使用异步开启子线程方式更新字符串到SP中，在保存大数据时可以提供效率
     *
     * @param key   key
     * @param value 保存值
     */
    public static void putStringByApply(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    /**
     * 获得一个String值
     *
     * @param key      key
     * @param defValue 默认值
     * @return value
     */
    public static String getString(String key, String defValue) {
        try {
            return getSharedPreferences().getString(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 获得一个int值
     *
     * @param key   key
     * @param value 保存值
     */
    public static void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    /**
     * 获得一个int值
     *
     * @param key      key
     * @param defValue 默认值
     * @return value
     */
    public static int getInt(String key, int defValue) {
        try {
            return getSharedPreferences().getInt(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 获得一个long值
     *
     * @param key   key
     * @param value 保存值
     */
    public static void putLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).apply();
    }

    /**
     * 获得一个long值
     *
     * @param key      key
     * @param defValue 默认值
     * @return value
     */
    public static long getLong(String key, long defValue) {
        try {
            return getSharedPreferences().getLong(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 保存一个map的值
     *
     * @param key
     * @param datas
     */

    public static void putHashSetData(String key, HashSet datas) {

        getSharedPreferences().edit().putStringSet(key, datas).apply();
    }

    /**
     * 获取一个map的值
     */
    public static HashSet<String> getHashSetData(String key, HashSet defValue) {

        try {
            return (HashSet<String>) getSharedPreferences().getStringSet(key, new HashSet<String>());
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 删除一个缓存
     *
     * @param key key
     * @Author ximi
     * @date 2015年5月28日上午11:50:39
     */
    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    /**
     * 检查是否包含指定key
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        return getSharedPreferences().contains(key);
    }

    public static class PublicKey {
        /**
         * 用户ID
         */
        public static final String user_uid = "uid";
        /**
         * 系统相机默认保存图片的路径
         */
        public static final String system_default_camera_path = "system_default_camera_path";
        /**
         * 应用缓存目录
         */
        public static final String cache_path = "cache_path";
        /**
         * 应用版本名称
         */
        public static final String app_version_name = "app_version_name";
        /**
         * 默认的UserAgent
         */
        public static final String user_agent = "user_agent";
        /**
         * 获取默认的UserAgent时间戳
         */
        public static final String user_agent_time = "user_agent_time";
    }

}
