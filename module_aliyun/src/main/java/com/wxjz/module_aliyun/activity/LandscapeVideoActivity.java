package com.wxjz.module_aliyun.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.VcPlayerLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wxjz.module_aliyun.NineGridView.NineGridView;
import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.constants.PlayParameter;
import com.wxjz.module_aliyun.aliyun.listener.MyAnalysisClickListener;
import com.wxjz.module_aliyun.aliyun.listener.MyCompletionListener;
import com.wxjz.module_aliyun.aliyun.listener.MyExerciseClickListener;
import com.wxjz.module_aliyun.aliyun.listener.MyFrameInfoListener;
import com.wxjz.module_aliyun.aliyun.listener.MyOnHintClickListener;
import com.wxjz.module_aliyun.aliyun.listener.MyOnNoteClickListener;
import com.wxjz.module_aliyun.aliyun.listener.MyOnScreenBrightnessListener;
import com.wxjz.module_aliyun.aliyun.listener.MyOrientationChangeListener;
import com.wxjz.module_aliyun.aliyun.listener.MyPlayStateBtnClickListener;
import com.wxjz.module_aliyun.aliyun.listener.MyPrepareListener;
import com.wxjz.module_aliyun.aliyun.listener.MySeekCompleteListener;
import com.wxjz.module_aliyun.aliyun.listener.MySeekStartListener;
import com.wxjz.module_aliyun.aliyun.listener.MyShowMoreClickLisener;
import com.wxjz.module_aliyun.aliyun.listener.MyShowStudyChangeListener;
import com.wxjz.module_aliyun.aliyun.listener.MySpeedChangeListener;
import com.wxjz.module_aliyun.aliyun.utils.Common;
import com.wxjz.module_aliyun.aliyun.utils.FixedToastUtils;
import com.wxjz.module_aliyun.aliyun.utils.VidAuthUtil;
import com.wxjz.module_aliyun.aliyun.utils.VidStsUtil;
import com.wxjz.module_aliyun.aliyun.utils.database.DatabaseManager;
import com.wxjz.module_aliyun.aliyun.utils.database.LoadDbDatasListener;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadInfoListener;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadManager;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadMediaInfo;
import com.wxjz.module_aliyun.aliyun.view.choice.AlivcShowMoreDialog;
import com.wxjz.module_aliyun.aliyun.view.download.AddDownloadView;
import com.wxjz.module_aliyun.aliyun.view.download.DownloadChoiceDialog;
import com.wxjz.module_aliyun.aliyun.view.download.DownloadDataProvider;
import com.wxjz.module_aliyun.aliyun.view.download.DownloadView;
import com.wxjz.module_aliyun.aliyun.view.gesturedialog.BrightnessDialog;
import com.wxjz.module_aliyun.aliyun.view.more.AliyunShowMoreValue;
import com.wxjz.module_aliyun.aliyun.view.more.ShowMoreView;
import com.wxjz.module_aliyun.aliyun.view.more.SpeedValue;
import com.wxjz.module_aliyun.aliyun.view.tipsview.ErrorInfo;
import com.wxjz.module_aliyun.aliyun.widget.AliyunScreenMode;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;
import com.wxjz.module_aliyun.dialogFragment.ExerciseDialog;
import com.wxjz.module_aliyun.dialogFragment.SpeedValueDialog;
import com.wxjz.module_aliyun.dialogFragment.TakeNoteDialog;
import com.wxjz.module_aliyun.dialogFragment.TerminologyDialog;
import com.wxjz.module_aliyun.dialogFragment.TipsDialog;
import com.wxjz.module_aliyun.dialogFragment.VideoListDialog;
import com.wxjz.module_aliyun.event.DialogDissmissEvent;
import com.wxjz.module_aliyun.mvp.contract.LandscapeVideoActivityContract;
import com.wxjz.module_aliyun.mvp.presenter.LandscapeVideoActivityPresenter;
import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.VideoInPlayPageBean;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.bean.VideoPlayHistory;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.DialogShowDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.db.dao.temp.DataGenerator;
import com.wxjz.module_base.db.dao.temp.VideoPlayHistoryDBDao;
import com.wxjz.module_base.dialog.MemberPromptDialog;
import com.wxjz.module_base.event.LoginSuccessEvent;
import com.wxjz.module_base.event.OnFinishVideoEvent;
import com.wxjz.module_base.listener.GetToMemberTipDialogListner;
import com.wxjz.module_base.listener.OnChooseSchoolDialogListener;
import com.wxjz.module_base.listener.OnLoginDialogListenr;
import com.wxjz.module_base.login.LoginDialog;
import com.wxjz.module_base.login.LoginTipsDialog;
import com.wxjz.module_base.util.AppManager;
import com.wxjz.module_base.util.DialogUtil;
import com.wxjz.module_base.util.TTSUtils;
import com.wxjz.module_base.util.ToastUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Route(path = "/module_aliyun/activity/landscape_video_activity")
public class LandscapeVideoActivity extends BaseMvpActivity<LandscapeVideoActivityPresenter> implements LandscapeVideoActivityContract.View, AliyunVodPlayerView.OnPointDialogShowListener {
    private static String preparedVid;
    private PlayerHandler playerHandler;
    private DownloadView dialogDownloadView;
    private AlivcShowMoreDialog showMoreDialog;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS");
    private List<String> logStrs = new ArrayList<>();

    private AliyunScreenMode currentScreenMode = AliyunScreenMode.Small;

    private AliyunVodPlayerView mAliyunVodPlayerView = null;

    private ErrorInfo currentError = ErrorInfo.Normal;

    public boolean isFlag = false;

    //控制学习模式是否是开启还是关闭 默认开启，为了控制相互刷新
    public static boolean isStudyShow = true;

    //请求点位数据是否成功,用于控制学习模式按钮的位置
    public boolean RequestedAllpoint;

    private static final String DEFAULT_VID = "09cf8b916ecc4613aa4a0044f70a06fd";
    /**
     * get StsToken stats
     */
    private boolean inRequest;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private Common commenUtils;

    /**
     * 数据中没有回答问题的下标位置
     */
    private int NoAnswer = -1;


    /**
     * 查询所有笔记的接口
     * TakeNotesListBean.TakeNoteBean
     */
    private List<PointListBean.PointBean> takeNoteBeans = new ArrayList<>();

    /**
     * 查询所有提示接口
     */
    private List<PointListBean.PointBean> tipsBeans = new ArrayList<>();

    /**
     * 查询所有术语接口
     */
    private List<PointListBean.PointBean> terminologyBeans = new ArrayList<>();
    /**
     * 插叙所有课堂练习接口
     * ExerciseListBean.ExerciseBean
     */
    private List<PointListBean.PointBean> exerciseBeans = new ArrayList<>();
    /**
     * 所有点位数据
     */
    private List<PointListBean.PointBean> pointBeans = new ArrayList<>();
    /**
     * 筛选后点位的数据
     */
    private List<PointListBean.PointBean> filterPointBeans = new ArrayList<>();


    /**
     * 笔记dialog
     *
     * @param
     */
    private TakeNoteDialog takeNoteDialog;

    /**
     * 提示Dialog
     *
     * @param
     */
    private TipsDialog tipsDialog;
    /**
     * 倍速切换
     */
    private SpeedValueDialog speedValueDialog;

    /**
     * 术语Dialog
     *
     * @param
     */
    private TerminologyDialog terminologyDialog;
    private String playAuth;
    private String videoId;
    private long oldTime;
    private AliyunDownloadManager downloadManager;
    private DownloadDataProvider downloadDataProvider;
    private boolean mDownloadInPrepare = false;
    private ArrayList<AliyunDownloadMediaInfo> currentPreparedMediaInfo;
    private AliyunScreenMode mCurrentDownloadScreenMode;
    private AliyunDownloadMediaInfo aliyunDownloadMediaInfo;


