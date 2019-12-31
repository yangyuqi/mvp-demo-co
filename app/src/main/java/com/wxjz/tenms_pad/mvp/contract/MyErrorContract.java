package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.FilterErrorExerciseBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public interface MyErrorContract {
    interface Presenter {
        void getTopTabs(int levelId);

        void getFilte(Integer subjectId, String userId, int domType);

    }

    interface View extends IBaseView {
        void onTopTabData(List<CourseItemPage> itemPageList);

        void onFilte(List<FilterErrorExerciseBean> filterErrorExerciseBean);
    }
}
