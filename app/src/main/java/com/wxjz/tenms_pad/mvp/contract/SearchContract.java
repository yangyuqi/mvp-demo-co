package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.RecommendBean;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.db.bean.HistoryBean;
import com.wxjz.module_base.db.bean.SearchTabBean;
import com.wxjz.module_base.db.bean.SearchTabContentBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/10.
 */

public interface SearchContract {
    interface Presenter {
        void getSearchHistory();
        void getRecommendCourse(int levelId,String gradeId,int page);
        void queryAllSearchData(String searchContent, String content, int levelId);

        void addCourseClickCount(int courseId);

    }

    interface View extends IBaseView {
        void onSearchHistory(List<HistoryBean> historyDatas);
        void onRecommendCourse(RecommendBean recommendBean);
        void onAllSearchData(SearchTabContentBean searchTabContentBeans);
    }
}
