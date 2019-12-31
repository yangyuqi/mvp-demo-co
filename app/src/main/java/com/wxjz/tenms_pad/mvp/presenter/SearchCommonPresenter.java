package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.SearchBean;
import com.wxjz.module_base.db.bean.SearchTabContentBean;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.mvp.contract.SearchCommonContract;

import java.util.List;

/**
 * Created by a on 2019/9/5.
 */

public class SearchCommonPresenter extends BasePresenter<SearchCommonContract.View> implements SearchCommonContract.Presenter {

    private final MainPageApi mainPageApi;

    public SearchCommonPresenter() {
        mainPageApi = create(MainPageApi.class);
    }

    @Override
    public void getSearchData(String content, int levelId, int subId, String gradeId) {
        makeRequest(mainPageApi.getVideoListBySubId(content, levelId, subId, gradeId), new BaseObserver<List<SearchBean>>() {
            @Override
            protected void onSuccess(List<SearchBean> searchBeans) {
                getView().onSearchData(searchBeans);
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
