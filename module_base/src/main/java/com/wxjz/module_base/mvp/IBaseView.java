package com.wxjz.module_base.mvp;

import android.support.annotation.StringRes;

/**
 * Created by a on 2019/7/5.
 */

public interface IBaseView {
    void toast(String msg);

    void toast(@StringRes int resId);

    void showLoading();

    void hideLoading();
}
