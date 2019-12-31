package com.wxjz.tenms_pad.mvp.contract;

import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.wxjz.module_base.bean.BrannerBean;
import com.wxjz.module_base.bean.FreeVideoListBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.module_base.bean.RecommendBean;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/5.
 */

public interface HomePageContract {
    interface Presenter {
        void getBannerData(String levelId,String gradeId);

        void getPopularCourse(int levelId, String gradeId,int page, int rows);

        void getRecommendCourse(int livelId, String gradeId,int page);

        void getFreeCourse(int levelId,String gradeId ,int page, int rows);
        void addCourseClickCount(int courseId);
    }

    interface View extends IBaseView {
        void onBanner(List<BrannerBean.BannerListBean> bannerList);

        void onPopularCourse(PopularMutiItem popularMutiItems);

        void onRecommendCourse(RecommendBean recommendCourseList, int page);

        void onFreeCourse(FreeVideoListBean freeVideoListBean);


    }
}