    private UserInfoBean userInfo;
    /**
     * 是否是点击了重播按钮
     */
    private boolean isReplay;
    /**
     * 是否是播放了下载的视频
     */
    private boolean isPlayDownload;
    /**
     * 当前选择下载的视频
     */
    private VideoInPlayPageBean.VideoListBean currentSelectVideo;
    /**
     * 是否需要展示下载界面,如果是恢复数据,则不用展示下载界面
     */
    private boolean showAddDownloadView;
    /**
     * 已经下载或者下载中的文件
     */
    List<AliyunDownloadMediaInfo> mDownloadedInfos;
    private String sectionName;
    /**
     * 开始下载的事件监听
     */
    private AddDownloadView.OnViewClickListener viewClickListener = new AddDownloadView.OnViewClickListener() {
        @Override
        public void onCancel() {
            if (downloadDialog != null) {
                downloadDialog.dismiss();
            }
        }

        @Override
        public void onDownload(AliyunDownloadMediaInfo info) {
            if (downloadDialog != null) {
                downloadDialog.dismiss();
            }
            aliyunDownloadMediaInfo = info;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                int permission = ContextCompat.checkSelfPermission(LandscapeVideoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(LandscapeVideoActivity.this, PERMISSIONS_STORAGE,
                            REQUEST_EXTERNAL_STORAGE);

                } else {
                    addNewInfo(info);
                }
            } else {
                addNewInfo(info);
            }

        }
    };

    private int id, subId;
    private String useId;
    private int videoDuration;
    private int page = 1;
    private VideoListDialog mVideoListDialog;
    private String url;
    private String title;
    private long videoSize;
    /**
     * 初次进入的时间
     */
    private long enterTime;
    private String gradeId;
    private Integer chapterId;
    private Integer sectionId;
    // private boolean isFree;
    private String userId;

    private void addNewInfo(AliyunDownloadMediaInfo info) {
        if (mDownloadedInfos != null && mDownloadedInfos.size() > 0) {
            for (AliyunDownloadMediaInfo mediaInfo : mDownloadedInfos) {
                if (mediaInfo.getVid().equals(info.getVid()) && mediaInfo.getQuality().equals(info.getQuality())) {
                    //该视频已经下载过，或者下载中
                    if (mediaInfo.getStatus().equals(AliyunDownloadMediaInfo.Status.Start)) {
                        toast("该视频正在下载");
                    } else if (mediaInfo.getStatus().equals(AliyunDownloadMediaInfo.Status.Complete)) {
                        toast("该视频已下载完成");
                    } else {
                        toast("该视频已下载完成");
                    }
                    return;
                }
            }
        }
        if (downloadManager != null && info != null) {
            //todo
//            downloadManager.addDownloadMedia(info);
//            callDownloadPrepare(info.getVid(), info.getTitle());
            downloadManager.startDownload(info);
            if (mVideoListDialog != null) {
                currentSelectVideo.setDownload_status(1);
                mVideoListDialog.updateViewByDownloadingVideo(currentSelectVideo);
            }
        }
    }

    /**
     * 课堂练习的dialog
     *
     * @param
     */
    private ExerciseDialog exerciseDialog;

    /**
     * 课堂练习中回答了问题，需要刷新对应进度条上对应的数据下标位置
     */
    private int exerciseListIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        copyAssets();
        TTSUtils.getInstance().init();
        DatabaseManager.getInstance().createDataBase(this);
        try {
            userId = UserInfoDao.getInstence().getCurrentUserInfo().getUserId();
        } catch (Exception e) {
            userId = "guest";
        }

        showAddDownloadView = false;
    }

    private void copyAssets() {
        commenUtils = Common.getInstance(getApplicationContext()).copyAssetsToSD("encrypt", "wxjz/encrypted");
        commenUtils.setFileOperateCallback(

                new Common.FileOperateCallback() {

                    @Override
                    public void onSuccess() {
                        // 获取AliyunDownloadManager对象
                        downloadManager = AliyunDownloadManager.getInstance(getApplicationContext());
                        downloadManager.setEncryptFilePath(BasisConstants.Aliyun.CACHE_PATH + "/encrypted/encryptedApp.dat");
                        //  PrivateService.initService(getApplicationContext(), BasisConstants.Aliyun.CACHE_PATH + "/encrypted/encryptedApp.dat");
                        // downloadManager.setDownloadDir(file.getAbsolutePath());
                        //设置同时下载个数
                        downloadManager.setMaxNum(3);

                        downloadDataProvider = DownloadDataProvider.getSingleton(getApplicationContext());

                        // 视频下载的回调
                        downloadManager.setDownloadInfoListener(new MyDownloadInfoListener(LandscapeVideoActivity.this));
                        //   downloadViewSetting(downloadView);
                        downloadDataProvider.restoreMediaInfo(new LoadDbDatasListener() {
                            @Override
                            public void onLoadSuccess(List<AliyunDownloadMediaInfo> dataList) {
                                mDownloadedInfos = new ArrayList<>();
                                mDownloadedInfos.addAll(dataList);
                            }
                        });

                    }

                    @Override
                    public void onFailed(String error) {
                    }
                });
    }

    @Override
    protected boolean needEventBus() {
        return true;
    }

    @Override
    protected LandscapeVideoActivityPresenter createPresenter() {
        return new LandscapeVideoActivityPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //设置九宫图的加载方式
        setNineGlideloading();

    }

    @Override
    protected void setStatusBarColor(int color) {
        //  StatusBarUtil.setColor(this, getResources().getColor(android.R.color.black), 0);
    }

    @Override
    protected void initView() {
        super.initView();

//        requestVidSts();
        initAliyunPlayerView();
        getIntentData();

        requestVidAuth();


    }


    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        subId = intent.getIntExtra("subId", -1);
        playAuth = intent.getStringExtra("playAuth");
        videoId = intent.getStringExtra("videoId");
        String chapterId1 = intent.getStringExtra("chapterId");
        String sectionId1 = intent.getStringExtra("sectionId");
        //isFree = intent.getBooleanExtra("isFree", false);
        if (TextUtils.isEmpty(chapterId1) || chapterId1.equalsIgnoreCase("0")) {
            chapterId = null;
        } else {
            chapterId = Integer.valueOf(chapterId1);
        }
        if (TextUtils.isEmpty(sectionId1) || sectionId1.equalsIgnoreCase("0")) {
            sectionId = null;
        } else {
            sectionId = Integer.valueOf(sectionId1);
        }

        gradeId = intent.getStringExtra("gradeId");
        videoDuration = intent.getIntExtra("videoDuration", -1);
        /**
         * 请求所有的点位数据
         */
        UserInfoBean userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfo != null) {
            useId = userInfo.getUserId();
        }
        if (userInfo != null && !isPlayDownload) {
            //新增学习记录
            mPresenter.insertLearnTime(id, subId);
        }

        if (!TextUtils.isEmpty(useId) && id != -1) {
            mPresenter.getAllPoints(useId, -1, id);
        }
        //播放下载视频
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        if (url != null && title != null) {
            mAliyunVodPlayerView.updateScreenShow();
            changePlayLocalSource(url, title);
            isPlayDownload = true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addNewInfo(aliyunDownloadMediaInfo);
            } else {
                // Permission Denied
                FixedToastUtils.show(LandscapeVideoActivity.this, "没有sd卡读写权限, 无法下载");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 请求播放凭证
     */
    private void requestVidAuth() {
        if (inRequest) {
            return;
        }
        inRequest = true;
        if (TextUtils.isEmpty(PlayParameter.PLAY_PARAM_VID)) {
            PlayParameter.PLAY_PARAM_VID = DEFAULT_VID;
        }
        if (TextUtils.isEmpty(videoId) || TextUtils.isEmpty(playAuth)) {
            onAuthFail();
        } else {
            onAuthSuccess(videoId, playAuth);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_landscape_video;
    }


    private void initAliyunPlayerView() {
        mAliyunVodPlayerView = (AliyunVodPlayerView) findViewById(R.id.video_view);
        //保持屏幕敞亮
        mAliyunVodPlayerView.setKeepScreenOn(true);
        PlayParameter.PLAY_PARAM_TYPE = "vidauth";
        // PlayParameter.PLAY_PARAM_URL = DEFAULT_URL;
        String sdDir = BasisConstants.Aliyun.ALICACHE;
        mAliyunVodPlayerView.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Orange);

        //mAliyunVodPlayerView.setCirclePlay(true);
        mAliyunVodPlayerView.setAutoPlay(true);

        mAliyunVodPlayerView.setOnPreparedListener(new MyPrepareListener(this));
        mAliyunVodPlayerView.setNetConnectedListener(new MyNetConnectedListener(this));
        mAliyunVodPlayerView.setOnCompletionListener(new MyCompletionListener(this));
        mAliyunVodPlayerView.setOnFirstFrameStartListener(new MyFrameInfoListener(this));
//        mAliyunVodPlayerView.setOnChangeQualityListener(new MyChangeQualityListener(this));
//        mAliyunVodPlayerView.setOnStoppedListener(new MyStoppedListener(this));
        mAliyunVodPlayerView.setmOnPlayerViewClickListener(new MyPlayViewClickListener(this));
        mAliyunVodPlayerView.setOrientationChangeListener(new MyOrientationChangeListener(this));
        mAliyunVodPlayerView.setOnShowMoreClickListener(new MyShowMoreClickLisener(this));
        mAliyunVodPlayerView.setOnPlayStateBtnClickListener(new MyPlayStateBtnClickListener(this));
        mAliyunVodPlayerView.setOnSeekCompleteListener(new MySeekCompleteListener(this));
        mAliyunVodPlayerView.setOnSeekStartListener(new MySeekStartListener(this));
        mAliyunVodPlayerView.setOnNoteClicklistener(new MyOnNoteClickListener(this));
        mAliyunVodPlayerView.setOnHintClickListener(new MyOnHintClickListener(this));
        mAliyunVodPlayerView.setOnExerciseClickListener(new MyExerciseClickListener(this));
        mAliyunVodPlayerView.setOnAnalysisClickListener(new MyAnalysisClickListener(this));
        mAliyunVodPlayerView.setOnSpeedChangeClickListener(new MySpeedChangeListener(this));
        mAliyunVodPlayerView.setOnStudyStatusListenr(new MyShowStudyChangeListener(this));
        mAliyunVodPlayerView.setOnScreenBrightnessListener(new MyOnScreenBrightnessListener(this));
        mAliyunVodPlayerView.setScreenBrightness(BrightnessDialog.getActivityBrightness(LandscapeVideoActivity.this));
        mAliyunVodPlayerView.setOnPointDialogShowListener(this);
        mAliyunVodPlayerView.setOnReplayListener(new MyOnRePlayClickListener(this));
        mAliyunVodPlayerView.enableNativeLog();
        initSpeedDialog();
    }

    private void setPlaySource() {
        if ("localSource".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            UrlSource urlSource = new UrlSource();
            urlSource.setUri(PlayParameter.PLAY_PARAM_URL);

            //默认是5000
            int maxDelayTime = 5000;
            if (PlayParameter.PLAY_PARAM_URL.startsWith("artp")) {
                //如果url的开头是artp，将直播延迟设置成100，
                maxDelayTime = 100;
            }
            if (mAliyunVodPlayerView != null) {
                PlayerConfig playerConfig = mAliyunVodPlayerView.getPlayerConfig();
                playerConfig.mMaxDelayTime = maxDelayTime;
                mAliyunVodPlayerView.setPlayerConfig(playerConfig);
                mAliyunVodPlayerView.setLocalSource(urlSource);

            }

        } else if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            if (!inRequest) {
                VidSts vidSts = new VidSts();
                vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
                vidSts.setRegion(PlayParameter.PLAY_PARAM_REGION);
                vidSts.setAccessKeyId(PlayParameter.PLAY_PARAM_AK_ID);
                vidSts.setAccessKeySecret(PlayParameter.PLAY_PARAM_AK_SECRE);
                vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setVidSts(vidSts);
                }
            }
        } else if ("vidauth".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            VidAuth vidAuth = new VidAuth();
            vidAuth.setPlayAuth(PlayParameter.PLAY_PARAM_PLAY_AUTH);
            vidAuth.setVid(PlayParameter.PLAY_PARAM_VID);
            if (mAliyunVodPlayerView != null) {
                mAliyunVodPlayerView.setAuthInfo(vidAuth);
            }

        }
    }

    private static final int DOWNLOAD_ERROR = 1;
    private static final String DOWNLOAD_ERROR_KEY = "error_key";


    /**
     * 点击popwindow 显示对应的弹窗
     *
     * @param index
     * @param type
     */
    @Override
    public void onPointDialogShow(int index, int type) {
        /**
         *  0 提示
         *  1 术语
         *  2 题目
         *  3 笔记
         */
        if (filterPointBeans.size() > index) {
            //防止list下标越界
            PointListBean.PointBean pointBean = filterPointBeans.get(index);
            switch (type) {
                case 0:
                    showSingleTipsDialog(pointBean);
                    break;
                case 1:
                    showSingelTerminology(pointBean);
                    break;
                case 2:
                    showSingelexercise(pointBean, index);
                    break;
                case 3:
                    showSingleTakeNote(pointBean, pointBeans.get(index).getId());
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 展示一个单独的笔记dialog
     *
     * @param pointBean
     */
    private void showSingleTakeNote(PointListBean.PointBean pointBean, int id) {
        takeNoteBeans.clear();
        takeNoteBeans.add(pointBean);
        initTakeNoteDialog();
        if (takeNoteDialog != null) {
            takeNoteDialog.setDataList(takeNoteBeans);
            takeNoteDialog.show(getSupportFragmentManager());
            mAliyunVodPlayerView.pause();
        }

    }

    /**
     * 展示一个单独的题目dialog
     *
     * @param pointBean
     */
    private void showSingelexercise(PointListBean.PointBean pointBean, int index) {
        pointBean.setStatus(true);
        exerciseBeans.clear();
        exerciseBeans.add(pointBean);
        initExercise();
        if (exerciseDialog != null) {
            exerciseDialog.setDataList(exerciseBeans);
            exerciseDialog.show(getSupportFragmentManager());
            mAliyunVodPlayerView.pause();

        }
    }

    /**
     * 展示一个单独的术语dialog
     *
     * @param pointBean
     */
    private void showSingelTerminology(PointListBean.PointBean pointBean) {
        terminologyBeans.clear();
        terminologyBeans.add(pointBean);
        initTerminology();
        if (terminologyDialog != null) {
            terminologyDialog.setDataList(terminologyBeans);
            terminologyDialog.show(getSupportFragmentManager());
            mAliyunVodPlayerView.pause();
        }
    }

    /**
     * 展示一个单独的提示的dialog
     *
     * @param pointBean
     */
    private void showSingleTipsDialog(PointListBean.PointBean pointBean) {
        tipsBeans.clear();
        tipsBeans.add(pointBean);
        initTipsDialog();
        if (tipsDialog != null) {
            tipsDialog.setDataList(tipsBeans);
            tipsDialog.show(getSupportFragmentManager());
            mAliyunVodPlayerView.pause();
        }
    }

    private Dialog thisdialog;
    /*********************笔记对话框******************************/

    /**
     * 初始化笔记弹窗
     */
    private void initTakeNoteDialog() {
        long current = mAliyunVodPlayerView.getCurrentPosition();
        takeNoteDialog = (TakeNoteDialog) TakeNoteDialog.newInstance()
                .setOutCancel(true)
                .setAnimStyle(R.style.dialogWindowAnim)
                .setSize((int) (getScreenWidth(this) * 0.6), getScreenHeight(this))
                .setGravity(Gravity.LEFT)
                .setUseEventBus(true);
        takeNoteDialog.setDataList(takeNoteBeans);
        takeNoteDialog.setProgcesscurrent(current);

        takeNoteDialog.setOnDeleteNotesItemLisenter(new TakeNoteDialog.onDeleteNotesItemLisenter() {
            @Override
            public void deleteNoteItem(int Id) {
                mPresenter.deleteDomInfoPad(String.valueOf(Id), 3);
            }
        });
        takeNoteDialog.setOnSaveNoteClickListener(new TakeNoteDialog.onSaveNoteClickListener() {
            @Override
            public void onSaveNote(int type, int Id, long progress, String content, Dialog dialog) {
                thisdialog = dialog;
                if (type == 0) {
                    //保存笔记
                    mPresenter.insertDomNote(useId, id, 3, new Long(progress / 1000).intValue(), content);
                } else if (type == 1) {
                    //更新笔记
                    mPresenter.updateDomNote(Id, content);
                }
            }
        });
    }

    /**
     * 查询所有笔记返回的数据
     *
     * @param takeNotesListBean
     */
    @Override
    public void onSelectDomNote(PointListBean takeNotesListBean) {
        takeNoteBeans = takeNotesListBean.getData();
        long current = mAliyunVodPlayerView.getCurrentPosition();
        if (takeNoteBeans != null) {
            mAliyunVodPlayerView.pause();
            if (takeNoteDialog != null) {
                takeNoteDialog.setDataList(takeNoteBeans);
                takeNoteDialog.setProgcesscurrent(current);
                if (!takeNoteDialog.isVisible()) {
                    takeNoteDialog.show(getSupportFragmentManager());
                }
            }
        }
    }

    /**
     * 删除笔记返回的数据
     */
    @Override
    public void onDeleteDomInfoPad() {
        mPresenter.getSelectDomNote(useId, 3, id);
        mPresenter.getAllPoints(useId, -1, id);
    }

    /**
     * 新增一条笔记
     */
    @Override
    public void onInsertDomNote() {
        mPresenter.getSelectDomNote(useId, 3, id);
        mPresenter.getAllPoints(useId, -1, id);
        if (thisdialog != null && thisdialog.isShowing()) {
            thisdialog.dismiss();
        }
        ToastUtil.showTextToas(this, "保存成功");
    }

    /**
     * 更新一条笔记
     */
    @Override
    public void onUpdateDomNote() {
        mPresenter.getSelectDomNote(useId, 3, id);
        mPresenter.getAllPoints(useId, -1, id);
        if (thisdialog != null && thisdialog.isShowing()) {
            thisdialog.dismiss();
        }
        ToastUtil.showTextToas(this, "修改成功");
    }

    /*************************************************************/

    /*************************提示**********************************/
    private void initTipsDialog() {

        tipsDialog = (TipsDialog) TipsDialog.newInstance()
                .setOutCancel(true)
                .setAnimStyle(R.style.dialogWindowAnim)
                .setSize((int) (getScreenWidth(this) * 0.6), getScreenHeight(this))
                .setGravity(Gravity.LEFT)
                .setUseEventBus(true);


    }

    @Override
    public void onTipsListBean(PointListBean listBean) {
        tipsBeans = listBean.getData();
        if (tipsBeans != null) {
            if (tipsDialog != null) {
                tipsDialog.setDataList(tipsBeans);
                if (!tipsDialog.isVisible()) {
                    tipsDialog.show(getSupportFragmentManager());
                }
            }
        }
    }

    /***************************************************************/

    /***************************术语**********************************/

    private void initTerminology() {
        if (terminologyDialog == null) {
            terminologyDialog = (TerminologyDialog) TerminologyDialog.newInstance()
                    .setOutCancel(true)
                    .setAnimStyle(R.style.dialogWindowAnim)
                    .setSize((int) (getScreenWidth(this) * 0.6), getScreenHeight(this))
                    .setGravity(Gravity.LEFT)
                    .setUseEventBus(true);
        }

    }

    @Override
    public void onTerminologyList(PointListBean listBean) {
        terminologyBeans = listBean.getData();
        if (terminologyBeans != null) {
            if (terminologyDialog != null) {
                terminologyDialog.setDataList(terminologyBeans);
                if (!terminologyDialog.isVisible()) {
                    terminologyDialog.show(getSupportFragmentManager());
                }
            }
        }
    }

    @Override
    public void onRefreshDownloadVidAuth(PlayAuthBean playAuthBean, AliyunDownloadMediaInfo info) {

    }


    /*************************************************************/

    /**********************课堂练习**********************************/
    private void initExercise() {
        exerciseDialog = (ExerciseDialog) ExerciseDialog.newInstance()
                .setOutCancel(true)
                .setAnimStyle(R.style.dialogWindowAnim)
                .setSize((int) (getScreenWidth(this) * 0.6), getScreenHeight(this))
                .setGravity(Gravity.LEFT)
                .setUseEventBus(true);
        /**
         * 提交了问题的选项
         */
        exerciseDialog.setOnAnswerResult(new ExerciseDialog.OnAnswerResult() {
            @Override
            public void onAnswerResult(int domId, String userAnswer, int isRight) {
                //等于-1就是接口调失败了，不做任何处理
                for (int i = 0; i < filterPointBeans.size(); i++) {
                    if (domId == filterPointBeans.get(i).getId()) {
                        exerciseListIndex = i;
                        break;
                    }
                }
                if (exerciseListIndex != -1) {
                    //说明在所有点位列表中找到了该点位
                    filterPointBeans.get(exerciseListIndex).setUserAnswer(userAnswer);
                    filterPointBeans.get(exerciseListIndex).setIsAnswer(1);
                    filterPointBeans.get(exerciseListIndex).setIsRight(isRight);
                }
                for (int i = 0; i < filterPointBeans.size(); i++) {
                    if (filterPointBeans.get(i).getDomType() == 2) {
                        if (filterPointBeans.get(i).getIsRight() == 0) {
                            NoAnswer = i;
                            Log.d("LF789", "未答题的位置--" + i);
                            mAliyunVodPlayerView.setIsAnswerIndex(NoAnswer);
                            return;
                        }
                    }
                }
                NoAnswer = -1;
                Log.d("LF789", "已经答完了所有题目" + NoAnswer);
                mAliyunVodPlayerView.setIsAnswerIndex(NoAnswer);
            }
        });


        /**
         * 需要去朗读文字
         */
        exerciseDialog.setOnTextToSpeek(new ExerciseDialog.OnTextToSpeek() {
            @Override
            public void OnSpeek(String domContent) {
                TTSUtils.getInstance().speak(domContent);
            }
        });
    }

    @Override
    public void onExerciseList(PointListBean exerciseListBean) {
        exerciseBeans = exerciseListBean.getData();
        if (exerciseBeans != null) {
            if (exerciseDialog != null) {
                exerciseDialog.setDataList(exerciseBeans);
                if (!exerciseDialog.isVisible()) {
                    exerciseDialog.show(getSupportFragmentManager());
                }
            }
        }
    }

    @Override
    public void onInsertUserAnswer() {

    }


    /**
     * 获取所有打点的信息
     *
     * @param pointBean
     */
    @Override
    public void onAllPoints(PointListBean pointBean) {
        if (pointBean != null) {
            if (mAliyunVodPlayerView != null) {
                if (pointBean.getData() != null && pointBean.getData().size() > 0) {
                    //请求成功并有数据。
                    RequestedAllpoint = true;
                    isStudyShow = true;
                    mAliyunVodPlayerView.setStudyUI(true);
                    mAliyunVodPlayerView.setStudyViewShow(true);
                    pointBeans = pointBean.getData();
                    //对点位进行排序，按照优先级，如果笔记与其他点位重合，将笔记的点位放在其他点位之前，因为其他点位
                    //是不会重合的。如果笔记自身重合，将按照笔记Id的优先级,将后一个笔记放在最后。
                    pointBeans = repeatSortBean(pointBeans);
                    //将排好的list利用HashMap去重
                    pointBeans = andAllData(pointBeans);
                    //将笔记筛选出去
                    filterPointBeans = filterData(pointBeans);
                    //如果获取的videoDuration不为-1的话
                    if (videoDuration != -1) {
                        mAliyunVodPlayerView.setMutiPoints(pointBeans, filterPointBeans, videoDuration * 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAliyunVodPlayerView.setMutiPoints(pointBeans, filterPointBeans, mAliyunVodPlayerView.getDuration());
                            }
                        }, 500);
                    }
                    //循环排序后的数据，判断当前的题目是否有未答的题目，并将未答题目的下标传递到播放页面
                    for (int i = 0; i < filterPointBeans.size(); i++) {
                        if (filterPointBeans.get(i).getDomType() == 2) {
                            if (TextUtils.isEmpty(filterPointBeans.get(i).getUserAnswer())) {
                                NoAnswer = i;
                                mAliyunVodPlayerView.setIsAnswerIndex(NoAnswer);
                                return;
                            } else if (filterPointBeans.get(i).getIsRight() == 0) {
                                NoAnswer = i;
                                mAliyunVodPlayerView.setIsAnswerIndex(NoAnswer);
                                return;
                            }
                        }
                    }
                    //走到这步，说明是没有未答的题目
                    mAliyunVodPlayerView.setIsAnswerIndex(-1);
                } else {
                    //请求成功但是也是没有数据的话，也不展示学习互动模式
                    mAliyunVodPlayerView.setStudyUI(false);
                    mAliyunVodPlayerView.setStudyViewShow(true);
                    RequestedAllpoint = false;
                }
            }
        } else {
            //请求失败或者网络不好，不展示学习互动模式
            mAliyunVodPlayerView.setStudyUI(false);
            mAliyunVodPlayerView.setStudyViewShow(false);
            isStudyShow = false;
            RequestedAllpoint = false;
        }
    }

    /**
     * 去除里面的重复元素，
     * 并且按照规律去重
     * 题目优先，笔记最后
     * 如果笔记有相同的，取最后创建时间
     *
     * @param pointBeans
     * @return
     */
    public List<PointListBean.PointBean> andAllData(List<PointListBean.PointBean> pointBeans) {
        List<PointListBean.PointBean> filterBeans = new ArrayList<>();
        Map<Long, PointListBean.PointBean> DataMap = new HashMap<>();
        for (int i = 0; i < pointBeans.size(); i++) {
            DataMap.put(pointBeans.get(i).getVideoDom(), pointBeans.get(i));
        }
        filterBeans = filterNoteData(DataMap, filterBeans);

        return filterBeans;
    }

    public List<PointListBean.PointBean> filterData(List<PointListBean.PointBean> pointBeans) {
        List<PointListBean.PointBean> filterBeans = new ArrayList<>();
        for (int i = 0; i < pointBeans.size(); i++) {
            if (pointBeans.get(i).getDomType() != 3) {
                filterBeans.add(pointBeans.get(i));
            }
        }
        return filterBeans;
    }

    /**
     * 对已经排过序的并去重的数据重新生成一个新的list。
     *
     * @param map
     * @param filterBeans
     * @return
     */
    public List<PointListBean.PointBean> filterNoteData(Map<Long, PointListBean.PointBean> map, List<PointListBean.PointBean> filterBeans) {
        for (Map.Entry<Long, PointListBean.PointBean> entry : map.entrySet()) {
            filterBeans.add(entry.getValue());
        }
        filterBeans = repeatSortBean(filterBeans);
        return filterBeans;
    }

    /**
     * 对可能有重复点位的list排序
     * 解决问题，按照videoDom排序，如果笔记和其他点位重合将笔记放在重合点位之前。如果没有重合，笔记自身重和，
     * 根据笔记的主键ID排序。
     *
     * @param filterBean
     * @return
     */
    public List<PointListBean.PointBean> repeatSortBean(List<PointListBean.PointBean> filterBean) {
        Collections.sort(filterBean, new Comparator<PointListBean.PointBean>() {
            @Override
            public int compare(PointListBean.PointBean pointBean, PointListBean.PointBean t1) {
                int progress = (int) (pointBean.getVideoDom() - t1.getVideoDom());
                if (progress == 0) {
                    int domtype = t1.getDomType() - pointBean.getDomType();
                    if (domtype == 0) {
                        return pointBean.getId() - t1.getId();
                    } else {
                        return domtype;
                    }
                }
                return progress;
            }
        });
        return filterBean;
    }

    /**
     * 视频列表要下载视频的播放凭证, 获取到播放凭证就开始走下载流程，
     *
     * @param playAuth
     */
    @Override
    public void onPlayAuth(String playAuth) {
        if (!TextUtils.isEmpty(playAuth)) {
//            if (mAliyunVodPlayerView != null) {
//                MediaInfo currentMediaInfo = mAliyunVodPlayerView.getCurrentMediaInfo();
//                if (currentMediaInfo != null && currentMediaInfo.getVideoId().equals(PlayParameter.PLAY_PARAM_VID)) {
//                    VidAuth vidAuth = new VidAuth();
//
//                    vidAuth.setPlayAuth(playAuth);
//                    vidAuth.setVid(videoId);
//                    //todo 下载入口
//                    downloadManager.prepareDownload(vidAuth, courseId, courseName, courseCover);
//                }
//            }
            if (isReplay) {
                isReplay = false;
                VidAuth vidAuth = new VidAuth();
                PlayParameter.PLAY_PARAM_PLAY_AUTH = playAuth;
                vidAuth.setPlayAuth(playAuth);
                vidAuth.setVid(PlayParameter.PLAY_PARAM_VID);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setAuthInfo(vidAuth);
                }

            } else if (currentSelectVideo != null && !TextUtils.isEmpty(currentSelectVideo.getVideoId())) {
                VidAuth vidAuth = new VidAuth();
                vidAuth.setPlayAuth(playAuth);
                vidAuth.setVid(currentSelectVideo.getVideoId());
                //todo 下载入口
                //  downloadManager.prepareDownload(vidAuth, courseId, courseName, courseCover, videoSize);
                int sectionId = currentSelectVideo.getSectionId();
                if (sectionId == 0) {
                    sectionId = currentSelectVideo.getChapterId();
                }

                downloadManager.prepareDownload(vidAuth, videoSize, userId, currentSelectVideo.getCoverUrl(), sectionName, sectionId, String.valueOf(id));
            } else {
                toast("下载视频失败");
            }
        }
    }

    @Override
    public void onInsertLearnTime() {
        EventBus.getDefault().post(new OnFinishVideoEvent(true));
    }


    @Override
    public void onUserInfo(com.wxjz.module_base.bean.UserInfoBean userInfoBean) {
        hideLoading();
        if (userInfoBean != null) {
            UserInfoDao.getInstence().saveUserInfo(userInfoBean);
            UserInfoBean userInfoBean1 = UserInfoDao.getInstence().getCurrentUserInfo();
            int isMember = userInfoBean.getUser().getIsMember();
            if (isMember == 1) {
                CheckGradeDao.getInstance().updataGuestChooseGradeId(userInfoBean1.getNianji());
                //会员 返回首页刷新数据
                mPresenter.getLeveListNoLevelID(false);
            } else {
                //登录成功刷新主页
                EventBus.getDefault().post(new LoginSuccessEvent());
                useId = userInfoBean.getUser().getUserId();
                if (!TextUtils.isEmpty(useId)) {
                    mPresenter.getAllPoints(useId, -1, id);
                }
                mAliyunVodPlayerView.start();
            }
        } else {
            ToastUtil.show(this, "当前用户信息为空");

        }
    }

    //登录成功以后需要遍历去判断是否有符合的年级
    @Override
    public void onLevelListByNoLevelID(List<LevelListBean> levelListBeans) {
        String gradeId = CheckGradeDao.getInstance().queryGradeId();
        userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (!TextUtils.isEmpty(gradeId)) {
            for (int i = 0; i < levelListBeans.size(); i++) {
                for (int j = 0; j < levelListBeans.get(i).getGradeList().size(); j++) {
                    if (gradeId.equals(levelListBeans.get(i).getGradeList().get(j).getId())) {
                        CheckGradeDao.getInstance().addGuestChooseGrade(levelListBeans.get(i).getId(), levelListBeans.get(i).getLevelName(), levelListBeans.get(i).getGradeList().get(j).getId(), levelListBeans.get(i).getGradeList().get(j).getGradeName(), true);
                        AppManager.getAppManager().finishAllActivity();
                        ARouter.getInstance().build("/main/main_activity").navigation();
                        return;
                    }
                }
            }
            CheckGradeDao.getInstance().addGuestChooseGrade(1, "小学", "P1", "一年级", false);
            AppManager.getAppManager().finishAllActivity();
            ARouter.getInstance().build("/main/main_activity").navigation();
        }
    }

    /**
     * 获取该课程下视频,已经比对过，设置完了 下载状态
     *
     * @param
     */
    @Override
    public void onCourseInVideo(List<VideoInPlayPageBean.VideoListBean> mNewVideolist, String sectionName) {
        this.sectionName = sectionName;
        //显示👉视频列表窗口
        mVideoListDialog = VideoListDialog.getInstance(mNewVideolist, videoId);
        mVideoListDialog.setGravity(Gravity.RIGHT)
                .setAnimStyle(R.style.dialogRightAnim)
                .setOutCancel(true)
                .show(getSupportFragmentManager());


        mVideoListDialog.setOnItemClickListener(new VideoListDialog.OnItemClickListener() {

            @Override
            public void onItemClick(VideoInPlayPageBean.VideoListBean downloadVideo) {
                // TODO: 2019/11/3  videoSize没有
                videoSize = downloadVideo.getVideoSize();
//                LandscapeVideoActivity aliyunPlayerSkinActivity = weakReference.get();
//                if (aliyunPlayerSkinActivity != null) {
                showAddDownloadView = true;
//                }
                currentSelectVideo = downloadVideo;

                //获取选择下载视频的播放凭证
                mPresenter.getPlayAuth(downloadVideo.getVideoId());
            }
        });

    }

    //上啦加载
    @Override
    public void onLoadMoreCourseInVideo(List<VideoInPlayPageBean.VideoListBean> mNewVideolist, String sectionName) {
        //显示👉视频列表窗口
        if (mVideoListDialog != null) {
            this.sectionName = sectionName;
            mVideoListDialog.onLoadMoreVideo(mNewVideolist);
        }
    }

    //加载更多视频
    public void loadMoreVideoList() {
        if (downloadManager != null) {
            mPresenter.loadMoreVideoInCourse(downloadManager, chapterId, sectionId, gradeId, ++page, 10);
        }
    }

    /****************************************************************/

    private static class PlayerHandler extends Handler {
        //持有弱引用AliyunPlayerSkinActivity,GC回收时会被回收掉.
        private final WeakReference<LandscapeVideoActivity> mActivty;

        public PlayerHandler(LandscapeVideoActivity activity) {
            mActivty = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LandscapeVideoActivity activity = mActivty.get();
            super.handleMessage(msg);
            if (activity != null) {
                switch (msg.what) {
                    case DOWNLOAD_ERROR:

                        break;
                    default:
                        break;
                }
            }
        }
    }


    public void onFirstFrameStart() {
        if (mAliyunVodPlayerView != null) {

            VideoPlayHistory videoPlayHistory = VideoPlayHistoryDBDao.getInstence().querySearchHistory(PlayParameter.PLAY_PARAM_VID);
            long position = videoPlayHistory.getPosition();
            if (isFlag) {
                isFlag = false;
                mAliyunVodPlayerView.setCurrentVideoPos(0, true);
            } else {
                if (Math.abs(position - videoDuration * 1000) < 1000) {
                    mAliyunVodPlayerView.setCurrentVideoPos(0, true);
                } else {
                    mAliyunVodPlayerView.setCurrentVideoPos(position, true);
                }
            }

        }

    }


    public void onCompletion() {
        //更新进度
//        // 当前视频播放结束, 播放下一个视频
//        if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
//            onNext();
//        }
    }

