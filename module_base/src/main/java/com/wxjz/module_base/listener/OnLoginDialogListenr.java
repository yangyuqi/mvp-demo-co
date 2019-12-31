package com.wxjz.module_base.listener;

import android.app.Dialog;

import com.wxjz.module_base.bean.UserInfoBean;

/**
 * @ClassName onLoginListenr
 * @Description 登录页面的逻辑暴露到外面
 * @Author liufang
 * @Date 2019-11-20 14:47
 * @Version 1.0
 */
public interface OnLoginDialogListenr {

    void onBeginRequest();

    void onChooseSchool();

    void onLosePassword();

    void onLoginSuccess(Dialog loginDialog);

    void onLoginError();
}
