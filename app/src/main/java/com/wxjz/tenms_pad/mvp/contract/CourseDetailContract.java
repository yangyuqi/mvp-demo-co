package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.CourseDetailBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.bean.VideoDetailBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/20.
 */

public interface CourseDetailContract {
    interface Presenter {

        void getVideoDetail(Integer id,Integer chapterId,Integer sectionId,String gradeId,int levelId);

        void addCourseClickCount(int courseId);

        void getPlayAuthByVid(String vid);
        void addVideoClickNum(int vdeoId,int courseId);
        void getUserInfo();

    }

    interface View extends IBaseView {
        void onAboutCourse( List<VideoDetailBean.AboutListBean> aboutList);

        void onUserInfo(UserInfoBean userInfoBean);
        void onVideoDetail(VideoDetailBean courseDetailBean);

        void onPlayAuth(PlayAuthBean playAuthBean);
    }
}
