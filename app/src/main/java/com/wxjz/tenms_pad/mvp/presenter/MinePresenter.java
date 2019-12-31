package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.UpdateInfoBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.http.api.MinePageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.mvp.contract.CourseCommonContract;
import com.wxjz.tenms_pad.mvp.contract.MineContract;


/**
 * Created by a on 2019/9/5.
 */

public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {

    private final MinePageApi mainPageApi;

    public MinePresenter() {
        mainPageApi = create(MinePageApi.class);
    }


    @Override
    public void updateHeadUrl(String url) {
        makeRequest(mainPageApi.updateHead(url), new BaseObserver<UpdateInfoBean>() {
            @Override
            protected void onSuccess(UpdateInfoBean updateInfoBean) {
                getView().onUpdateHeadUrl(updateInfoBean);
            }

            @Override
            public void onError(Throwable e) {
                getView().onUpdateHeadError(e.getMessage());
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
