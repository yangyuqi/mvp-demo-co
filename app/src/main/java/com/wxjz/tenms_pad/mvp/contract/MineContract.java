package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.UpdateInfoBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.mvp.IBaseView;

/**
 * Created by a on 2019/9/11.
 */

public interface MineContract {
    interface Presenter {
        void updateHeadUrl(String url);
        void getUserInfo();

    }
    interface View extends IBaseView{
        void onUpdateHeadUrl(UpdateInfoBean updateInfoBean);
        void onUpdateHeadError(String error);
        void onUserInfo(UserInfoBean infoBean);
    }
}
