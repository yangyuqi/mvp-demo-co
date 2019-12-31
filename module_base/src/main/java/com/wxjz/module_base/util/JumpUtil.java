package com.wxjz.module_base.util;

import android.content.Context;
import android.content.Intent;

import com.wxjz.module_base.base.BaseActivity;
import com.wxjz.module_base.bean.VideoDetailBean;

import java.util.List;

/**
 * Created by a on 2019/9/20.
 */

public class JumpUtil {
    /**
     * 课程详情
     *
     * @param context
     * @param clazz
     * @param courseId
     */
    public static void jump2CourseDetailActivity(Context context, Class clazz, String videoTitle, int courseId, int subId, boolean isFree) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("courseId", courseId);
        intent.putExtra("subId", subId);
        intent.putExtra("isFree", isFree);
        intent.putExtra("videoTitle", videoTitle);
        context.startActivity(intent);
    }

    /**
     * 课程详情
     *
     * @param context
     * @param clazz
     * @param id        传递视频主键Id，章id,节id
     * @param chapterId
     * @param sectionId
     * @param gradeId
     */
    public static void jump2CourseDetailActivity(Context context, Class clazz, int id, int chapterId, int sectionId, String gradeId) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("id", id);
        intent.putExtra("chapterId", chapterId);
        intent.putExtra("sectionId", sectionId);
        intent.putExtra("gradeId", gradeId);
        context.startActivity(intent);
    }

    public static void jump2VideoActivity(Context context, Class clazz, String url, String title, int id) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    public static void jump2VideoActivity(Context mContext, Class clazz, String playAuth, String videoId, int id, int subId, String chapterId, String sectionId, String gradeId
            , long videoDuration) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtra("playAuth", playAuth);
        intent.putExtra("videoId", videoId);
        intent.putExtra("id", id);
        intent.putExtra("subId", subId);
        intent.putExtra("chapterId", chapterId);
        intent.putExtra("sectionId", sectionId);
        intent.putExtra("gradeId", gradeId);
        intent.putExtra("videoDuration", videoDuration);
        //intent.putExtra("isFree", isFree);
//        intent.putExtra("courseId", courseId);
//        intent.putExtra("courseName", courseName);
//        intent.putExtra("courseCover", courseCover);
        mContext.startActivity(intent);

    }

    public static void jump2Mine(BaseActivity mContext, Class mineActivityClass, int checkPosition) {
        Intent intent = new Intent(mContext, mineActivityClass);
        intent.putExtra("checkPosition", checkPosition);
        mContext.startActivity(intent);
    }
}
