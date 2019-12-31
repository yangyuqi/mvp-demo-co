package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.CourseDetailBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.bean.VideoDetailBean;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.activity.CourseDetailActivity;
import com.wxjz.tenms_pad.mvp.contract.CourseDetailContract;

/**
 * Created by a on 2019/9/20.
 */

public class CourseDetailPresenter extends BasePresenter<CourseDetailContract.View> implements CourseDetailContract.Presenter {

    private final MainPageApi mainPageApi;

    public CourseDetailPresenter(CourseDetailActivity activity) {
        mainPageApi = create(MainPageApi.class);
    }





    @Override
    public void getVideoDetail(Integer id,Integer chapterId,Integer sectionId,String gradeId,int levelId) {
        makeRequest(mainPageApi.getVideoDetail(id,chapterId,sectionId,gradeId,levelId), new BaseObserver<VideoDetailBean>() {
            @Override
            protected void onSuccess(VideoDetailBean courseDetailBean) {
                getView().onVideoDetail(courseDetailBean);
            }
        });
    }

    @Override
    public void addCourseClickCount(int courseId) {
        makeRequest(mainPageApi.addCourseClickCount(courseId), new BaseObserver<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean loginBean) {

            }
        });

    }

    @Override
    public void getPlayAuthByVid(String vid) {
        makeRequest(mainPageApi.getPlayAuthByVid(vid), new BaseObserver<PlayAuthBean>() {
            @Override
            protected void onSuccess(PlayAuthBean playAuthBean) {
                getView().onPlayAuth(playAuthBean);
            }
        });
    }

    @Override
    public void addVideoClickNum(int vdeoId, int courseId) {
        makeRequest(mainPageApi.addVideoClickCount(courseId, vdeoId), new BaseObserver<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean loginBean) {

            }
        });
    }

    @Override
    public void getUserInfo() {
        makeRequest(mainPageApi.getUserInfo(), new BaseObserver<UserInfoBean>() {
            @Override
            protected void onSuccess(UserInfoBean userInfoBean) {
                getView().onUserInfo(userInfoBean);
            }
        });
    }
}
