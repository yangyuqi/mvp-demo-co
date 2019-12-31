package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.ErrorProblemBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/18.
 */

public interface FragmentInMyErrorContract {
    interface Presenter {
        void getErrorProblem(String userId, Integer subId, int domType, int page, int rows, int stuLevelId);

        void deleteError(String id, int domType);

        void getPlayAuthByVid(String vid);
    }

    interface View extends IBaseView {
        void onErrorProblem(List<ErrorProblemBean> problemBeans);

        void onErrorProblemMore(List<ErrorProblemBean> problemBeans);

        void onDeleteError();

        void onPlayAuth(PlayAuthBean playAuthBean);
    }
}
