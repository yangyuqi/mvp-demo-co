package com.wxjz.tenms_pad.mvp.presenter;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.TopTabBean;
import com.wxjz.module_base.http.api.MinePageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.tenms_pad.fragment.CourseCommonFragment;
import com.wxjz.tenms_pad.fragment.mine.FragmentInMyNote;
import com.wxjz.tenms_pad.fragment.mine.MyNoteFragment;
import com.wxjz.tenms_pad.mvp.contract.DownloadManageContract;
import com.wxjz.tenms_pad.mvp.contract.MyNoteContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public class MyNotePresenter extends BasePresenter<MyNoteContract.View> implements MyNoteContract.Presenter {

    private final MinePageApi minePageApi;
    private MyNoteFragment fragment;

    public MyNotePresenter(MyNoteFragment fragment) {
        this.fragment = fragment;
        minePageApi = create(MinePageApi.class);
    }

    @Override
    public void getTopTabs(int levelId) {
        if (levelId == -1) {
            levelId = 1;
        }
        makeRequest(minePageApi.getTopTabs(levelId), new BaseObserver<List<TopTabBean>>() {
            @Override
            protected void onSuccess(List<TopTabBean> topTabBeans) {
                final List<CourseItemPage> mPages = new ArrayList<>();
                CourseItemPage courseItemPageAll = new CourseItemPage();
                FragmentInMyNote instanceAll = FragmentInMyNote.getInstance(0,fragment);
                courseItemPageAll.setFragment(instanceAll);
                courseItemPageAll.setTitle("全部");
                courseItemPageAll.setId(0);
                mPages.add(courseItemPageAll);

                for (TopTabBean dataBean : topTabBeans) {
                    CourseItemPage courseItemPage = new CourseItemPage();
                    FragmentInMyNote instance = FragmentInMyNote.getInstance(dataBean.getId(),fragment);
                    courseItemPage.setFragment(instance);
                    courseItemPage.setTitle(dataBean.getSubjectName());
                    courseItemPage.setId(dataBean.getId());
                    mPages.add(courseItemPage);

                }
                getView().onTopTabData(mPages);

            }
        });
    }
}
