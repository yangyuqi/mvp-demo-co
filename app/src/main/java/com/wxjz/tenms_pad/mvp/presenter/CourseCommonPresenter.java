package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.CourseListItemBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.SelectVideoBean;
import com.wxjz.module_base.db.bean.SubjectChapterBean;
import com.wxjz.module_base.db.bean.SubjectHomeBean;
import com.wxjz.module_base.db.bean.SubjectSectionBean;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.fragment.CourseCommonFragment;
import com.wxjz.tenms_pad.mvp.contract.CourseCommonContract;

import java.util.List;


/**
 * Created by a on 2019/9/5.
 */

public class CourseCommonPresenter extends BasePresenter<CourseCommonContract.View> implements CourseCommonContract.Presenter {

    private final MainPageApi mainPageApi;
    private CourseCommonFragment fragment;

    public CourseCommonPresenter(CourseCommonFragment fragment) {
        mainPageApi = create(MainPageApi.class);
        this.fragment = fragment;
    }


    @Override
    public void getAllCourseList(int subId, int levelId) {
        makeRequest(mainPageApi.getAllCourseList(subId, levelId), new BaseObserver<List<CourseListItemBean>>(fragment) {
            @Override
            protected void onSuccess(List<CourseListItemBean> courseListItemBeans) {
                getView().onAllCourseList(courseListItemBeans);
            }
        });
    }

    @Override
    public void getSelectVideoList(int courseId, int page, int rows) {
        makeRequest(mainPageApi.getSelectVideoList(courseId, page, rows), new BaseObserver<SelectVideoBean>(fragment) {
            @Override
            protected void onSuccess(SelectVideoBean selectVideoBeans) {
                getView().onSelectVideoList(selectVideoBeans);
            }
        });
    }

    @Override
    public void loadMoreVideoList(int courseId, int page, int rows) {
        makeRequest(mainPageApi.getSelectVideoList(courseId, page, rows), new BaseObserver<SelectVideoBean>(fragment) {
            @Override
            protected void onSuccess(SelectVideoBean selectVideoBeans) {
                if (selectVideoBeans.getList() == null || selectVideoBeans.getList().size() == 0) {
                    getView().onLoadMoreComplete();
                } else {
                    getView().onLoadMoreVideoList(selectVideoBeans);

                }
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
    public void getPlayAuth(final String videoId, final int id) {
        makeRequest(mainPageApi.getPlayAuthByVid(videoId), new BaseObserver<PlayAuthBean>() {
            @Override
            protected void onSuccess(PlayAuthBean playAuthBean) {
                getView().onPlayAuth(playAuthBean, videoId, id);
            }
        });
    }

    @Override
    public void addVideoClickCount(int vid, int courseId) {
        makeRequest(mainPageApi.addVideoClickCount(courseId, vid), new BaseObserver<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean loginBean) {

            }
        });
    }

    @Override
    public void getSubjectHome(int levelId, int subId, String gradeId) {
        makeRequest(mainPageApi.getSubjectHome(levelId, subId, gradeId), new BaseObserver<SubjectHomeBean>() {
            @Override
            protected void onSuccess(SubjectHomeBean subjectHomeBeans) {
                getView().onSubjectHome(subjectHomeBeans);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }

    @Override
    public void getChapterVideoList(int chapterId) {
        makeRequest(mainPageApi.getSubjectChapterVideoList(chapterId), new BaseObserver<List<SubjectChapterBean>>() {
            @Override
            protected void onSuccess(List<SubjectChapterBean> subjectChapterBeans) {
                getView().onChapterVideoList(subjectChapterBeans);
            }
        });

    }

    @Override
    public void getSectionIdVideoList(int sectionId) {
        makeRequest(mainPageApi.getSubjectSectionVideoList(sectionId), new BaseObserver<List<SubjectSectionBean>>() {
            @Override
            protected void onSuccess(List<SubjectSectionBean> subjectSectionBeans) {
                getView().onSectionIdVideoList(subjectSectionBeans);
            }
        });
    }

}
