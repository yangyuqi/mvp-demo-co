package com.wxjz.tenms_pad.mvp.contract;

import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.mvp.IBaseView;

/**
 * Created by a on 2019/9/20.
 */

public interface CatalogueFragmentContract {
    interface Presenter {
        void getVideoList(int courseId, int page, int rows);

        void addVideoClickCount(int videoId,int courseId);

        void getPlayAuthByVid(String vid);

        void getLoadMoreVideoList(int courseId,int page,int rows);
    }

    interface View extends IBaseView {
        void onVideoList(CourseOutlineBean courseOutlineBean);
        void onPlayAuth(PlayAuthBean playAuthBean);
        void onLoadMoreVideoList(CourseOutlineBean courseOutlineBean);
    }
}
