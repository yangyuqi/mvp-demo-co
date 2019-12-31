package com.wxjz.module_base.base;

import com.wxjz.module_base.mvp.IBasePresenter;
import com.wxjz.module_base.mvp.IBaseView;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.module_base.widgt.ProgressDialog;

/**
 * Created by a on 2019/7/5.
 */

public abstract class BaseMvpActivity<P extends IBasePresenter> extends BaseActivity implements IBaseView {

    public P mPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

    }

    /**
     * 默认不注册eventbus
     *
     * @return
     */
    @Override
    protected boolean needEventBus() {
        return false;
    }

    protected abstract P createPresenter();

    @Override
    public void toast(String msg) {
        ToastUtil.show(this, msg);
    }

    @Override
    public void toast(int resId) {
        ToastUtil.show(this, resId);
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setContent("");
            progressDialog.setCancelAble(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
