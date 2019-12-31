package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.bean.SchoollistBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/5.
 */

public interface MainContract {
    interface Presenter {
        void getTopTabs(int levelId);

        void getClassifyData();

        void getUserInfo();

        void getLeveListNoLevelID(boolean isMember);
    }

    interface View extends IBaseView {
        void onTopTabData(List<CourseItemPage> itemPageList);

        void onClassifyData(int currentId);

        void onUserInfo(UserInfoBean userInfoBean);

        void onLevelListByNoLevelID(List<LevelListBean> levelListBeans);
    }
}
