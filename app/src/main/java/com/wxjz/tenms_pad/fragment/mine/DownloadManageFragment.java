package com.wxjz.tenms_pad.fragment.mine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.VcPlayerLog;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.constants.PlayParameter;
import com.wxjz.module_aliyun.aliyun.listener.MyNetConnectedListener;
import com.wxjz.module_aliyun.aliyun.listener.RefreshStsCallback;
import com.wxjz.module_aliyun.aliyun.utils.FixedToastUtils;
import com.wxjz.module_aliyun.aliyun.utils.NetWatchdog;
import com.wxjz.module_aliyun.aliyun.utils.VidStsUtil;
import com.wxjz.module_aliyun.aliyun.utils.database.DatabaseManager;
import com.wxjz.module_aliyun.aliyun.utils.database.LoadDbDatasListener;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadInfoListener;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadManager;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadMediaInfo;
import com.wxjz.module_aliyun.aliyun.view.download.AlivcDialog;
import com.wxjz.module_aliyun.aliyun.view.download.AlivcDownloadMediaInfo;
import com.wxjz.module_aliyun.aliyun.view.download.DownloadDataProvider;
import com.wxjz.module_aliyun.aliyun.view.download.DownloadView;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.module_base.util.SystemManager;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.MineActivity;
import com.wxjz.tenms_pad.mvp.contract.DownloadManageContract;
import com.wxjz.tenms_pad.mvp.presenter.DownloadManagePresenter;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 下载管理
 */
//todo  像数据库添加课程信息和课程名字 然后再排序
public class DownloadManageFragment extends BaseMvpFragment<DownloadManagePresenter> implements DownloadManageContract.View {
    private static String preparedVid;
    private static boolean mDownloadInPrepare;
    private PlayerHandler playerHandler;
    private DownloadView downloadView;
    private AliyunVodPlayerView mAliyunVodPlayerView = null;

    private DownloadDataProvider downloadDataProvider;
    private AliyunDownloadManager downloadManager;
    private MyDownloadInfoListener mDownloadInfoListener;

    public static DownloadManageFragment getInstance() {
        return new DownloadManageFragment();
    }

    @Override
    protected DownloadManagePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {
        DatabaseManager.getInstance().createDataBase(mContext);
        downloadView = (DownloadView) view.findViewById(R.id.download_view);
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
        downloadViewSetting(downloadView);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_downlod_manage;
    }

