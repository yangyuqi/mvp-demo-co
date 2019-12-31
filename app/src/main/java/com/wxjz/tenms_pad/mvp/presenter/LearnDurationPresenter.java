package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.ClassRankBean;
import com.wxjz.module_base.bean.GradeRankBean;
import com.wxjz.module_base.http.api.MinePageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.fragment.mine.LearnDurationFragment;
import com.wxjz.tenms_pad.mvp.contract.LearnDurationContract;
import com.wxjz.tenms_pad.mvp.contract.MyErrorContract;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public class LearnDurationPresenter extends BasePresenter<LearnDurationContract.View> implements LearnDurationContract.Presenter {

    private final MinePageApi minePageApi;
    private LearnDurationFragment fragment;

    public LearnDurationPresenter(LearnDurationFragment fragment) {
        this.fragment = fragment;
        minePageApi = create(MinePageApi.class);
    }

    @Override
    public void getClassRank(final String data) {
        makeRequest(minePageApi.getClassRank(data), new BaseObserver<ClassRankBean>(fragment) {
            @Override
            protected void onSuccess(ClassRankBean classRankBeans) {
                getView().onClassRank(classRankBeans, data);
            }
        });
    }

    @Override
    public void getGradeRank(final String date) {
        makeRequest(minePageApi.getGradeRank(date), new BaseObserver<List<GradeRankBean>>(fragment) {
            @Override
            protected void onSuccess(List<GradeRankBean> gradeRankBeans) {
                getView().onGradeRank(gradeRankBeans,date);
            }
        });
    }
}
