package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.FreeVideoListBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.activity.FreeCourseActivity;
import com.wxjz.tenms_pad.mvp.contract.FreeCourseContract;

/**
 * Created by a on 2019/9/20.
 */

public class FreeCoursePresenter extends BasePresenter<FreeCourseContract.View> implements FreeCourseContract.Presenter {
    private FreeCourseActivity mActivity;
    private final MainPageApi mainPageApi;

    public FreeCoursePresenter(FreeCourseActivity activity){
        this.mActivity = activity;
        mainPageApi = create(MainPageApi.class);
    }
    @Override
    public void getFreeCourse(int levelId, String gradeId,int page, int rows) {
        makeRequest(mainPageApi.getFreeCourse(levelId, gradeId,page, rows), new BaseObserver<FreeVideoListBean>(mActivity) {
            @Override
            protected void onSuccess(FreeVideoListBean popularMutiItem) {
                getView().onFreeCourse(popularMutiItem);
            }
        });
    }

    @Override
    public void getMoreFreCourse(int levelId, String gradeId,int page, int rows) {
        makeRequest(mainPageApi.getFreeCourse(levelId, gradeId,page, rows), new BaseObserver<FreeVideoListBean>(mActivity) {
            @Override
            protected void onSuccess(FreeVideoListBean popularMutiItem) {
                getView().loadMoreFreeCourse(popularMutiItem);
            }
        });
    }
}
