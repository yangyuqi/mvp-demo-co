package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.FreeVideoListBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.module_base.mvp.IBaseView;

/**
 * Created by a on 2019/9/20.
 */

public interface FreeCourseContract {
    interface Presenter {
        void getFreeCourse(int levelId, String gradeId, int page, int rows);

        void getMoreFreCourse(int levelId, String gradeId, int page, int rows);
    }

    interface View extends IBaseView {
        void onFreeCourse(FreeVideoListBean freeCourse);

        void loadMoreFreeCourse(FreeVideoListBean freeCourse);
    }
}
