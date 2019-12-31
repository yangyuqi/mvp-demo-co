package com.wxjz.tenms_pad.mvp.contract;


import com.wxjz.module_base.bean.SearchBean;
import com.wxjz.module_base.db.bean.SearchTabContentBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/5.
 */

public interface SearchCommonContract {
    interface Presenter{
        void getSearchData(String content,int levelId, int  subiD,String gradeId);
        void addCourseClickCount(int courseId);
    }
    interface View extends IBaseView {
        void onSearchData(List<SearchBean> searchTabContent);
    }
}
