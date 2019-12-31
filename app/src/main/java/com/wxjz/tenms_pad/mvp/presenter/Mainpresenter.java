package com.wxjz.tenms_pad.mvp.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonArray;
import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.bean.LoginBean;
import com.wxjz.module_base.bean.SchoollistBean;
import com.wxjz.module_base.bean.TopTabBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.SchoolItemBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.OwnStudyDBDao;
import com.wxjz.module_base.http.RetrofitClient;
import com.wxjz.module_base.http.api.MainPageApi;
import com.wxjz.module_base.mvp.BasePresenter;
import com.wxjz.module_base.util.HttpUtil;
import com.wxjz.module_base.util.SPCacheUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.fragment.CourseCommonFragment;
import com.wxjz.tenms_pad.fragment.CourseHomePageFragment;
import com.wxjz.tenms_pad.mvp.contract.MainContract;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by a on 2019/9/5.
 */

public class Mainpresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {


    private Activity mContext;
    private final MainPageApi homePageApi;

    public Mainpresenter(Activity context) {
        this.mContext = context;
        homePageApi = create(MainPageApi.class);
    }

    @Override
    public void getTopTabs(int stuLevelId) {
        if (stuLevelId == -1) {
            stuLevelId = 1;
        }
        final List<CourseItemPage> mPages = new ArrayList<>();
        CourseHomePageFragment instance = CourseHomePageFragment.getInstance(stuLevelId);
        final CourseItemPage courseItemPage = new CourseItemPage();
        courseItemPage.setFragment(instance);
        courseItemPage.setTitle(mContext.getResources().getString(R.string.recommend));
        courseItemPage.setId(0);
        mPages.add(courseItemPage);
        makeRequest(homePageApi.getTopTabs(stuLevelId), new BaseObserver<List<TopTabBean>>() {
            @Override
            protected void onSuccess(List<TopTabBean> topTabBeans) {
                for (TopTabBean dataBean : topTabBeans) {
                    CourseItemPage courseItemPage = new CourseItemPage();
                    CourseCommonFragment instance = CourseCommonFragment.getInstance(dataBean.getId());
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
    public void getClassifyData() {
        //从缓存中去取学段Id,Id为0说明没有学段
        String gradeId = CheckGradeDao.getInstance().queryGradeId();
        if (TextUtils.isEmpty(gradeId)) {
            CheckGradeDao.getInstance().addGuestChooseGrade(1, "小学", "P1", "一年级", false);
        }
        int levelId = CheckGradeDao.getInstance().queryleveId();
//        int id = ClassifyDao.getInstence().queryCurrentCheckId();
        getView().onClassifyData(levelId);
    }


    @Override
    public void getUserInfo() {
        makeRequest(homePageApi.getUserInfo(), new BaseObserver<UserInfoBean>() {
            @Override
            protected void onSuccess(UserInfoBean userInfoBean) {
                getView().onUserInfo(userInfoBean);
            }
        });
    }


    /**
     * 不是会员，而且查不到学段
     *
     * @param isMember
     */
    @Override
    public void getLeveListNoLevelID(boolean isMember) {
        makeRequest(homePageApi.getLevelListNoMember(isMember), new BaseObserver<List<LevelListBean>>() {
            @Override
            protected void onSuccess(List<LevelListBean> levelListBeans) {
                getView().onLevelListByNoLevelID(levelListBeans);
            }
        });
    }

}
