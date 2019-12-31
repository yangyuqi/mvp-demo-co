package com.wxjz.module_base.constant;

import android.os.Environment;

import com.wxjz.module_base.db.bean.SearchTabContentBean;

import java.util.ArrayList;

/**
 * Created by a on 2019/9/23.
 */

public class BasisConstants {
    public static final String NULL_STRING = "";


    // public static final String BASE_NEWS_WEB_URL = "http://192.168.31.27:8721/";
    public static final String BASE_NEWS_WEB_URL = "http://live.lead-reading.cn/";
    //    public static final String BASE_NEWS_WEB_URL = "http://edu.k12c.com/";
    public static String SEARCH_CONTENT = "";
    public static ArrayList<SearchTabContentBean.AllBean> SEARCH_LIST = new ArrayList<>();
//    public static final String BASE_NEWS_WEB_URL = "http://192.168.31.27:8721/";


    //提示
    public static final int TIPS_TYPE = 0;
    //术语
    public static final int TERMINOLOGY_TYPE = 1;

    public static final int EXERCISE_TYPE = 2;
    //笔记
    public static final int NOTE_TYPE = 3;


    /**
     * 文件信息配置
     */
    public class File {

        /**
         * 相册文件缓存目录
         */
        public static final String GALLERY_ROOT_PATH = "/gallery/";
        public static final String CAMERA_PATH_ROOT = "/DCIM/Camera";
        public static final String CAMERA_SHORT_PATH_ROOT = "/Camera";
        public static final String CAMERA_PATH_ROOT_NAME = "相机照片";
        public static final String WEIXIN_PATH_ROOT = "/WeiXin";
        public static final String WEIXIN_PATH_ROOT_NAME = "微信";
        public static final String SCREENSHOTS_PATH_ROOT = "/Screenshots";
        public static final String SCREENSHOTS_PATH_ROOT_NAME = "截图";

        public static final String UPDATA_CACHE_PATH = "/update/";
    }

    public class Glide {
        // 图片缓存最大容量，150M，根据自己的需求进行修改
        public static final int GLIDE_CATCH_SIZE = 150 * 1024 * 1024;

        // 图片缓存子目录
        public static final String GLIDE_CARCH_DIR = "glide_catch";
    }

    public static class Aliyun {
        public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wxjz";
        public static final String ALICACHE = CACHE_PATH + "/aliyun_save_cache";
    }

    public class ID {
        public static final String SCHID = "100075";
    }
}
