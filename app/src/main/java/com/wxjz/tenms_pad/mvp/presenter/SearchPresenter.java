package com.wxjz.tenms_pad.mvp.presenter;

import android.app.Activity;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.RecommendBean;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.db.bean.HistoryBean;
import com.wxjz.module_base.db.bean.SearchTabBean;
import com.wxjz.module_base.db.bean.SearchTabContentBean;
import com.wxjz.module_base.db.dao.HistoryDBDao;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.activity.SearchActivity;
import com.wxjz.tenms_pad.mvp.contract.SearchContract;

import java.util.List;

/**
 * Created by a on 2019/9/10.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private final MainPageApi mainPageApi;
    private SearchActivity activity;

    public SearchPresenter(SearchActivity activity) {
        mainPageApi = create(MainPageApi.class);
        this.activity = activity;
    }

    @Override
    public void getSearchHistory() {
        List<HistoryBean> historyDatas = HistoryDBDao.getInstence().querySearchHistory();
        getView().onSearchHistory(historyDatas);
    }

    @Override
    public void getRecommendCourse(int levelId, String gradeId,int page) {
        makeRequest(mainPageApi.getRecommend(levelId, gradeId,page), new BaseObserver<RecommendBean>() {
            @Override
            protected void onSuccess(RecommendBean recommendBean) {
                getView().onRecommendCourse(recommendBean);
            }
        });
    }


    @Override
    public void queryAllSearchData(String searchContent, String gradeId, int levelId) {
        makeRequest(mainPageApi.getSearchTabContent(searchContent, gradeId, levelId), new BaseObserver<SearchTabContentBean>(activity) {
            @Override
            protected void onSuccess(SearchTabContentBean searchTabContentBeans) {
                getView().onAllSearchData(searchTabContentBeans);
            }
        });
    }

    @Override
    public void addCourseClickCount(int courseId) {
        makeRequest(mainPageApi.addCourseClickCount(courseId), new BaseObserver<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean loginBean) {

            }
        });
    }
}
