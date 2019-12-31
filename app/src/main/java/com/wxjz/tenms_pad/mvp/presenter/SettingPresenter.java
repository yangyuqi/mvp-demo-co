package com.wxjz.tenms_pad.mvp.presenter;

import android.util.Log;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.base.BaseObserver2;
import com.wxjz.module_base.bean.ChangePassBean;
import com.wxjz.module_base.http.api.MinePageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.mvp.contract.MyErrorContract;
import com.wxjz.tenms_pad.mvp.contract.SettingContract;

import org.litepal.util.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by a on 2019/9/11.
 */

public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private final MinePageApi minePageApi;

    public SettingPresenter() {
        minePageApi = create(MinePageApi.class);
    }

    @Override
    public void changePassword(String userId, String pass, String passSure) {
        makeRequest2(minePageApi.changePassword(userId, pass, passSure), new BaseObserver2<ChangePassBean>() {
            @Override
            protected void onSuccess(ChangePassBean changePassBean) {
                LogUtil.d("ql","修改成功");
                getView().onChangePassSuccess();
            }
        });
    }
}