    /**
     * downloadView的配置 里面配置了需要下载的视频的信息, 事件监听等 抽取该方法的主要目的是, 横屏下download dialog的离线视频列表中也用到了downloadView, 而两者显示内容和数据是同步的,
     * 所以在此进行抽取 AliyunPlayerSkinActivity.class#showAddDownloadView(DownloadVie view)中使用
     */
    private void downloadViewSetting(final DownloadView downloadView) {
        downloadDataProvider.restoreMediaInfo(new LoadDbDatasListener() {
            @Override
            public void onLoadSuccess(List<AliyunDownloadMediaInfo> dataList) {
                if (downloadView != null) {
                    downloadView.addAllDownloadMediaInfo(dataList);
                }
            }
        });

        downloadView.setOnDownloadViewListener(new DownloadView.OnDownloadViewListener() {
            @Override
            public void onStop(AliyunDownloadMediaInfo downloadMediaInfo) {
                downloadManager.stopDownload(downloadMediaInfo);
            }

            @Override
            public void onStart(AliyunDownloadMediaInfo downloadMediaInfo) {
                downloadManager.startDownload(downloadMediaInfo);
            }

            @Override
            public void onDeleteDownloadInfo(final ArrayList<AlivcDownloadMediaInfo> alivcDownloadMediaInfos) {
                // 视频删除的dialog
                final AlivcDialog alivcDialog = new AlivcDialog(mContext);
                alivcDialog.setDialogIcon(R.drawable.icon_delete_tips);
                alivcDialog.setMessage(getResources().getString(R.string.alivc_delete_confirm));
                alivcDialog.setOnConfirmclickListener(getResources().getString(R.string.alivc_dialog_sure),
                        new AlivcDialog.onConfirmClickListener() {
                            @Override
                            public void onConfirm() {
                                alivcDialog.dismiss();
                                if (alivcDownloadMediaInfos != null && alivcDownloadMediaInfos.size() > 0) {
                                    downloadView.deleteDownloadInfo();

                                    if (downloadManager != null) {
                                        for (AlivcDownloadMediaInfo alivcDownloadMediaInfo : alivcDownloadMediaInfos) {
                                            downloadManager.deleteFile(alivcDownloadMediaInfo.getAliyunDownloadMediaInfo());
                                        }

                                    }
                                    downloadDataProvider.deleteAllDownloadInfo(alivcDownloadMediaInfos);
                                } else {
                                    toast("没有删除的视频选项...");
                                }
                            }
                        });
                alivcDialog.setOnCancelOnclickListener(getResources().getString(R.string.alivc_dialog_cancle),
                        new AlivcDialog.onCancelOnclickListener() {
                            @Override
                            public void onCancel() {
                                alivcDialog.dismiss();
                            }
                        });
                alivcDialog.show();
            }
        });

        downloadView.setOnDownloadedItemClickListener(new DownloadView.OnDownloadItemClickListener() {
            @Override
            public void onDownloadedItemClick(final int positin) {
                ArrayList<AlivcDownloadMediaInfo> allDownloadMediaInfo = downloadView.getAllDownloadMediaInfo();
                if (positin < 0) {
                    toast("视频资源不存在");
                    return;
                }

                // 如果点击列表中的视频, 需要将类型改为vid
                AliyunDownloadMediaInfo aliyunDownloadMediaInfo = allDownloadMediaInfo.get(positin).getAliyunDownloadMediaInfo();
                PlayParameter.PLAY_PARAM_TYPE = "localSource";
                if (aliyunDownloadMediaInfo != null) {
                    PlayParameter.PLAY_PARAM_URL = aliyunDownloadMediaInfo.getSavePath();
                    if (!TextUtils.isEmpty(aliyunDownloadMediaInfo.getVideoId())) {
                        int id = Integer.getInteger(aliyunDownloadMediaInfo.getVideoId());
                        JumpUtil.jump2VideoActivity(getActivity(), LandscapeVideoActivity.class, PlayParameter.PLAY_PARAM_URL, aliyunDownloadMediaInfo.getTitle(),id);
                    }

                }

            }

            @Override
            public void onDownloadingItemClick(ArrayList<AlivcDownloadMediaInfo> infos, int position) {
                AlivcDownloadMediaInfo alivcInfo = infos.get(position);
                AliyunDownloadMediaInfo aliyunDownloadInfo = alivcInfo.getAliyunDownloadMediaInfo();
                AliyunDownloadMediaInfo.Status status = aliyunDownloadInfo.getStatus();
                if (status == AliyunDownloadMediaInfo.Status.Error || status == AliyunDownloadMediaInfo.Status.Wait) {
                    //downloadManager.removeDownloadMedia(aliyunDownloadInfo);
                    downloadManager.startDownload(aliyunDownloadInfo);
                }
            }
        });
    }

    private static class MyRefreshStsCallback implements RefreshStsCallback {

        @Override
        public VidSts refreshSts(String vid, String quality, String format, String title, boolean encript) {
            VcPlayerLog.d("refreshSts ", "refreshSts , vid = " + vid);
            //NOTE: 注意：这个不能启动线程去请求。因为这个方法已经在线程中调用了。
            VidSts vidSts = VidStsUtil.getVidSts(vid);
            if (vidSts == null) {
                return null;
            } else {
                vidSts.setVid(vid);
                vidSts.setQuality(quality, true);
                vidSts.setTitle(title);
                return vidSts;
            }
        }
    }

