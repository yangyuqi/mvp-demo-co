package com.wxjz.tenms_pad.fragment.mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.source.VidAuth;
import com.wxjz.module_aliyun.aliyun.utils.VidAuthUtil;
import com.wxjz.module_aliyun.aliyun.utils.database.DatabaseManager;
import com.wxjz.module_aliyun.aliyun.utils.database.LoadDbDatasListener;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadInfoListener;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadManager;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadMediaInfo;
import com.wxjz.module_aliyun.aliyun.view.download.AlivcDownloadMediaInfo;
import com.wxjz.module_aliyun.aliyun.view.download.DownloadDataProvider;
import com.wxjz.module_aliyun.aliyun.view.download.DownloadView;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.util.FileUtil;
import com.wxjz.module_base.util.LogUtils;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.MineActivity;
import com.wxjz.tenms_pad.adapter.CourseDownloadProgressAdapter;
import com.wxjz.tenms_pad.bean.AliyunMediaInfo;
import com.wxjz.tenms_pad.mvp.contract.DownloadManageContract;
import com.wxjz.tenms_pad.mvp.presenter.DownloadManagePresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a on 2019/9/30.
 */

public class DownloadManageFragment2 extends BaseMvpFragment<DownloadManagePresenter> implements DownloadManageContract.View, View.OnClickListener {

    private RecyclerView rv_course;
    private DownloadView downloadView;

    private DownloadDataProvider downloadDataProvider;
    private AliyunDownloadManager downloadManager;
    private MyDownloadInfoListener mDownloadInfoListener;
    private boolean mDownloadInPrepare;
    private static final int DOWNLOAD_ERROR = 1;
    private static final String DOWNLOAD_ERROR_KEY = "error_key";
    private PlayerHandler playerHandler;
    private ProgressBar progress_space;
    private TextView tv_space;
    private CourseDownloadProgressAdapter mDownloadProgressAdapter;
    private List<AliyunMediaInfo> mediaInfoList;
    private TextView tvChooseAll;

    private TextView tvDelete;
    private TextView tvManage;
    private LinearLayout llChoose;
    private RelativeLayout rlEmpty;
    private RelativeLayout rlContent;

