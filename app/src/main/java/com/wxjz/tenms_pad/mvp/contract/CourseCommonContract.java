package com.wxjz.tenms_pad.mvp.contract;


import com.wxjz.module_base.bean.CourseListItemBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.SelectVideoBean;
import com.wxjz.module_base.db.bean.SubjectChapterBean;
import com.wxjz.module_base.db.bean.SubjectHomeBean;
import com.wxjz.module_base.db.bean.SubjectSectionBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/5.
 */

public interface CourseCommonContract {
    interface Presenter {
        void getAllCourseList(int subId, int levelId);

        void getSelectVideoList(int courseId, int page, int rows);

        void loadMoreVideoList(int courseId, int page, int rows);

        void addCourseClickCount(int courseId);

        void getPlayAuth(String videoId, int id);

        void addVideoClickCount(int vid, int courseId);

        void getSubjectHome(int levelId, int subId, String gradeId);

        void getChapterVideoList(int chapterId);

        void getSectionIdVideoList(int sectionId);
    }

    interface View extends IBaseView {
        void onAllCourseList(List<CourseListItemBean> courseListItemBeans);

        void onSelectVideoList(SelectVideoBean selectVideoBeans);

        void onLoadMoreVideoList(SelectVideoBean selectVideoBeans);

        void onLoadMoreComplete();

        void onPlayAuth(PlayAuthBean playAuthBean, String videoId, int id);

        void onSubjectHome(SubjectHomeBean subjectHomeBeans);

        void onChapterVideoList(List<SubjectChapterBean> subjectChapterBeans);

        void onSectionIdVideoList(List<SubjectSectionBean> subjectSectionBeans);
    }
}
