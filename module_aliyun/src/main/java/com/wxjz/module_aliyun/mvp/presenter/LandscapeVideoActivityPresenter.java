package com.wxjz.module_aliyun.mvp.presenter;


import android.app.DownloadManager;
import android.text.TextUtils;
import android.util.Log;

import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.utils.database.LoadDbDatasListener;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadManager;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadMediaInfo;
import com.wxjz.module_aliyun.mvp.contract.LandscapeVideoActivityContract;
import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.AddNoteItemBean;
import com.wxjz.module_base.bean.AnswerQuestionBean;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.DeleteNoteItemBean;
import com.wxjz.module_base.bean.ExerciseListBean;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.bean.VideoInPlayPageBean;
import com.wxjz.module_base.mvp.BasePresenter;


import com.wxjz.module_base.bean.UpdateNoteItemBean;
import com.wxjz.module_base.http.api.AliyunVideoApi;
import com.wxjz.module_base.util.LogUtils;
import com.wxjz.module_base.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LandscapeVideoActivityPresenter
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-25 17:26
 * @Version 1.0
 */
public class LandscapeVideoActivityPresenter extends BasePresenter<LandscapeVideoActivityContract.View> implements LandscapeVideoActivityContract.Presenter {
    /**
     * domType
     * 0 提示
     * 1 术语
     * 2 题目
     * 3 笔记
     */
    private AliyunVideoApi mAliyunVideoApi;
    private LandscapeVideoActivity mActivity;

    public LandscapeVideoActivityPresenter(LandscapeVideoActivity mActivity) {
        mAliyunVideoApi = create(AliyunVideoApi.class);
        this.mActivity = mActivity;
    }