//    private void onNext() {
//        if (currentError == ErrorInfo.UnConnectInternet) {
//            // 此处需要判断网络和播放类型
//            // 网络资源, 播放完自动波下一个, 无网状态提示ErrorTipsView
//            // 本地资源, 播放完需要重播, 显示Replay, 此处不需要处理
//            if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
//                mAliyunVodPlayerView.showErrorTipView(4014, "-1", "当前网络不可用");
//            }
//            return;
//        }
//
//        currentVideoPosition++;
//        if (currentVideoPosition > alivcVideoInfos.size() - 1) {
//            //列表循环播放，如发现播放完成了从列表的第一个开始重新播放
//            currentVideoPosition = 0;
//        }
//
//        if (alivcVideoInfos.size() > 0) {
//            AlivcVideoInfo.DataBean.VideoListBean video = alivcVideoInfos.get(currentVideoPosition);
//            if (video != null) {
//                changePlayVidSource(video);
//            }
//        }
//    }


    /**
     * 页面重新创建展示数据
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    /**
     * 页面被销毁保存数据
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onPrepared() {
        if (mAliyunVodPlayerView != null) {
            int duration = mAliyunVodPlayerView.getDuration();
            // mAliyunVodPlayerView.setCurrentVideoPos(5000l,duration,true);
            //   mAliyunVodPlayerView.setSeekPosition(20000);
        }
    }


    private void onAuthFail() {
        if (playAuth != null) {
            FixedToastUtils.show(getApplicationContext(), R.string.request_vidsts_fail);
        }
        inRequest = false;
        //finish();
    }


    private void onAuthSuccess(String mVid, String auth) {
        PlayParameter.PLAY_PARAM_VID = mVid;
        PlayParameter.PLAY_PARAM_PLAY_AUTH = auth;
        inRequest = false;
        setPlaySource();
    }


    public void hideShowMoreDialog(boolean from, AliyunScreenMode currentMode) {
        if (showMoreDialog != null) {
            if (currentMode == AliyunScreenMode.Small) {
                showMoreDialog.dismiss();
                currentScreenMode = currentMode;
            }
        }
    }

    public void hideDownloadDialog(boolean from, AliyunScreenMode currentMode) {

        if (downloadDialog != null) {
            if (currentScreenMode != currentMode) {
                downloadDialog.dismiss();
                currentScreenMode = currentMode;
            }
        }
    }


//    public void showSections(LandscapeVideoActivity activity) {
//        AlivcShowMoreDialog showSectionsDialog = new AlivcShowMoreDialog(activity);
//        ShowSectionView showSectionView = new ShowSectionView(this);
//        showSectionsDialog.setContentView(showSectionView);
//        showSectionsDialog.show();
//        showSectionView.setOnSectionSelectListener(new ShowSectionView.OnSectionSelectListener() {
//            @Override
//            public void onSectionSelect() {
//
//                changePlayLocalSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4", "我是标题");
//            }
//        });
//    }


    public void showMore(final LandscapeVideoActivity activity) {
        showMoreDialog = new AlivcShowMoreDialog(activity);
        AliyunShowMoreValue moreValue;
        moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(mAliyunVodPlayerView.getCurrentSpeed());
        moreValue.setVolume((int) mAliyunVodPlayerView.getCurrentVolume());
        moreValue.setStudyStatus(RequestedAllpoint);
        ShowMoreView showMoreView = new ShowMoreView(activity, moreValue);
        showMoreDialog.setContentView(showMoreView);
        showMoreDialog.show();


        showMoreView.setOnScreenCastButtonClickListener(new ShowMoreView.OnScreenCastButtonClickListener() {
            @Override
            public void onScreenCastClick() {
                FixedToastUtils.show(LandscapeVideoActivity.this, "功能开发中, 敬请期待...");
            }
        });

        showMoreView.setOnBarrageButtonClickListener(new ShowMoreView.OnBarrageButtonClickListener() {
            @Override
            public void onBarrageClick() {
                FixedToastUtils.show(LandscapeVideoActivity.this, "功能开发中, 敬请期待...");
            }
        });
        //速度切换
        showMoreView.setOnSpeedCheckedChangedListener(new ShowMoreView.OnSpeedCheckedChangedListener() {
            @Override
            public void onSpeedChanged(RadioGroup group, int checkedId) {
                // 点击速度切换
                if (checkedId == R.id.rb_speed_slow) {
                    Log.d("LF123", "切换速度为0.75");
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneSlow);
                    mAliyunVodPlayerView.setTvSpeedValue("0.75x");
                } else if (checkedId == R.id.rb_speed_normal) {
                    Log.d("LF123", "切换速度为1.0");
                    mAliyunVodPlayerView.setTvSpeedValue("1.0x");
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.One);
                } else if (checkedId == R.id.rb_speed_onequartern) {
                    Log.d("LF123", "切换速度为1.25");
                    mAliyunVodPlayerView.setTvSpeedValue("1.25x");
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
                } else if (checkedId == R.id.rb_speed_onehalf) {
                    Log.d("LF123", "切换速度为1.5");
                    mAliyunVodPlayerView.setTvSpeedValue("1.5x");
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
                } else if (checkedId == R.id.rb_speed_once_three) {
                    Log.d("LF123", "切换速度为1.75");
                    mAliyunVodPlayerView.setTvSpeedValue("1.75x");
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuarters);
//                } else if (checkedId == R.id.rb_speed_twice) {
//                    Log.d("LF123", "切换速度为2.0");
//                    mAliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
                }
            }
        });
        //像素切换
        showMoreView.setOnPictureQualityChangedListener(new ShowMoreView.OnPictureQualityChangedListener() {
            @Override
            public void onQualityChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_pixel_slow) {
                    FixedToastUtils.show(LandscapeVideoActivity.this, "切换像素为270p,功能开发中, 敬请期待...");
                } else if (checkedId == R.id.rb_pixel_normal) {
                    FixedToastUtils.show(LandscapeVideoActivity.this, "切换像素为480p,功能开发中, 敬请期待...");
                } else if (checkedId == R.id.rb_pixel_hight) {
                    FixedToastUtils.show(LandscapeVideoActivity.this, "切换像素为720p,功能开发中, 敬请期待...");
                } else if (checkedId == R.id.rb_pixel_hightseter) {
                    FixedToastUtils.show(LandscapeVideoActivity.this, "切换像素为720p,功能开发中, 敬请期待...");
                }
            }
        });
        /**
         * 初始化亮度
         */
        if (mAliyunVodPlayerView != null) {
            showMoreView.setBrightness(mAliyunVodPlayerView.getScreenBrightness());
        }
        // 亮度seek
        showMoreView.setOnLightSeekChangeListener(new ShowMoreView.OnLightSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                setWindowBrightness(progress);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setScreenBrightness(progress);
                }
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        /**
         * 初始化音量
         */
        if (mAliyunVodPlayerView != null) {
            showMoreView.setVoiceVolume(mAliyunVodPlayerView.getCurrentVolume());
        }

        showMoreView.setOnVoiceSeekChangeListener(new ShowMoreView.OnVoiceSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                mAliyunVodPlayerView.setCurrentVolume(progress / 100.00f);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        showMoreView.setmOnStudyStatusChangeListener(new ShowMoreView.OnStudyStatusChangeListener() {
            @Override
            public void OnStudyStatusChange(boolean status) {
                isStudyShow = status;
                if (status) {
                    ToastUtil.showTextToas(mContext, getResources().getString(R.string.study_status_open));
                } else {
                    ToastUtil.showTextToas(mContext, getResources().getString(R.string.study_status_close));
                }
                mAliyunVodPlayerView.setStudyViewShow(status);
                mAliyunVodPlayerView.isShowPoint(status);
            }
        });

    }

    /**
     * 记笔记
     *
     * @return
     */

    public void takeNote() {
        UserInfoBean userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfo != null) {
            initTakeNoteDialog();
            mAliyunVodPlayerView.pause();
            mPresenter.getSelectDomNote(useId, 3, id);
        } else {
            mAliyunVodPlayerView.pause();
            showLoginTipsDialog();
        }

    }

    /**
     * 提醒按钮
     */
    public void onHint() {
        UserInfoBean userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfo != null) {
            initTipsDialog();
            mAliyunVodPlayerView.pause();
            mPresenter.getTipsList(id, 0, useId);
        } else {
            mAliyunVodPlayerView.pause();
            showLoginTipsDialog();
        }

    }


    /**
     * 题目练习按钮
     */
    public void onExercise() {
        UserInfoBean userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfo != null) {
            initExercise();
            mAliyunVodPlayerView.pause();
            mPresenter.getExerciseList(id, 2, useId);
        } else {
            mAliyunVodPlayerView.pause();
            showLoginTipsDialog();
        }

    }

    /**
     * 术语按钮
     */
    public void onTerminology() {
        UserInfoBean userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfo != null) {
            initTerminology();
            mAliyunVodPlayerView.pause();
            mPresenter.getTerminologyList(id, 1, useId);
        } else {
            mAliyunVodPlayerView.pause();
            showLoginTipsDialog();
        }

    }

    private boolean ischeck;

    /**
     * 设置屏幕亮度
     *
     * @param brightness
     */
    public void setBrightness(int brightness) {
        setWindowBrightness(brightness);
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.setScreenBrightness(brightness);
        }
    }

    /**
     * 设置屏幕亮度
     */
    private void setWindowBrightness(int brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }

    /**
     * 倍速切换按钮
     */
    public void onSpeedChange() {
        AliyunShowMoreValue moreValue;
        moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(mAliyunVodPlayerView.getCurrentSpeed());
        moreValue.setVolume((int) mAliyunVodPlayerView.getCurrentVolume());
        speedValueDialog.setMoreValue(moreValue);
        speedValueDialog.show(getSupportFragmentManager());
        speedValueDialog.setSpeedChangeLisener(new SpeedValueDialog.OnSpeedChangeLisener() {
            @Override
            public void OnSpeedChange(int i) {
                switch (i) {
                    case 0:
                        mAliyunVodPlayerView.changeSpeed(SpeedValue.OneSlow);
                        mAliyunVodPlayerView.setTvSpeedValue("0.75x");
                        break;
                    case 1:
                        mAliyunVodPlayerView.changeSpeed(SpeedValue.One);
                        mAliyunVodPlayerView.setTvSpeedValue("1.0x");
                        break;
                    case 2:
                        mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
                        mAliyunVodPlayerView.setTvSpeedValue("1.25x");
                        break;
                    case 3:
                        mAliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
                        mAliyunVodPlayerView.setTvSpeedValue("1.5x");
                        break;
                    case 4:
                        mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuarters);
                        mAliyunVodPlayerView.setTvSpeedValue("1.75x");
                        break;
                }
            }
        });

    }

    public void initSpeedDialog() {
        speedValueDialog = SpeedValueDialog.newInstance();
        speedValueDialog.setOutCancel(true)
                .setAnimStyle(R.style.dialogRightAnim)
                .setSize((int) (getScreenWidth(this) * 0.2), getScreenHeight(this))
                .setGravity(Gravity.RIGHT);
    }


    /**
     * 学习模式切换按钮
     *
     * @param b
     */
    @SuppressLint("ResourceType")
    public void onStudyChange(boolean b) {
        if (b) {
            ToastUtil.showTextToas(this, getResources().getString(R.string.study_status_open));
        } else {
            ToastUtil.showTextToas(this, getResources().getString(R.string.study_status_close));
        }
        isStudyShow = b;
        mAliyunVodPlayerView.isShowPoint(b);
        mAliyunVodPlayerView.setStudyViewShow(b);
    }


    /**
     * 播放本地资源
     *
     * @param url
     * @param title
     */
    private void changePlayLocalSource(String url, String title) {
        UrlSource urlSource = new UrlSource();
        urlSource.setUri(url);
        urlSource.setTitle(title);
        mAliyunVodPlayerView.setLocalSource(urlSource);
        mAliyunVodPlayerView.setVideoTitle(title);
    }

    private Dialog downloadDialog = null;

    /**
     * 获取url的scheme
     *
     * @param url
     * @return
     */
    private String getUrlScheme(String url) {
        return Uri.parse(url).getScheme();
    }


    /**
     * 播放状态切换
     *
     * @param playerState
     */
    public void onPlayStateSwitch(int playerState) {
        if (playerState == IPlayer.started) {

        } else if (playerState == IPlayer.paused) {

        }

    }


    public void onSeekComplete() {
        //tvLogs.append(format.format(new Date()) + getString(R.string.log_seek_completed) + "\n");
    }


    public void onSeekStart(int position) {
        Log.d("LF123", "onSeekStart: " + position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePlayerViewMode();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
        }
        enterTime = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TTSUtils.getInstance().pause();
        //观看时长
        if (!isPlayDownload) {
            long exitTime = System.currentTimeMillis();
            int currentPosition = (int) (mAliyunVodPlayerView.getCurrentPosition() / 1000);
            int learnTime = (int) ((exitTime - enterTime) / 1000);
            mPresenter.updateLearnTime(id, currentPosition, learnTime);
            int floor = (int) Math.floor((mAliyunVodPlayerView.getCurrentPosition()) / 1000);
            int floor1 = (int) Math.floor((mAliyunVodPlayerView.getDuration()) / 1000);
            if (!TextUtils.isEmpty(videoId)) {
                if ((int) Math.floor((mAliyunVodPlayerView.getCurrentPosition()) / 1000) == (int) Math.floor((mAliyunVodPlayerView.getDuration()) / 1000)) {
                    VideoPlayHistoryDBDao.getInstence().addPlayHistory(videoId, 0);

                } else {
                    VideoPlayHistoryDBDao.getInstence().addPlayHistory(videoId, mAliyunVodPlayerView.getCurrentPosition());

                }

            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        TTSUtils.getInstance().stop();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }

    @Override
    protected void onDestroy() {

        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
            mAliyunVodPlayerView = null;
        }

        if (playerHandler != null) {
            playerHandler.removeMessages(DOWNLOAD_ERROR);
            playerHandler = null;
        }

        if (commenUtils != null) {
            commenUtils.onDestroy();
            commenUtils = null;
        }
        TTSUtils.getInstance().release();
        DialogUtil.getInstance().release();
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAliyunVodPlayerView != null) {
            boolean handler = mAliyunVodPlayerView.onKeyDown(keyCode, event);
            if (!handler) {
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //解决某些手机上锁屏之后会出现标题栏的问题。
        updatePlayerViewMode();
    }

    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//                //转为竖屏了。
//                //显示状态栏
//                //                if (!isStrangePhone()) {
//                //                    getSupportActionBar().show();
//                //                }
//
//                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//
//                //设置view的布局，宽高之类
//                RelativeLayout.LayoutParams aliVcVideoViewLayoutParams = (RelativeLayout.LayoutParams) mAliyunVodPlayerView
//                        .getLayoutParams();
//                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
//                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                //                if (!isStrangePhone()) {
//                //                    aliVcVideoViewLayoutParams.topMargin = getSupportActionBar().getHeight();
//                //                }

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }

                //设置view的布局，宽高
                RelativeLayout.LayoutParams aliVcVideoViewLayoutParams = (RelativeLayout.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //                if (!isStrangePhone()) {
                //                    aliVcVideoViewLayoutParams.topMargin = 0;
                //                }
            }

        }
    }


    protected boolean isStrangePhone() {
        boolean strangePhone = "mx5".equalsIgnoreCase(Build.DEVICE)
                || "Redmi Note2".equalsIgnoreCase(Build.DEVICE)
                || "Z00A_1".equalsIgnoreCase(Build.DEVICE)
                || "hwH60-L02".equalsIgnoreCase(Build.DEVICE)
                || "hermes".equalsIgnoreCase(Build.DEVICE)
                || ("V4".equalsIgnoreCase(Build.DEVICE) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER))
                || ("m1metal".equalsIgnoreCase(Build.DEVICE) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public void addData() {
        //生成知识点解析
        DataGenerator.getInstance().generatorAnalysis();
        //生成题目
        DataGenerator.getInstance().generatorExercise();
        //生成笔记
        DataGenerator.getInstance().generatorNotes();
        // 生成提示
        DataGenerator.getInstance().generatorTips();
    }

    /**
     * 设置九宫图的加载方式
     */
    private void setNineGlideloading() {
        NineGridView.setImageLoader(new GlideImageLoader());
    }

    //使用Glide去加载图片
    private class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, final ImageView imageView, String url) {
            Glide.with(context)
                    .asBitmap()
                    .load(url)//
                    .placeholder(R.drawable.ic_default_color)//
                    .error(R.drawable.ic_default_color)//
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }

    }

    /**
     * 对话框关闭了，判断是否需要进行seek到对应位置
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDialogDismiss(DialogDissmissEvent event) {
        if (event.isDismiss()) {
            DialogShowDao.getInstance().addDialogshow(false);
            TTSUtils.getInstance().stop();
//            if (BasisConstants.EXERCISE_TYPE != event.getType()) {
                if (mAliyunVodPlayerView != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAliyunVodPlayerView.start();
                        }
                    }, 500);
                }
//            } else {
//                if (mAliyunVodPlayerView != null) {
//                    mAliyunVodPlayerView.setPlayBtnCanClick(false);
//                }
//            }


        }
    }

    /**
     * 判断是否有网络的监听
     */
    private class MyNetConnectedListener implements AliyunVodPlayerView.NetConnectedListener {
        WeakReference<LandscapeVideoActivity> weakReference;

        public MyNetConnectedListener(LandscapeVideoActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onReNetConnected(boolean isReconnect) {
            LandscapeVideoActivity activity = weakReference.get();
            if (activity != null) {
                activity.onReNetConnected(isReconnect);
            }
        }

        @Override
        public void onNetUnConnected() {
            LandscapeVideoActivity activity = weakReference.get();
            if (activity != null) {
                activity.onNetUnConnected();
            }
        }
    }

    List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfoList = new ArrayList<>();

    public void onNetUnConnected() {
        currentError = ErrorInfo.UnConnectInternet;
        if (aliyunDownloadMediaInfoList != null && aliyunDownloadMediaInfoList.size() > 0) {
            ConcurrentLinkedQueue<AliyunDownloadMediaInfo> allDownloadMediaInfo = new ConcurrentLinkedQueue<>();
            List<AliyunDownloadMediaInfo> mediaInfos = downloadDataProvider.getAllDownloadMediaInfo();
            allDownloadMediaInfo.addAll(mediaInfos);
            downloadManager.stopDownloads(allDownloadMediaInfo);
        }
    }

    public void onReNetConnected(boolean isReconnect) {
        currentError = ErrorInfo.Normal;
        if (isReconnect) {
            if (aliyunDownloadMediaInfoList != null && aliyunDownloadMediaInfoList.size() > 0) {
                int unCompleteDownload = 0;
                for (AliyunDownloadMediaInfo info : aliyunDownloadMediaInfoList) {
                    if (info.getStatus() == AliyunDownloadMediaInfo.Status.Stop) {
                        unCompleteDownload++;
                    }
                }

                if (unCompleteDownload > 0) {
                    FixedToastUtils.show(this, "网络恢复, 请手动开启下载任务...");
                }
            }
            // 如果当前播放列表为空, 网络重连后需要重新请求sts和播放列表, 其他情况不需要
            onAuthSuccess(videoId, playAuth);
        }
    }

    private class MyPlayViewClickListener implements AliyunVodPlayerView.OnPlayerViewClickListener {

        private WeakReference<LandscapeVideoActivity> weakReference;

        public MyPlayViewClickListener(LandscapeVideoActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onClick(AliyunScreenMode screenMode, AliyunVodPlayerView.PlayViewType viewType) {
            UserInfoBean userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
            if (userInfo == null) {
                showLoginTipsDialog();
                return;
            }
            if (userInfo.getIsMember() != 1) {
                showMemberPromptDialog();
                return;
            }
            long currentClickTime = System.currentTimeMillis();
            // 防止快速点击
            if (currentClickTime - oldTime <= 1000) {
                return;
            }
            oldTime = currentClickTime;
            // 如果当前的Type是Download, 就显示Download对话框
            if (viewType == AliyunVodPlayerView.PlayViewType.Download) {
                mCurrentDownloadScreenMode = screenMode;
                if (downloadManager != null) {
                    if (page > 1) {
                        page = 1;
                    }
                    mPresenter.getVideoInCourse(downloadManager, chapterId, sectionId, gradeId, page, 10);

                } else {
                    toast("视频下载失败，请稍后再试");
                }


            }
        }
    }

    private class MyOnRePlayClickListener implements AliyunVodPlayerView.OnReplayListener {
        private WeakReference<LandscapeVideoActivity> weakReference;

        public MyOnRePlayClickListener(LandscapeVideoActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onReplay() {
            if (isPlayDownload) {
                if (url != null && title != null) {
                    mAliyunVodPlayerView.updateScreenShow();
                    changePlayLocalSource(url, title);
                    isPlayDownload = true;
                }
            } else {
                isReplay = true;
                isFlag = true;
                mPresenter.getPlayAuth(videoId);
            }


        }
    }

    /**
     * 下载监听
     */
    private class MyDownloadInfoListener implements AliyunDownloadInfoListener {

        private WeakReference<LandscapeVideoActivity> weakReference;

        public MyDownloadInfoListener(LandscapeVideoActivity aliyunPlayerSkinActivity) {
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
            LandscapeVideoActivity landscapeVideoActivity = weakReference.get();
            if (landscapeVideoActivity != null) {
                landscapeVideoActivity.mDownloadInPrepare = false;
                landscapeVideoActivity.onDownloadPrepared(infos, landscapeVideoActivity.showAddDownloadView);
            }
        }

        @Override
        public void onAdd(AliyunDownloadMediaInfo info) {
            LandscapeVideoActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                if (aliyunPlayerSkinActivity.downloadDataProvider != null) {
                    aliyunPlayerSkinActivity.downloadDataProvider.addDownloadMediaInfo(info);
                }
            }
        }

        @Override
        public void onStart(AliyunDownloadMediaInfo info) {
//            AliyunPlayerSkinActivity aliyunPlayerSkinActivity = weakReference.get();
//            if (aliyunPlayerSkinActivity != null) {
//                if (aliyunPlayerSkinActivity.dialogDownloadView != null) {
//                    aliyunPlayerSkinActivity.dialogDownloadView.updateInfo(info);
//                }
//                if (aliyunPlayerSkinActivity.downloadView != null) {
//                    aliyunPlayerSkinActivity.downloadView.updateInfo(info);
//                }
//
//            }
            if (mAliyunVodPlayerView != null) {
                mAliyunVodPlayerView.showDownloadingTag(true);
            }
        }

        @Override
        public void onProgress(AliyunDownloadMediaInfo info, int percent) {
//            AliyunPlayerSkinActivity aliyunPlayerSkinActivity = weakReference.get();
//            if (aliyunPlayerSkinActivity != null) {
//                if (aliyunPlayerSkinActivity.dialogDownloadView != null) {
//                    aliyunPlayerSkinActivity.dialogDownloadView.updateInfo(info);
//                }
//                if (aliyunPlayerSkinActivity.downloadView != null) {
//                    aliyunPlayerSkinActivity.downloadView.updateInfo(info);
//                }
//            }
            if (mAliyunVodPlayerView != null) {
                if (percent == 100) {
                    mAliyunVodPlayerView.showDownloadingTag(false);

                } else {
                    mAliyunVodPlayerView.showDownloadingTag(true);

                }
            }
        }

        @Override
        public void onStop(AliyunDownloadMediaInfo info) {
            LandscapeVideoActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                if (aliyunPlayerSkinActivity.dialogDownloadView != null) {
                    aliyunPlayerSkinActivity.dialogDownloadView.updateInfo(info);
                }

            }
        }

        @Override
        public void onCompletion(AliyunDownloadMediaInfo info) {
            LandscapeVideoActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                synchronized (aliyunPlayerSkinActivity) {

                    if (mVideoListDialog != null && currentSelectVideo != null) {
                        currentSelectVideo.setDownload_status(2);
                        mVideoListDialog.updateViewByDownloadingVideo(currentSelectVideo);
                    }
                    if (aliyunPlayerSkinActivity.dialogDownloadView != null) {
                        aliyunPlayerSkinActivity.dialogDownloadView.updateInfoByComplete(info);
                    }

                    if (aliyunPlayerSkinActivity.downloadDataProvider != null) {
                        aliyunPlayerSkinActivity.downloadDataProvider.addDownloadMediaInfo(info);
                    }
                }
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.showDownloadingTag(false);
                }
            }
        }

        @Override
        public void onError(AliyunDownloadMediaInfo info, ErrorCode code, String msg, String requestId) {
            toast(msg);
            LandscapeVideoActivity aliyunPlayerSkinActivity = weakReference.get();
            if (aliyunPlayerSkinActivity != null) {
                aliyunPlayerSkinActivity.mDownloadInPrepare = false;


                if (aliyunPlayerSkinActivity.dialogDownloadView != null) {
                    aliyunPlayerSkinActivity.dialogDownloadView.updateInfoByError(info);
                }

                //鉴权过期
                if (code.getValue() == ErrorCode.ERROR_SERVER_POP_UNKNOWN.getValue()) {
                    mPresenter.refreshDownloadVidAuth(info.getVid(), info);
                    aliyunPlayerSkinActivity.refreshDownloadVidAuth(info);

                }
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(DOWNLOAD_ERROR_KEY, msg);
                message.setData(bundle);
                message.what = DOWNLOAD_ERROR;
                aliyunPlayerSkinActivity.playerHandler = new PlayerHandler(aliyunPlayerSkinActivity);
                aliyunPlayerSkinActivity.playerHandler.sendMessage(message);
            }
        }

        @Override
        public void onWait(AliyunDownloadMediaInfo info) {
            //            mPlayerDownloadAdapter.updateData(info);
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

    private void onDownloadPrepared(List<AliyunDownloadMediaInfo> infos, boolean showAddDownloadView) {
        onDownload(infos.get(0));
        currentPreparedMediaInfo = new ArrayList<>();
        currentPreparedMediaInfo.addAll(infos);
//        if (showAddDownloadView) {
//            showAddDownloadView(mCurrentDownloadScreenMode);
//        }

    }

    public void onDownload(AliyunDownloadMediaInfo info) {
        if (downloadDialog != null) {
            downloadDialog.dismiss();
        }
        aliyunDownloadMediaInfo = info;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permission = ContextCompat.checkSelfPermission(LandscapeVideoActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(LandscapeVideoActivity.this, PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);

            } else {
                addNewInfo(info);
            }
        } else {
            addNewInfo(info);
        }

    }

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
     * 显示下载选择项 download 对话框
     *
     * @param screenMode
     */
    private void showAddDownloadView(AliyunScreenMode screenMode) {
        //这个时候视频的状态已经是delete了
        if (currentPreparedMediaInfo != null && currentPreparedMediaInfo.get(0).getVid().equals(preparedVid)) {
            downloadDialog = new DownloadChoiceDialog(this, screenMode);
            final AddDownloadView contentView = new AddDownloadView(this, screenMode);
            contentView.onPrepared(currentPreparedMediaInfo);
            contentView.setOnViewClickListener(viewClickListener);
            final View inflate = LayoutInflater.from(getApplicationContext()).inflate(
                    R.layout.alivc_dialog_download_video, null, false);
            dialogDownloadView = inflate.findViewById(R.id.download_view);
            downloadDialog.setContentView(contentView);
            downloadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if (dialogDownloadView != null) {
                        dialogDownloadView.setOnDownloadViewListener(null);
                        dialogDownloadView.setOnDownloadedItemClickListener(null);
                    }
                }
            });
            if (!downloadDialog.isShowing()) {
                downloadDialog.show();
            }
            downloadDialog.setCanceledOnTouchOutside(true);