    private static final int DOWNLOAD_ERROR = 1;
    private static final String DOWNLOAD_ERROR_KEY = "error_key";

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
            preparedVid = infos.get(0).getVid();
            Collections.sort(infos, new Comparator<AliyunDownloadMediaInfo>() {
                @Override
                public int compare(AliyunDownloadMediaInfo mediaInfo1, AliyunDownloadMediaInfo mediaInfo2) {
                    if (mediaInfo1.getSize() > mediaInfo2.getSize()) {
                        return 1;
                    }
                    if (mediaInfo1.getSize() < mediaInfo2.getSize()) {
                        return -1;
                    }

                    if (mediaInfo1.getSize() == mediaInfo2.getSize()) {
                        return 0;
                    }
                    return 0;
                }
            });
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                mDownloadInPrepare = false;
                //  aliyunPlayerSkinActivity.onDownloadPrepared(infos, aliyunPlayerSkinActivity.showAddDownloadView);
            }
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
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {

                if (downloadView != null) {
                    downloadView.updateInfo(info);
                }

            }
        }

        @Override
        public void onProgress(AliyunDownloadMediaInfo info, int percent) {
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {

                if (downloadView != null) {
                    downloadView.updateInfo(info);
                }
            }
        }

        @Override
        public void onStop(AliyunDownloadMediaInfo info) {
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {

                if (downloadView != null) {
                    downloadView.updateInfo(info);
                }
            }
        }

        @Override
        public void onCompletion(AliyunDownloadMediaInfo info) {
            MineActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                synchronized (aliyunPlayerSkinActivity) {
                    if (downloadView != null) {
                        downloadView.updateInfoByComplete(info);
                    }


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


                //鉴权过期
                if (code.getValue() == ErrorCode.ERROR_SERVER_POP_UNKNOWN.getValue()) {
                    refreshDownloadVidSts(info);
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
//            mPlayerDownloadAdapter.deleteData(info);
        }

        @Override
        public void onDeleteAll() {
//            mPlayerDownloadAdapter.clearAll();
        }

        @Override
        public void onFileProgress(AliyunDownloadMediaInfo info) {

        }
    }

    private List<AliyunDownloadMediaInfo> currentPreparedMediaInfo = null;


    /**
     * 刷新下载的VidSts
     */
    private void refreshDownloadVidSts(final AliyunDownloadMediaInfo downloadMediaInfo) {
        VidStsUtil.getVidSts(downloadMediaInfo.getVidSts().getVid(), new VidStsUtil.OnStsResultListener() {
            @Override
            public void onSuccess(String vid, String akid, String akSecret, String token) {
                if (downloadManager != null) {
                    VidSts vidSts = new VidSts();
                    vidSts.setVid(vid);
                    vidSts.setRegion(PlayParameter.PLAY_PARAM_REGION);
                    vidSts.setAccessKeyId(akid);
                    vidSts.setAccessKeySecret(akSecret);
                    vidSts.setSecurityToken(token);
                    downloadMediaInfo.setVidSts(vidSts);
                    PlayParameter.PLAY_PARAM_AK_ID = akid;
                    PlayParameter.PLAY_PARAM_AK_SECRE = akSecret;
                    PlayParameter.PLAY_PARAM_SCU_TOKEN = token;
                    downloadManager.prepareDownloadByQuality(downloadMediaInfo, AliyunDownloadManager.INTENT_STATE_START);
                }
            }

            @Override
            public void onFail() {

            }
        });

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
                        Log.d("donwload", msg.getData().getString(DOWNLOAD_ERROR_KEY));
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateDownloadView();
    }

    private void updateDownloadView() {
        if (downloadView != null && downloadManager != null) {
            ArrayList<AlivcDownloadMediaInfo> downloadMediaInfo = downloadView.getDownloadMediaInfo();
            for (AlivcDownloadMediaInfo alivcDownloadMediaInfo : downloadMediaInfo) {
                AliyunDownloadMediaInfo aliyunDownloadMediaInfo = alivcDownloadMediaInfo.getAliyunDownloadMediaInfo();
                String savePath = aliyunDownloadMediaInfo.getSavePath();
                if (!TextUtils.isEmpty(savePath)) {
                    File file = new File(savePath);
                    if (!file.exists() && aliyunDownloadMediaInfo.getStatus() == AliyunDownloadMediaInfo.Status.Complete) {
                        downloadView.deleteDownloadInfo(aliyunDownloadMediaInfo);
                        downloadManager.deleteFile(aliyunDownloadMediaInfo);
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        downloadManager.removeDownloadInfoListener(mDownloadInfoListener);
    }

}