    @Override
    public void getSelectDomNote(String userId, int domType, int videoId) {

        makeRequest(mAliyunVideoApi.getSelectDomNote(userId, domType, videoId), new BaseObserver<PointListBean>(mActivity) {
            @Override
            protected void onSuccess(PointListBean takeNotesListBean) {
                getView().onSelectDomNote(takeNotesListBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void deleteDomInfoPad(String id, int domType) {
        makeRequest(mAliyunVideoApi.deleteDomInfoPad(id, domType), new BaseObserver<DeleteNoteItemBean>(mActivity) {
            @Override
            protected void onSuccess(DeleteNoteItemBean deleteNoteItemBean) {
                getView().onDeleteDomInfoPad();
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showTextToas(mActivity, "删除失败，请稍后再试");
                super.onError(e);
            }
        });
    }

    @Override
    public void insertDomNote(String userId, int videoId, int domType, int videoDom, String domContent) {
        makeRequest(mAliyunVideoApi.insertDomNote(userId, videoId, domType, videoDom, domContent), new BaseObserver<AddNoteItemBean>(mActivity) {
            @Override
            protected void onSuccess(AddNoteItemBean addNoteItemBean) {
                getView().onInsertDomNote();
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showTextToas(mActivity, "新增失败，请稍后再试");
                super.onError(e);
            }
        });
    }

    @Override
    public void updateDomNote(int id, String domContent) {
        makeRequest(mAliyunVideoApi.updateDomNote(id, domContent), new BaseObserver<UpdateNoteItemBean>(mActivity) {
            @Override
            protected void onSuccess(UpdateNoteItemBean updateNoteItemBean) {
                getView().onUpdateDomNote();
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showTextToas(mActivity, "更新失败，请稍后再试");
                super.onError(e);
            }
        });
    }

    @Override
    public void getTipsList(int videoId, int domType, String userId) {
        makeRequest(mAliyunVideoApi.getTipsList(videoId, domType, userId), new BaseObserver<PointListBean>() {
            @Override
            protected void onSuccess(PointListBean tipsListBean) {
                getView().onTipsListBean(tipsListBean);
            }
        });
    }

    @Override
    public void getTerminologyList(int videoId, int domType, String userId) {
        makeRequest(mAliyunVideoApi.getTerminologysList(videoId, domType, userId), new BaseObserver<PointListBean>() {
            @Override
            protected void onSuccess(PointListBean terminologyListBean) {
                getView().onTerminologyList(terminologyListBean);
            }
        });
    }

    @Override
    public void refreshDownloadVidAuth(String videoId, final AliyunDownloadMediaInfo info) {
        makeRequest(mAliyunVideoApi.getPlayAuthByVid(videoId), new BaseObserver<PlayAuthBean>() {
            @Override
            protected void onSuccess(PlayAuthBean playAuthBean) {
                getView().onRefreshDownloadVidAuth(playAuthBean, info);
            }
        });
    }

    @Override
    public void updateLearnTime(int vid, int progress, int todayTimeRealRecord) {
        makeRequest(mAliyunVideoApi.updateLearnTime(vid, progress, todayTimeRealRecord), new BaseObserver<UpdateNoteItemBean>() {
            @Override
            protected void onSuccess(UpdateNoteItemBean updateNoteItemBean) {
                getView().onInsertLearnTime();
            }
        });
    }

    @Override
    public void insertLearnTime(int videoId, int subId) {
        makeRequest(mAliyunVideoApi.insertLearnTime(videoId, subId), new BaseObserver<UpdateNoteItemBean>() {
            @Override
            protected void onSuccess(UpdateNoteItemBean updateNoteItemBean) {

            }
        });
    }


    @Override
    public void getExerciseList(int videoId, int domType, String userId) {
        makeRequest(mAliyunVideoApi.getExerciseList(videoId, domType, userId), new BaseObserver<PointListBean>() {
            @Override
            protected void onSuccess(PointListBean exerciseListBean) {
                getView().onExerciseList(exerciseListBean);
            }
        });
    }


    @Override
    public void getInsertUserAnswer(String userId, int domId, String userAnswer) {
        makeRequest(mAliyunVideoApi.getInsertUserAnswer(userId, domId, userAnswer), new BaseObserver<AnswerQuestionBean>() {
            @Override
            protected void onSuccess(AnswerQuestionBean answerQuestionBean) {
                getView().onInsertUserAnswer();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().onInsertUserAnswer();
            }
        });
    }

    @Override
    public void getAllPoints(String userId, int domType, int videoId) {
        makeRequest(mAliyunVideoApi.getAllPointList(userId, domType, videoId), new BaseObserver<PointListBean>() {
            @Override
            protected void onSuccess(PointListBean pointListBean) {
                getView().onAllPoints(pointListBean);
            }

            @Override
            public void onError(Throwable e) {
                getView().onAllPoints(null);
                super.onError(e);
            }
        });
    }

    @Override
    public void getVideoInCourse(final AliyunDownloadManager downloadManager, final Integer chapterId, final Integer sectionId, String gradeId, final int page, int rows) {
        makeRequest(mAliyunVideoApi.getVideoList(chapterId, sectionId, gradeId, page, rows), new BaseObserver<VideoInPlayPageBean>() {
            @Override
            protected void onSuccess(VideoInPlayPageBean videoInPlayPageBeans) {
                if (sectionId == null) {

                    getHasDownloadInfo(downloadManager, chapterId, videoInPlayPageBeans.getVideoList(), page, videoInPlayPageBeans.getTitle());
                } else {
                    getHasDownloadInfo(downloadManager, sectionId, videoInPlayPageBeans.getVideoList(), page, videoInPlayPageBeans.getTitle());
                }


            }
        });
    }

    @Override
    public void loadMoreVideoInCourse(final AliyunDownloadManager downloadManager, final Integer chapterId, final Integer sectionId, String gradeId, final int page, int rows) {
        makeRequest(mAliyunVideoApi.getVideoList(chapterId, sectionId, gradeId, page, rows),
                new BaseObserver<VideoInPlayPageBean>() {
                    @Override
                    protected void onSuccess(VideoInPlayPageBean videoInPlayPageBeans) {
                        if (sectionId == null) {
                            getHasDownloadInfo(downloadManager, chapterId, videoInPlayPageBeans.getVideoList(), page, videoInPlayPageBeans.getTitle());
                        } else {
                            getHasDownloadInfo(downloadManager, sectionId, videoInPlayPageBeans.getVideoList(), page, videoInPlayPageBeans.getTitle());
                        }

                    }
                });
    }

    @Override
    public void getPlayAuth(String vid) {
        makeRequest(mAliyunVideoApi.getPlayAuthByVid(vid), new BaseObserver<PlayAuthBean>() {
            @Override
            protected void onSuccess(PlayAuthBean playAuthBean) {
                getView().onPlayAuth(playAuthBean.getPlayAuth());
            }
        });
    }

    /**
     * 获取用户信息
     */
    @Override
    public void getUserInfo() {
        makeRequest(mAliyunVideoApi.getUserInfo(), new BaseObserver<UserInfoBean>() {
            @Override
            protected void onSuccess(UserInfoBean userInfoBean) {
                getView().onUserInfo(userInfoBean);
            }
        });
    }

    /**
     * 查询所有学段和年级的信息
     *
     * @param isMember
     */

    @Override
    public void getLeveListNoLevelID(boolean isMember) {
        makeRequest(mAliyunVideoApi.getLevelListNoMember(isMember), new BaseObserver<List<LevelListBean>>() {
            @Override
            protected void onSuccess(List<LevelListBean> levelListBeans) {
                getView().onLevelListByNoLevelID(levelListBeans);
            }
        });
    }

    private void getHasDownloadInfo(AliyunDownloadManager downloadManager, int sectionId, final List<VideoInPlayPageBean.VideoListBean> videoList, final int page, final String title) {
        downloadManager.findVideoInCourseByDb(sectionId, new LoadDbDatasListener() {
            @Override
            public void onLoadSuccess(List<AliyunDownloadMediaInfo> downloadDataList) {
                List<VideoInPlayPageBean.VideoListBean> mNewVideolist = new ArrayList<>();
                for (int i = 0; i < videoList.size(); i++) {
                    VideoInPlayPageBean.VideoListBean video = videoList.get(i);
                    for (int j = 0; j < downloadDataList.size(); j++) {
                        //视频列表里视频的vid
                        String vid = videoList.get(i).getVideoId();
                        //已下载视频的vid
                        AliyunDownloadMediaInfo downloadVideo = downloadDataList.get(j);
                        String downloadVid = downloadVideo.getVid();
                        if (vid.equals(downloadVid)) {
                            if (downloadVideo.getStatus() == AliyunDownloadMediaInfo.Status.Complete) {
                                video.setDownload_status(2);

                            } else if (downloadVideo.getStatus() == AliyunDownloadMediaInfo.Status.Start) {
                                video.setDownload_status(1);
                            } else {
                                video.setDownload_status(0);
                            }

                        }
                    }
                    mNewVideolist.add(video);
                }
                if (page > 1) {
                    getView().onLoadMoreCourseInVideo(mNewVideolist, title);
                } else {
                    getView().onCourseInVideo(mNewVideolist, title);

                }
            }
        });
    }

    /**
     * 获取用户信息
     */


}
