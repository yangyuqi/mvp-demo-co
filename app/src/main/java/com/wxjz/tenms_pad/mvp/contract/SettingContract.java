package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.mvp.IBaseView;

/**
 * Created by a on 2019/9/11.
 */

public interface SettingContract {
    interface Presenter {
        void changePassword(String userId,String pass,String passSure);
    }
    interface View extends IBaseView{
            void onChangePassSuccess();
    }
}
