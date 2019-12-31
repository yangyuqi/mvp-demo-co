package com.wxjz.tenms_pad.mvp.presenter;


import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.BrannerBean;
import com.wxjz.module_base.bean.FreeVideoListBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.module_base.bean.RecommendBean;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.CheckedSchool;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.CheckIdSchoolDao;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.activity.MainActivity;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.mvp.contract.HomePageContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/5.
 */

public class CourseHomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {

    private final MainPageApi mainPageApi;
    private MainActivity activity;

    public CourseHomePagePresenter(MainActivity activity) {
        this.activity = activity;
        mainPageApi = create(MainPageApi.class);
    }

    @Override
    public void getBannerData(String levelId, String gradeId) {
        String schoolId = "";
        int schId = 0;
        CheckedSchool checkedSchool = CheckIdSchoolDao.getInstance().getCheckedSchool();
        if (checkedSchool != null) {
            schId = checkedSchool.getSchId();
        }
        if (schId == 0) {
            schoolId = "";
        } else {
            schoolId = String.valueOf(schId);
        }
        makeRequest(mainPageApi.getBanners(levelId, gradeId, schoolId), new BaseObserver<BrannerBean>() {
            @Override
            protected void onSuccess(BrannerBean brannerBean) {
                List<BrannerBean.BannerListBean> bannerList = brannerBean.getBannerList();
                getView().onBanner(bannerList);

            }

            @Override
            public void onError(Throwable e) {
                getView().onBanner(null);
                super.onError(e);
            }
        });

    }

    @Override
    public void getPopularCourse(int levelId, String gradeId, int page, int rows) {
        makeRequest(mainPageApi.getWelcomeCourse(levelId, gradeId, page, rows), new BaseObserver<PopularMutiItem>(activity) {
            @Override
            protected void onSuccess(PopularMutiItem popularMutiItems) {
                getView().onPopularCourse(popularMutiItems);
            }
        });
    }

    @Override
    public void getRecommendCourse(int livelId, String gradeId, final int page) {
        makeRequest(mainPageApi.getRecommend(livelId, gradeId, page), new BaseObserver<RecommendBean>(activity) {
            @Override
            protected void onSuccess(RecommendBean recommendCourseBeanList) {
                getView().onRecommendCourse(recommendCourseBeanList, page);
            }
        });
    }

    @Override
    public void getFreeCourse(int levelId, String gradeId, int page, int rows) {
        makeRequest(mainPageApi.getFreeCourse(levelId, gradeId, page, rows), new BaseObserver<FreeVideoListBean>() {
            @Override
            protected void onSuccess(FreeVideoListBean freeVideoListBean) {
                getView().onFreeCourse(freeVideoListBean);
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
}
