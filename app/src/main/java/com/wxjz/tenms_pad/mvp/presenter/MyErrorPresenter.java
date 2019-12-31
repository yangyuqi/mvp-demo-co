package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.FilterErrorExerciseBean;
import com.wxjz.module_base.bean.TopTabBean;
import com.wxjz.module_base.http.api.MinePageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.fragment.mine.FragmentInMyError;
import com.wxjz.tenms_pad.fragment.mine.MyErrorFragment;
import com.wxjz.tenms_pad.mvp.contract.MyErrorContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public class MyErrorPresenter extends BasePresenter<MyErrorContract.View> implements MyErrorContract.Presenter {
    private final MinePageApi minePageApi;
    private MyErrorFragment fragment;

    public MyErrorPresenter(MyErrorFragment fragment) {
        this.fragment = fragment;
        minePageApi = create(MinePageApi.class);
    }

    @Override
    public void getTopTabs(int levelId) {
        makeRequest(minePageApi.getTopTabs(levelId), new BaseObserver<List<TopTabBean>>() {
            @Override
            protected void onSuccess(List<TopTabBean> topTabBeans) {
                final List<CourseItemPage> mPages = new ArrayList<>();
                CourseItemPage courseItemPageAll = new CourseItemPage();
                FragmentInMyError instanceAll = FragmentInMyError.getInstance(0, fragment);
                courseItemPageAll.setFragment(instanceAll);
                courseItemPageAll.setTitle("全部");
                courseItemPageAll.setId(0);
                mPages.add(courseItemPageAll);

                for (TopTabBean dataBean : topTabBeans) {
                    CourseItemPage courseItemPage = new CourseItemPage();
                    FragmentInMyError instance = FragmentInMyError.getInstance(dataBean.getId(), fragment);
                    courseItemPage.setFragment(instance);
                    courseItemPage.setTitle(dataBean.getSubjectName());
                    courseItemPage.setId(dataBean.getId());
                    mPages.add(courseItemPage);

                }
                getView().onTopTabData(mPages);
            }
        });
    }

    @Override
    public void getFilte(Integer subjectId, String userId, int domType) {
        makeRequest(minePageApi.getFilterErrorBean(subjectId, userId, domType), new BaseObserver<List<FilterErrorExerciseBean>>() {
            @Override
            protected void onSuccess(List<FilterErrorExerciseBean> filterErrorExerciseBeans) {
                getView().onFilte(filterErrorExerciseBeans);
            }
        });
    }
}
