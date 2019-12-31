package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.LearnRecordBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public interface LearnRecordContract {
    interface Presenter {
        void getLearnRecord( int levelId);
        void addCourseClickCount(int courseId);
        void deleteLearnRecord(String courseIds);

    }

    interface View extends IBaseView {
        void onLearnRecord(LearnRecordBean recordBeanList);
        void onDeleteLearnRecord();
    }
}
