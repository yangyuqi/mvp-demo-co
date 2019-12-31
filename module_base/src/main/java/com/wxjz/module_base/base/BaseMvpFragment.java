package com.wxjz.module_base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wxjz.module_base.mvp.IBasePresenter;
import com.wxjz.module_base.mvp.IBaseView;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.module_base.widgt.ProgressDialog;

/**
 * Created by a on 2019/8/29.
 */

public abstract class BaseMvpFragment<P extends IBasePresenter> extends BaseFragment implements IBaseView {
    protected String TAG = getClass().getSimpleName();
    private ProgressDialog progressDialog;
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
    }

    protected abstract P createPresenter();

    @Override
    public void toast(String msg) {
        ToastUtil.show(mContext, msg);
    }

    @Override
    public void toast(int resId) {
        ToastUtil.show(mContext, resId);
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setContent("");
            progressDialog.setCancelAble(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 默认不注册Eventbus
     *
     * @return
     */
    @Override
    public boolean needEventBus() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
