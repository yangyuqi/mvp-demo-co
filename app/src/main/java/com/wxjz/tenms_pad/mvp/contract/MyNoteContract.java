package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public interface MyNoteContract {
    interface Presenter {
        void getTopTabs(int levelId);

    }
    interface View extends IBaseView{
        void onTopTabData(List<CourseItemPage> itemPageList);
    }
}
