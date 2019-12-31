package com.wxjz.module_aliyun.mvp.contract;

import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadManager;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadMediaInfo;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.bean.VideoInPlayPageBean;
import com.wxjz.module_base.mvp.IBaseView;

import java.util.List;

/**
 * @ClassName LandscapeVideoActivityContract
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-25 17:21
 * @Version 1.0
 */
public interface LandscapeVideoActivityContract {

    /**
     * domType
     * 0 提示
     * 1 术语
     * 2 题目
     * 3 笔记
     */

    interface Presenter {
        //笔记信息查询
        void getSelectDomNote(String userId, int domType, int videoId);

        //删除笔记操作
        void deleteDomInfoPad(String id, int domType);

        //添加一条笔记的操作
        void insertDomNote(String userId, int videoId, int domType, int videoDom, String domContent);

        //更新一条笔记
        void updateDomNote(int id, String domContent);

        //查询所有提示
        void getTipsList(int videoId, int domType, String userId);

        //查询所有术语
        void getTerminologyList(int videoId, int domType, String userId);

        void refreshDownloadVidAuth(String videoId, AliyunDownloadMediaInfo info);

        //更新学习时间
        void updateLearnTime(int vid, int progress, int todayTimeRealRecord);

        //新增学习记录
        void insertLearnTime(int videoId, int subId);

        //查询所有问题
        void getExerciseList(int videoId, int domType, String userId);

        //回答问题
        void getInsertUserAnswer(String userId, int domId, String userAnswer);

        //请求所有的点位数据
        void getAllPoints(String userId, int domType, int videoId);

        void getVideoInCourse(AliyunDownloadManager downloadManager, Integer chapterId, Integer sectionId, String gradeId, final int page, int rows);

        void loadMoreVideoInCourse(AliyunDownloadManager downloadManager, Integer chapterId, Integer sectionId, String gradeId, final int page, int rows);

        void getPlayAuth(String vid);

        void getUserInfo();

        //根据年级进行比较
        void getLeveListNoLevelID(boolean isMember);

    }

    interface View extends IBaseView {
        void onSelectDomNote(PointListBean takeNotesListBean);

        void onDeleteDomInfoPad();

        void onInsertDomNote();

        void onUpdateDomNote();

        void onTipsListBean(PointListBean listBean);

        void onTerminologyList(PointListBean listBean);

        void onRefreshDownloadVidAuth(PlayAuthBean playAuthBean, AliyunDownloadMediaInfo info);

        void onExerciseList(PointListBean exerciseListBean);

        void onInsertUserAnswer();

        void onAllPoints(PointListBean pointBean);

        void onCourseInVideo(List<VideoInPlayPageBean.VideoListBean> mNewVideolist, String sectionName);

        void onLoadMoreCourseInVideo(List<VideoInPlayPageBean.VideoListBean> mNewVideolist, String sectionName);

        void onPlayAuth(String playAuth);

        void onInsertLearnTime();

        void onUserInfo(UserInfoBean userInfoBean);

        void onLevelListByNoLevelID(List<LevelListBean> levelListBeans);
    }
}
