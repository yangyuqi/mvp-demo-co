package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.ClassRankBean;
import com.wxjz.module_base.bean.GradeRankBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public interface LearnDurationContract {
    interface Presenter {
        void getClassRank(String data);
        void getGradeRank(String data);

    }
    interface View extends IBaseView{
        void onClassRank(ClassRankBean classRankBeans,String date);
        void onGradeRank(List<GradeRankBean> gradeRankBeans ,String date);
    }
}
