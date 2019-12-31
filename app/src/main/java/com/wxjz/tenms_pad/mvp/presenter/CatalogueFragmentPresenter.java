package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.fragment.CatalogueFragment;
import com.wxjz.tenms_pad.mvp.contract.CatalogueFragmentContract;

/**
 * Created by a on 2019/9/20.
 */

public class CatalogueFragmentPresenter extends BasePresenter<CatalogueFragmentContract.View> implements CatalogueFragmentContract.Presenter {

    private final MainPageApi mainPageApi;

    public CatalogueFragmentPresenter() {
        mainPageApi = create(MainPageApi.class);
    }

    @Override
    public void getVideoList(int courseId, int page, int rows) {
        makeRequest(mainPageApi.getVideoList(courseId, page, rows), new BaseObserver<CourseOutlineBean>() {
            @Override
            protected void onSuccess(CourseOutlineBean courseOutlineBean) {
                getView().onVideoList(courseOutlineBean);
            }
        });
    }

    @Override
    public void addVideoClickCount(int courseId,int videoId) {
        makeRequest(mainPageApi.addVideoClickCount(courseId,videoId), new BaseObserver<LoginBean>() {
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
    public void getLoadMoreVideoList(int courseId, int page, int rows) {
        makeRequest(mainPageApi.getVideoList(courseId, page, rows), new BaseObserver<CourseOutlineBean>() {
            @Override
            protected void onSuccess(CourseOutlineBean courseOutlineBean) {

                getView().onLoadMoreVideoList(courseOutlineBean);
            }
        });
    }
}
