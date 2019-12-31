package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.LearnRecordBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.http.api.MinePageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.fragment.mine.LearnRecordFragment;
import com.wxjz.tenms_pad.mvp.contract.DownloadManageContract;
import com.wxjz.tenms_pad.mvp.contract.LearnRecordContract;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public class LearnRecordPresenter extends BasePresenter<LearnRecordContract.View> implements LearnRecordContract.Presenter {
    private LearnRecordFragment fragment;
    private final MinePageApi minePageApi;

    public LearnRecordPresenter(LearnRecordFragment fragment) {
        this.fragment = fragment;
        minePageApi = create(MinePageApi.class);
    }

    @Override
    public void getLearnRecord(int levelId) {
        makeRequest(minePageApi.getLearnRecord(levelId), new BaseObserver<LearnRecordBean>(fragment) {
            @Override
            protected void onSuccess(LearnRecordBean recordBeanList) {
                getView().onLearnRecord(recordBeanList);
            }
        });
    }

    @Override
    public void addCourseClickCount(int courseId) {
        makeRequest(minePageApi.addCourseClickCount(courseId), new BaseObserver<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean loginBean) {

            }
        });
    }

    @Override
    public void deleteLearnRecord(String ids) {
        makeRequest(minePageApi.deleteLearnRecord(ids), new BaseObserver<LoginBean>() {
            @Override
            protected void onSuccess(LoginBean loginBean) {
                getView().onDeleteLearnRecord();
            }
        });
    }
}