    /**
     * 删除完视频重新渲染页面
     */
    public void deleteVideoFinish() {
        if (notFast()) {
            showLoading();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getDownloadCourseData();
                    llChoose.setVisibility(View.GONE);
                    tvManage.setVisibility(View.VISIBLE);
                    hideLoading();
                }
            }, 2000);
        }


    }

    private long lastClickTime;

    private boolean notFast() {

        if (System.currentTimeMillis() - lastClickTime <= 1000) {
            return false;

        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }

    private static class PlayerHandler extends Handler {
        //持有弱引用AliyunPlayerSkinActivity,GC回收时会被回收掉.
        private final WeakReference<MineActivity> mActivty;

        public PlayerHandler(MineActivity activity) {
            mActivty = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MineActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case DOWNLOAD_ERROR:
                        ToastUtil.show(activity, msg.getData().getString(DOWNLOAD_ERROR_KEY));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected DownloadManagePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {
        DatabaseManager.getInstance().createDataBase(mContext);
        rv_course = view.findViewById(R.id.rv_course);
        progress_space = view.findViewById(R.id.progress_space);
        tv_space = view.findViewById(R.id.tv_space);
        tvChooseAll = view.findViewById(R.id.tv_choose_all);

        tvDelete = view.findViewById(R.id.tv_delete);
        tvManage = view.findViewById(R.id.tv_manage);
        llChoose = view.findViewById(R.id.llChoose);
        rlEmpty = view.findViewById(R.id.rl_empty);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        rlContent = view.findViewById(R.id.rlContent);
        tvManage.setOnClickListener(this);
        tvChooseAll.setOnClickListener(this);

        tvDelete.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        initListener();

    }

    private void initListener() {
        downloadManager = AliyunDownloadManager.getInstance(mContext.getApplicationContext());
        downloadDataProvider = DownloadDataProvider.getSingleton(mContext.getApplicationContext());
        // 更新sts回调
        //  downloadManager.setRefreshStsCallback(new MyRefreshStsCallback());
        // 视频下载的回调
        mDownloadInfoListener = new MyDownloadInfoListener((MineActivity) mContext);
        downloadManager.setDownloadInfoListener(mDownloadInfoListener);

    }

    @Override
    protected void initData() {
        getDownloadCourseData();
        getPhoneSpace();

    }

    private void getPhoneSpace() {
        long sdcardAvailableSize = FileUtil.getSdcardAvailableSize();
        long sdcardTotalSize = FileUtil.getSdcardTotalSize();
        int progress = (int) ((1 - ((double) sdcardAvailableSize / sdcardTotalSize)) * 100);
        progress_space.setProgress(progress);
        tv_space.setText("总空间" + FileUtil.getFormatSize(sdcardTotalSize) + "/剩余" + FileUtil.getFormatSize(sdcardAvailableSize));
    }

    private void getDownloadCourseData() {
        downloadDataProvider.restoreMediaInfo(new LoadDbDatasListener() {
            @Override
            public void onLoadSuccess(List<AliyunDownloadMediaInfo> dataList) {
                //todo 按SectionId排序
                Map<Integer, List<AliyunDownloadMediaInfo>> map = new HashMap<>();
                for (AliyunDownloadMediaInfo mediaInfo : dataList) {
                    int key = mediaInfo.getSectionId();
                    if (map.containsKey(key)) {
                        List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfos = map.get(key);
                        //去重复
                        if (aliyunDownloadMediaInfos.contains(mediaInfo)) {
                            aliyunDownloadMediaInfos.remove(mediaInfo);
                        }
                        aliyunDownloadMediaInfos.add(mediaInfo);
                    } else {
                        List<AliyunDownloadMediaInfo> list = new ArrayList<>();
                        list.add(mediaInfo);
                        map.put(key, list);
                    }
                }
                mediaInfoList = new ArrayList<>();
                for (int key : map.keySet()) {
                    List<AliyunDownloadMediaInfo> list = map.get(key);
                    AliyunMediaInfo aliyunMediaInfo = new AliyunMediaInfo();
                    aliyunMediaInfo.setSectionName(list.get(0).getSectionName());

                    List<AliyunMediaInfo.DownloadVideoMidiainfo> downloadVideos = new ArrayList<>();
                    for (AliyunDownloadMediaInfo mediaInfo : list) {
                        AliyunMediaInfo.DownloadVideoMidiainfo downloadVideo = new AliyunMediaInfo.DownloadVideoMidiainfo();
                        downloadVideo.setErrorCode(mediaInfo.getErrorCode());
                        downloadVideo.setErrorMsg(mediaInfo.getErrorMsg());
                        downloadVideo.setIsEncripted(mediaInfo.isEncripted());
                        downloadVideo.setVideoCover(mediaInfo.getVideoCover());
                        downloadVideo.setmDownloadIndex(mediaInfo.getDownloadIndex());
                        downloadVideo.setmDuration(mediaInfo.getDuration());
                        downloadVideo.setmFileHandleProgress(mediaInfo.getmFileHandleProgress());
                        downloadVideo.setmFormat(mediaInfo.getFormat());
                        downloadVideo.setmProgress(mediaInfo.getProgress());
                        downloadVideo.setmQuality(mediaInfo.getQuality());
                        downloadVideo.setmQualityIndex(mediaInfo.getQualityIndex());
                        downloadVideo.setmSavePath(mediaInfo.getSavePath());
                        downloadVideo.setmSize(mediaInfo.getSize());
                        downloadVideo.setmStatus(mediaInfo.getStatus());
                        downloadVideo.setmTitle(mediaInfo.getTitle());
                        downloadVideo.setmTrackInfo(mediaInfo.getTrackInfo());
                        downloadVideo.setmVid(mediaInfo.getVid());
                        downloadVideo.setmVidAuth(mediaInfo.getmVidAuth());
                        downloadVideo.setVideoCover(mediaInfo.getVideoCover());
                        downloadVideo.setVideoId(mediaInfo.getVideoId());
                        downloadVideos.add(downloadVideo);

                    }
                    aliyunMediaInfo.setDownloadVideoMidiainfos(downloadVideos);
                    mediaInfoList.add(aliyunMediaInfo);

                    // LogUtils.e("qlei", "mediaInfoList:\n" + mediaInfoList.toString());

                }
                setDownloadCourseView();
            }
        });

    }

    private void setDownloadCourseView() {
        if (mediaInfoList.size() == 0) {
            rlEmpty.setVisibility(View.VISIBLE);
            rlContent.setVisibility(View.GONE);
        } else {
            rlEmpty.setVisibility(View.GONE);
            rlContent.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rv_course.setLayoutManager(linearLayoutManager);
        mDownloadProgressAdapter = new CourseDownloadProgressAdapter(R.layout.layout_kemu_download_item, mediaInfoList, downloadManager, this);
        rv_course.setAdapter(mDownloadProgressAdapter);
    }

    private boolean isStop = false;

    @Override
    protected int getLayoutId() {
        return R.layout.down_load_manage_fragment2;
    }

    public static DownloadManageFragment2 getInstance() {
        return new DownloadManageFragment2();
    }

    /**
     * 下载监听
     */
    private class MyDownloadInfoListener implements AliyunDownloadInfoListener {

        private WeakReference<MineActivity> weakReference;

        public MyDownloadInfoListener(MineActivity aliyunPlayerSkinActivity) {
            weakReference = new WeakReference<>(aliyunPlayerSkinActivity);
        }


        @Override
        public void onPrepared(List<AliyunDownloadMediaInfo> infos) {

        }

        @Override
        public void onAdd(AliyunDownloadMediaInfo info) {
//            DownloadActivity aliyunPlayerSkinActivity = weakReference.get();
//            if (aliyunPlayerSkinActivity != null) {
//                if (aliyunPlayerSkinActivity.downloadDataProvider != null) {
//                    aliyunPlayerSkinActivity.downloadDataProvider.addDownloadMediaInfo(info);
//                }
//            }
        }

        @Override
        public void onStart(AliyunDownloadMediaInfo info) {
            Log.e("========", "onStart");
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                isStop = false;
//                if (downloadView != null) {
//                    downloadView.updateInfo(info);
//                }
                // updateInfoStatusAndProgress(info);
            }
        }

        @Override
        public void onProgress(AliyunDownloadMediaInfo info, int percent) {
            Log.e("========", "onProgress");
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                if (!isStop) {
                    updateInfoStatusAndProgress(info);
                }
//                if (downloadView != null) {
//                    downloadView.updateInfo(info);
//                }
            }
        }

        @Override
        public void onStop(AliyunDownloadMediaInfo info) {
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                downloadManager.stopDownload(info);
//                if (downloadView != null) {
//                    downloadView.updateInfo(info);
//                }
                isStop = true;
                updateInfoStatusAndProgress(info);


            }
        }

        @Override
        public void onCompletion(AliyunDownloadMediaInfo info) {
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                synchronized (aliyunPlayerSkinActivity) {

                    updateInfoStatusAndProgress(info);

                    if (downloadDataProvider != null) {
                        downloadDataProvider.addDownloadMediaInfo(info);
                    }
                }
            }
        }

        @Override
        public void onError(AliyunDownloadMediaInfo info, ErrorCode code, String msg, String requestId) {
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                mDownloadInPrepare = false;
                if (downloadView != null) {
                    downloadView.updateInfoByError(info);
                }
                updateInfoStatusAndProgress(info);

                //鉴权过期
                if (code.getValue() == ErrorCode.ERROR_SERVER_POP_UNKNOWN.getValue()) {
                    refreshDownloadVidAuth(info);
                }
                if (code.getValue() == ErrorCode.ERROR_SERVER_VOD_INVALIDAUTHINFO_EXPIRETIME.getValue()) {
                    refreshDownloadVidAuth(info);
                }
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(DOWNLOAD_ERROR_KEY, msg);
                message.setData(bundle);
                message.what = DOWNLOAD_ERROR;
                playerHandler = new PlayerHandler(aliyunPlayerSkinActivity);
                playerHandler.sendMessage(message);
            }
        }

        @Override
        public void onWait(AliyunDownloadMediaInfo info) {
            //   mPlayerDownloadAdapter.updateData(info);
        }

        @Override
        public void onDelete(AliyunDownloadMediaInfo info) {
            //deleteVideoFinish();
        }

        @Override
        public void onDeleteAll() {
//            mPlayerDownloadAdapter.clearAll();
        }

        @Override
        public void onFileProgress(AliyunDownloadMediaInfo info) {

        }
    }

    private void updateInfoStatusAndProgress(AliyunDownloadMediaInfo info) {
        if (mDownloadProgressAdapter != null) {

            for (AliyunMediaInfo mediaInfo : mediaInfoList) {
                List<AliyunMediaInfo.DownloadVideoMidiainfo> downloadVideoMidiainfos = mediaInfo.getDownloadVideoMidiainfos();
                for (AliyunMediaInfo.DownloadVideoMidiainfo downloadVideoMidiainfo : downloadVideoMidiainfos) {
                    if (downloadVideoMidiainfo.getmVid().equals(info.getVid())) {
                        int videoIndex = downloadVideoMidiainfos.indexOf(downloadVideoMidiainfo);
                        if (info.getStatus() == AliyunDownloadMediaInfo.Status.Stop && info.getProgress() != 100) {
                        } else {
                            downloadVideoMidiainfo.setmProgress(info.getProgress());

                        }
                        downloadVideoMidiainfo.setmStatus(info.getStatus());
                        int courseIndexOf = mediaInfoList.indexOf(mediaInfo);
                        // Log.e("=====",downloadVideoMidiainfo.getmTitle()+"刷新位置："+courseIndexOf+"视频位置："+videoIndex+"当前进度"+downloadVideoMidiainfo.getmProgress());
                        mDownloadProgressAdapter.notifyItemChangedVideo(courseIndexOf, videoIndex);

                        break;
                    }
                }
            }
        }
    }

    /**
     * 刷新下载的Vidauth
     */
    private void refreshDownloadVidAuth(final AliyunDownloadMediaInfo downloadMediaInfo) {
        VidAuthUtil.getVidAuth(downloadMediaInfo.getVid(), new VidAuthUtil.OnAuthResultListener() {
            @Override
            public void onSuccess(String vid, String auth) {
                if (downloadManager != null) {
                    VidAuth vidAuth = new VidAuth();
                    vidAuth.setVid(vid);
                    vidAuth.setPlayAuth(auth);
                    downloadMediaInfo.setmVidAuth(vidAuth);
                    downloadManager.prepareDownloadByQuality(downloadMediaInfo, AliyunDownloadManager.INTENT_STATE_START);
                }
            }

            @Override
            public void onFail() {

            }
        });

    }

    /**
     * checkbox选中状态
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_all:
                mDownloadProgressAdapter.checkAllItem(true);

                break;


            case R.id.tv_cancel:
                setManageVisible(true);
                break;
            case R.id.tv_delete:

                mDownloadProgressAdapter.deleteCheckedVideo(true);
                break;
            case R.id.tv_manage:
                setManageVisible(false);
                break;

        }
    }

    /**
     * 设置删除按钮样式
     *
     * @param clickable
     */
    public void setDeteleClickable(boolean clickable) {
//        tvDelete.setClickable(clickable);
//        tvDelete.setTextColor(clickable ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray909399));
    }

    /**
     * 设置管理按钮h和checkbox的显示和隐藏
     *
     * @param visible
     */
    private void setManageVisible(boolean visible) {
        llChoose.setVisibility(visible ? View.GONE : View.VISIBLE);
        tvManage.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (mDownloadProgressAdapter != null) {
            mDownloadProgressAdapter.setCheckboxVisible(!visible);
        }
    }


}