//            if (screenMode == AliyunScreenMode.Full) {
//                contentView.setOnShowVideoListLisener(new AddDownloadView.OnShowNativeVideoBtnClickListener() {
//                    @Override
//                    public void onShowVideo() {
//                        if (downloadDataProvider != null) {
//                            downloadDataProvider.restoreMediaInfo(new LoadDbDatasListener() {
//                                @Override
//                                public void onLoadSuccess(List<AliyunDownloadMediaInfo> dataList) {
//                                    if (dialogDownloadView != null) {
//                                        dialogDownloadView.addAllDownloadMediaInfo(dataList);
//                                    }
//                                }
//                            });
//                        }
//                        downloadDialog.setContentView(inflate);
//                    }
//                });
//
//                dialogDownloadView.setOnDownloadViewListener(new DownloadView.OnDownloadViewListener() {
//                    @Override
//                    public void onStop(AliyunDownloadMediaInfo downloadMediaInfo) {
//                        downloadManager.stopDownload(downloadMediaInfo);
//                    }
//
//                    @Override
//                    public void onStart(AliyunDownloadMediaInfo downloadMediaInfo) {
//                        downloadManager.startDownload(downloadMediaInfo);
//                    }
//
//                    @Override
//                    public void onDeleteDownloadInfo(final ArrayList<AlivcDownloadMediaInfo> alivcDownloadMediaInfos) {
//                        // 视频删除的dialog
//                        final AlivcDialog alivcDialog = new AlivcDialog(LandscapeVideoActivity.this);
//                        alivcDialog.setDialogIcon(R.drawable.icon_delete_tips);
//                        alivcDialog.setMessage(getResources().getString(R.string.alivc_delete_confirm));
//                        alivcDialog.setOnConfirmclickListener(getResources().getString(R.string.alivc_dialog_sure),
//                                new AlivcDialog.onConfirmClickListener() {
//                                    @Override
//                                    public void onConfirm() {
//                                        alivcDialog.dismiss();
//                                        if (alivcDownloadMediaInfos != null && alivcDownloadMediaInfos.size() > 0) {
//                                            dialogDownloadView.deleteDownloadInfo();
//                                            if (downloadView != null) {
//                                                for (AlivcDownloadMediaInfo alivcDownloadMediaInfo : alivcDownloadMediaInfos) {
//                                                    if (alivcDownloadMediaInfo.isCheckedState()) {
//                                                        downloadView.deleteDownloadInfo(alivcDownloadMediaInfo.getAliyunDownloadMediaInfo());
//                                                    }
//                                                }
//
//                                            }
//                                            if (downloadManager != null) {
//                                                for (AlivcDownloadMediaInfo alivcDownloadMediaInfo : alivcDownloadMediaInfos) {
//                                                    downloadManager.deleteFile(alivcDownloadMediaInfo.getAliyunDownloadMediaInfo());
//                                                }
//
//                                            }
//                                            downloadDataProvider.deleteAllDownloadInfo(alivcDownloadMediaInfos);
//                                        } else {
//                                            FixedToastUtils.show(LandscapeVideoActivity.this, "没有删除的视频选项...");
//                                        }
//                                    }
//                                });
//                        alivcDialog.setOnCancelOnclickListener(getResources().getString(R.string.alivc_dialog_cancle),
//                                new AlivcDialog.onCancelOnclickListener() {
//                                    @Override
//                                    public void onCancel() {
//                                        alivcDialog.dismiss();
//                                    }
//                                });
//                        alivcDialog.show();
//                    }
//                });
//
//                dialogDownloadView.setOnDownloadedItemClickListener(new DownloadView.OnDownloadItemClickListener() {
//                    @Override
//                    public void onDownloadedItemClick(final int positin) {
//                        ArrayList<AlivcDownloadMediaInfo> allDownloadMediaInfo = dialogDownloadView.getAllDownloadMediaInfo();
//                        List<AliyunDownloadMediaInfo> dataList = new ArrayList<>();
//                        for (AlivcDownloadMediaInfo alivcDownloadMediaInfo : allDownloadMediaInfo) {
//                            dataList.add(alivcDownloadMediaInfo.getAliyunDownloadMediaInfo());
//                        }
////                List<AliyunDownloadMediaInfo> dataList = downloadDataProvider.getAllDownloadMediaInfo();
//                        // 存入顺序和显示顺序相反,  所以进行倒序
//                        ArrayList<AliyunDownloadMediaInfo> tempList = new ArrayList<>();
//                        int size = dataList.size();
//                        for (AliyunDownloadMediaInfo aliyunDownloadMediaInfo : dataList) {
//                            if (aliyunDownloadMediaInfo.getProgress() == 100) {
//                                tempList.add(aliyunDownloadMediaInfo);
//                            }
//                        }
//
//                        Collections.reverse(tempList);
//                        if ((dataList.size() - 1) < 0 || (dataList.size() - 1) > tempList.size()) {
//                            return;
//                        }
//                        tempList.add(dataList.get(dataList.size() - 1));
//                        for (int i = 0; i < size; i++) {
//                            AliyunDownloadMediaInfo aliyunDownloadMediaInfo = dataList.get(i);
//                            if (!tempList.contains(aliyunDownloadMediaInfo)) {
//                                tempList.add(aliyunDownloadMediaInfo);
//                            }
//                        }
//
//                        if (positin < 0) {
//                            FixedToastUtils.show(LandscapeVideoActivity.this, "视频资源不存在");
//                            return;
//                        }
//
//                        // 如果点击列表中的视频, 需要将类型改为vid
//                        AliyunDownloadMediaInfo aliyunDownloadMediaInfo = tempList.get(positin);
//                        PlayParameter.PLAY_PARAM_TYPE = "localSource";
//                        if (aliyunDownloadMediaInfo != null) {
//                            PlayParameter.PLAY_PARAM_URL = aliyunDownloadMediaInfo.getSavePath();
//                            mAliyunVodPlayerView.updateScreenShow();
//                            changePlayLocalSource(PlayParameter.PLAY_PARAM_URL, aliyunDownloadMediaInfo.getTitle());
//                        }
//                    }
//
//                    @Override
//                    public void onDownloadingItemClick(ArrayList<AlivcDownloadMediaInfo> infos, int position) {
//                        AlivcDownloadMediaInfo alivcInfo = infos.get(position);
//                        AliyunDownloadMediaInfo aliyunDownloadInfo = alivcInfo.getAliyunDownloadMediaInfo();
//                        AliyunDownloadMediaInfo.Status status = aliyunDownloadInfo.getStatus();
//                        if (status == AliyunDownloadMediaInfo.Status.Error || status == AliyunDownloadMediaInfo.Status.Wait) {
//                            //downloadManager.removeDownloadMedia(aliyunDownloadInfo);
//                            downloadManager.startDownload(aliyunDownloadInfo);
//
//                        }
//                    }
//
//                });
//            }
        }
    }

    public void showLoginTipsDialog() {
        DialogUtil.getInstance().getToLogin(this, new OnLoginDialogListenr() {

            @Override
            public void onBeginRequest() {
                showLoading();
            }

            @Override
            public void onChooseSchool() {
                showChooseSchoolDialog();
            }

            @Override
            public void onLosePassword() {
                showLosePasswordDialog();
            }

            @Override
            public void onLoginSuccess(Dialog loginDialog) {
                hideLoading();
                mPresenter.getUserInfo();
                loginDialog.dismiss();
            }

            @Override
            public void onLoginError() {
                hideLoading();
                ToastUtil.show(BaseApplication.getContext(), "当前登录失败");
            }
        });
    }

    /**
     * 需要打开选择学校的对话框
     */
    public void showChooseSchoolDialog() {
        DialogUtil.getInstance().getToChooseSchoolDialog(this, new OnChooseSchoolDialogListener() {
            @Override
            public void onLoginDialogShow() {
//                showLoginDialog();
            }

            @Override
            public void onNotFindSchool() {
                notFindSchool();
            }
        });
    }

    /**
     * 没有学校的对话框
     */
    public void notFindSchool() {
        DialogUtil.getInstance().getToNotFindSchool(this);
    }

    /**
     * 打开忘记密码的对框框
     */
    public void showLosePasswordDialog() {
        DialogUtil.getInstance().getToLosePassWordTips(this);
    }

    /**
     * 需要打开登陆对话框
     */
    public void showLoginDialog() {
        DialogUtil.getInstance().getToLogin(this, new OnLoginDialogListenr() {

            @Override
            public void onBeginRequest() {
                showLoading();
            }

            @Override
            public void onChooseSchool() {
                showChooseSchoolDialog();
            }

            @Override
            public void onLosePassword() {
                showLosePasswordDialog();
            }

            @Override
            public void onLoginSuccess(Dialog loginDialog) {
                hideLoading();
                mPresenter.getUserInfo();
                loginDialog.dismiss();
            }

            @Override
            public void onLoginError() {
                hideLoading();
                ToastUtil.show(BaseApplication.getContext(), "当前登录失败");
            }
        });
    }

    /**
     * 非会员的时候弹出登陆提示
     */
    private void showMemberPromptDialog() {
        DialogUtil.getInstance().getToMemberTip(this, new GetToMemberTipDialogListner() {
            @Override
            public void onGotologin() {
                showLoginDialog();
            }
        });
    }

}
