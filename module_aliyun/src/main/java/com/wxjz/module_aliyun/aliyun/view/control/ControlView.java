package com.wxjz.module_aliyun.aliyun.view.control;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.utils.VcPlayerLog;
import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.constants.PlayParameter;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.aliyun.theme.ITheme;
import com.wxjz.module_aliyun.aliyun.utils.TimeFormater;
import com.wxjz.module_aliyun.aliyun.view.interfaces.ViewAction;
import com.wxjz.module_aliyun.aliyun.view.quality.QualityItem;
import com.wxjz.module_aliyun.aliyun.widget.AliyunScreenMode;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;
import com.wxjz.module_aliyun.popwindow.CustomPopWindow;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.db.bean.BaseBean;
import com.wxjz.module_aliyun.aliyun.MutiPointSeekBar;
import com.wxjz.module_base.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

/**
 * 控制条界面。包括了顶部的标题栏，底部 的控制栏，锁屏按钮等等。是界面的主要组成部分。
 */

public class ControlView extends RelativeLayout implements ViewAction, ITheme {

    private static final String TAG = ControlView.class.getSimpleName();

    /**
     * 判断是否获取到了点位信息
     */
    private boolean isStudyShow;

    //标题，控制条单独控制是否可显示
    private boolean mTitleBarCanShow = true;
    private boolean mControlBarCanShow = true;
    private View mTitleBar;
    private View mControlBar;

    //这些是大小屏都有的==========START========
    //返回按钮
    private ImageView mTitlebarBackBtn;
    //标题
    private TextView mTitlebarText;
    //视频播放状态
    private PlayState mPlayState = PlayState.NotPlaying;
    //播放按钮
    private ImageView mPlayStateBtn;

    //下载
    private ImageView mTitleDownload;

    //锁定屏幕方向相关
    // 屏幕方向是否锁定
    private boolean mScreenLocked = false;
    //锁屏按钮
    private ImageView mScreenLockBtn;


    //切换大小屏相关
    private AliyunScreenMode mAliyunScreenMode = AliyunScreenMode.Full;
    //全屏/小屏按钮
    private ImageView mScreenModeBtn;

    //大小屏公用的信息
    //视频信息，info显示用。
    private MediaInfo mAliyunMediaInfo;
    //播放的进度
    private int mVideoPosition = 0;
    //seekbar拖动状态
    private boolean isSeekbarTouching = false;
    //视频缓冲进度
    private int mVideoBufferPosition;
    //这些是大小屏都有的==========END========


    //这些是大屏时显示的
    //大屏的底部控制栏
    private View mLargeInfoBar;
    //当前位置文字
    private TextView mLargePositionText;
    //时长文字
    private TextView mLargeDurationText;
    //进度条
    private MutiPointSeekBar mLargeSeekbar;
    //当前的清晰度
    private String mCurrentQuality;
    //是否固定清晰度
    private boolean mForceQuality = false;
    //改变清晰度的按钮
    private Button mLargeChangeQualityBtn;
    //更多弹窗按钮
    private ImageView mTitleMore;
    //这些是小屏时显示的
    //底部控制栏
    private View mSmallInfoBar;
    //当前位置文字
    private TextView mSmallPositionText;
    //时长文字
    private TextView mSmallDurationText;
    //seek进度条
    private SeekBar mSmallSeekbar;


    //整个view的显示控制：
    //不显示的原因。如果是错误的，那么view就都不显示了。
    private HideType mHideType = null;

    //saas,还是mts资源,清晰度的显示不一样
    private boolean isMtsSource;

    //各种监听
    // 进度拖动监听
    private OnSeekListener mOnSeekListener;
    //菜单点击监听
    private OnMenuClickListener mOnMenuClickListener;
    //下载点击监听
    private OnDownloadClickListener onDownloadClickListener;
    //标题返回按钮监听
    private OnBackClickListener mOnBackClickListener;
    //播放按钮点击监听
    private OnPlayStateClickListener mOnPlayStateClickListener;
    //清晰度按钮点击监听
    private OnQualityBtnClickListener mOnQualityBtnClickListener;
    //锁屏按钮点击监听
    private OnScreenLockClickListener mOnScreenLockClickListener;
    //大小屏按钮点击监听
    private OnScreenModeClickListener mOnScreenModeClickListener;
    // 显示更多
    private OnShowMoreClickListener mOnShowMoreClickListener;
    //笔记点击监听
    public OnNoteClicklistener onNoteClicklistener;
    //提示点击监听
    public OnHintClickListener onHintClickListener;
    //问题点击监听
    public OnExerciseClickListener onExerciseClickListener;
    //倍速切换按钮
    public OnSpeedChangeClickLitener onSpeedChangeClickLitener;

    //屏幕截图
    private OnScreenShotClickListener mOnScreenShotClickListener;
    //录制
    private OnScreenRecoderClickListener mOnScreenRecoderClickListener;

    //控制是否是学习模式
    private OnStudyStatusListener mOnStudyStatusListener;

    private ImageView mScreenShot;
    private ImageView mScreenRecorder;
    private List<PointListBean.PointBean> points = new ArrayList<>();

    private OnSectionsClickListener onSectionsClickListener;
    //笔记
    private ImageView mIvNote;
    //提示
    private ImageView mIvHint;
    //题目
    private ImageView mIvExercise;
    //术语
    private ImageView mIvAnalysis;

    private LinearLayout llRight;
    //速度切换按钮
    private TextView mTvSpeedValue;
    //控制学习模式按钮
    private ImageView mIvStudyStatus;

    private TextView mTvSetData;
    private Context mContext;

    public OnPopWindowListener onPopWindowListener;
    private ImageView ivDownloadingTag;

    public ControlView(Context context) {
        super(context);
        init();
        mContext = context;
    }

    public ControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mContext = context;
    }

    public ControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        mContext = context;
    }

    private void init() {
        //Inflate布局
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_view_control, this, true);
        findAllViews(); //找到所有的view

        setViewListener(); //设置view的监听事件

        updateAllViews(); //更新view的显示
    }

    private void findAllViews() {
        mTitleBar = findViewById(R.id.titlebar);
        mControlBar = findViewById(R.id.controlbar);
        ivDownloadingTag = findViewById(R.id.iv_downloading_tag);

        mTitlebarBackBtn = (ImageView) findViewById(R.id.alivc_title_back);
        mTitlebarText = (TextView) findViewById(R.id.alivc_title_title);
        mTitleDownload = (ImageView) findViewById(R.id.alivc_title_download);
        mTitleMore = findViewById(R.id.alivc_title_more);
        mScreenModeBtn = (ImageView) findViewById(R.id.alivc_screen_mode);
        mScreenLockBtn = (ImageView) findViewById(R.id.alivc_screen_lock);
        mPlayStateBtn = (ImageView) findViewById(R.id.alivc_player_state);
        mScreenShot = findViewById(R.id.alivc_screen_shot);
        mScreenRecorder = findViewById(R.id.alivc_screen_recoder);
        mLargeInfoBar = findViewById(R.id.alivc_info_large_bar);
        mLargePositionText = (TextView) findViewById(R.id.alivc_info_large_position);
        mLargeDurationText = (TextView) findViewById(R.id.alivc_info_large_duration);
        mLargeSeekbar = (MutiPointSeekBar) findViewById(R.id.alivc_info_large_seekbar);
        mLargeChangeQualityBtn = (Button) findViewById(R.id.alivc_info_large_rate_btn);


        mSmallInfoBar = findViewById(R.id.alivc_info_small_bar);
        mSmallPositionText = (TextView) findViewById(R.id.alivc_info_small_position);
        mSmallDurationText = (TextView) findViewById(R.id.alivc_info_small_duration);
        mSmallSeekbar = (SeekBar) findViewById(R.id.alivc_info_small_seekbar);
        //右边新加的标签
        llRight = findViewById(R.id.llRight);
        mIvNote = findViewById(R.id.ivNote);
        mIvHint = findViewById(R.id.ivHint);
        mIvExercise = findViewById(R.id.ivExercise);
        mIvAnalysis = findViewById(R.id.ivAnalysis);
        //速度切换按钮
        mTvSpeedValue = findViewById(R.id.tv_speed_value);
        //学习模式切换按钮
        mIvStudyStatus = findViewById(R.id.iv_exercise_status);
        //下载按钮的框架
    }

    private void setViewListener() {
//标题的返回按钮监听
        mTitlebarBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBackClickListener != null) {
                    mOnBackClickListener.onClick();
                }
            }
        });
//下载菜单监听
        mTitleDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDownloadClickListener != null) {
                    onDownloadClickListener.onDownloadClick();
                }
            }
        });
//控制栏的播放按钮监听
        mPlayStateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPlayStateClickListener != null) {
                    mOnPlayStateClickListener.onPlayStateClick();
                }
            }
        });
//锁屏按钮监听
        mScreenLockBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnScreenLockClickListener != null) {
                    mOnScreenLockClickListener.onClick();
                }
            }
        });

        // 截图按钮监听
        mScreenShot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnScreenShotClickListener != null) {
                    mOnScreenShotClickListener.onScreenShotClick();
                }
            }
        });

        // 录制按钮监听
        mScreenRecorder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnScreenRecoderClickListener != null) {
                    mOnScreenRecoderClickListener.onScreenRecoderClick();
                }
            }
        });

//大小屏按钮监听
        mScreenModeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnScreenModeClickListener != null) {
                    mOnScreenModeClickListener.onClick();
                }
            }
        });

        //笔记
        mIvNote.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (onNoteClicklistener != null) {
                    onNoteClicklistener.onNoteClick();
                }
            }
        });
        //提示
        mIvHint.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (onHintClickListener != null) {
                    onHintClickListener.onHintClick();
                }
            }
        });
        //题目
        mIvExercise.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (onExerciseClickListener != null) {
                    onExerciseClickListener.onExerciseClick();
                }
            }
        });
        //解答
        mIvAnalysis.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (onAnalysisClickListener != null) {
                    onAnalysisClickListener.onAnalysisClick();
                }
            }
        });
        //点击改变学习模式
        mIvStudyStatus.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {

                if (!isStudyShow) {
                    //说明没有获取到点位信息
                    ToastUtil.show(getContext(), "当前视频暂无学习模式");
                } else {
                    if (LandscapeVideoActivity.isStudyShow) {
                        if (mOnStudyStatusListener != null) {
                            mOnStudyStatusListener.onStudyStatusCilck(false);
                        }
                        mIvStudyStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.exercise_open_close));
                    } else {
                        if (mOnStudyStatusListener != null) {
                            mOnStudyStatusListener.onStudyStatusCilck(true);
                        }
                        mIvStudyStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.exercise_open));
                    }
                }
            }
        });
        //弹出窗口的点击事件
        mLargeSeekbar.setOnPopWindowOnClick(new MutiPointSeekBar.OnPopWindowOnClick() {
            @Override
            public void onPopWindowClick(Object tag) {
                if (onPopWindowListener != null) {
                    onPopWindowListener.OnPopWindowClick(tag);
                }
            }
        });

//seekbar的滑动监听
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //这里是用户拖动，直接设置文字进度就行，
                if (fromUser) {
                    // 无需去updateAllViews() ， 因为不影响其他的界面。
                    if (mAliyunScreenMode == AliyunScreenMode.Full) {
                        //全屏状态.
                        mLargePositionText.setText(TimeFormater.formatMs(progress));
                    } else if (mAliyunScreenMode == AliyunScreenMode.Small) {
                        //小屏状态
                        mSmallPositionText.setText(TimeFormater.formatMs(progress));

                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekbarTouching = true;
                mHideHandler.removeMessages(WHAT_HIDE);
                if (mOnSeekListener != null) {
                    mOnSeekListener.onSeekStart();
                }
                Log.d("LF123", "onStartTrackingTouch 回调");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mOnSeekListener != null) {
                    mOnSeekListener.onSeekEnd(seekBar.getProgress());
                }
                isSeekbarTouching = false;
                mHideHandler.removeMessages(WHAT_HIDE);
                mHideHandler.sendEmptyMessageDelayed(WHAT_HIDE, DELAY_TIME);
                Log.d("LF123", "onStopTrackingTouch 回调");
            }
        };
//seekbar的滑动监听
        mLargeSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        mSmallSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
//全屏下的切换分辨率按钮监听
//        mLargeChangeQualityBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //点击切换分辨率 显示分辨率的对话框
//                if (mOnQualityBtnClickListener != null && mAliyunMediaInfo != null) {
//                    List<TrackInfo> qualityTrackInfos = new ArrayList<>();
//                    List<TrackInfo> trackInfos = mAliyunMediaInfo.getTrackInfos();
//                    for (TrackInfo trackInfo : trackInfos) {
//                        //清晰度
//                        if (trackInfo.getType() == TrackInfo.Type.TYPE_VOD) {
//                            qualityTrackInfos.add(trackInfo);
//                        }
//                    }
//                    mOnQualityBtnClickListener.onQualityBtnClick(v, qualityTrackInfos, mCurrentQuality);
//                }
//            }
//        });

        // 更多按钮点击监听
        mTitleMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnShowMoreClickListener != null) {
                    mOnShowMoreClickListener.showMore();
                }
            }
        });

        //速度切换按钮
        mTvSpeedValue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSpeedChangeClickLitener != null) {
                    onSpeedChangeClickLitener.onSpeedChange();
                }
            }
        });
    }

    /**
     * 是不是MTS的源 //MTS的清晰度显示与其他的不太一样，所以这里需要加一个作为区分
     *
     * @param isMts true:是。false:不是
     */
    public void setIsMtsSource(boolean isMts) {
        isMtsSource = isMts;
    }

    /**
     * 设置当前播放的清晰度
     *
     * @param currentQuality 当前清晰度
     */
    public void setCurrentQuality(String currentQuality) {
        mCurrentQuality = currentQuality;
        updateLargeInfoBar();
//        updateChangeQualityBtn();
    }

    /**
     * 设置是否强制清晰度。如果是强制，则不会显示切换清晰度按钮
     *
     * @param forceQuality true：是
     */
    public void setForceQuality(boolean forceQuality) {
        mForceQuality = forceQuality;
//        updateChangeQualityBtn();
    }

    /**
     * 设置是否显示标题栏。
     *
     * @param show false:不显示
     */
    public void setTitleBarCanShow(boolean show) {
        mTitleBarCanShow = show;
        updateAllTitleBar();
    }

    /**
     * 设置是否显示控制栏
     *
     * @param show fase：不显示
     */
    public void setControlBarCanShow(boolean show) {
        mControlBarCanShow = show;
        updateAllControlBar();
    }

    /**
     * 设置当前屏幕模式：全屏还是小屏
     *
     * @param mode {@link AliyunScreenMode#Small}：小屏. {@link AliyunScreenMode#Full}:全屏
     */
    @Override
    public void setScreenModeStatus(AliyunScreenMode mode) {
        mAliyunScreenMode = mode;
        updateLargeInfoBar();
        updateSmallInfoBar();
        updateScreenLockBtn();
        updateScreenModeBtn();
        updateShowMoreBtn();
        updateScreenShotBtn();
        updateScreenRecorderBtn();
        updateDownloadBtn();
        updateRightIcon();
    }

    private void updateRightIcon() {
        if (mAliyunScreenMode == AliyunScreenMode.Full && !mScreenLocked) {
            llRight.setVisibility(VISIBLE);
        } else {
            llRight.setVisibility(GONE);
        }
    }

    /**
     * 更新下载按钮的显示和隐藏
     */
    public void updateDownloadBtn() {
        if ("localSource".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            mTitleDownload.setVisibility(GONE);
        } else if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            mTitleDownload.setVisibility(VISIBLE);
        }
    }

    /**
     * 更新录屏按钮的显示和隐藏
     */
    private void updateScreenRecorderBtn() {
//        if (mAliyunScreenMode == AliyunScreenMode.Full) {
//            mScreenRecorder.setVisibility(VISIBLE);
//        } else {
//            mScreenRecorder.setVisibility(GONE);
//        }
        mScreenRecorder.setVisibility(GONE);
    }

    /**
     * 更新截图按钮的显示和隐藏
     */
    private void updateScreenShotBtn() {
//        if (mAliyunScreenMode == AliyunScreenMode.Full) {
//            mScreenShot.setVisibility(VISIBLE);
//        } else {
//            mScreenShot.setVisibility(GONE);
//        }
        mScreenShot.setVisibility(GONE);
    }

    /**
     * 更新更多按钮的显示和隐藏
     */
    private void updateShowMoreBtn() {
        if (mAliyunScreenMode == AliyunScreenMode.Full) {
            mTitleMore.setVisibility(VISIBLE);
            mTitleDownload.setVisibility(VISIBLE);
        } else {
            mTitleMore.setVisibility(GONE);
            mTitleDownload.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置主题色
     *
     * @param theme 支持的主题
     */
    @Override
    public void setTheme(AliyunVodPlayerView.Theme theme) {
        updateSeekBarTheme(theme);
    }

    /**
     * 设置当前的播放状态
     *
     * @param playState 播放状态
     */
    public void setPlayState(PlayState playState) {
        mPlayState = playState;
        updatePlayStateBtn();
    }

    /**
     * 设置视频信息
     *
     * @param
     * @param currentQuality 当前清晰度
     */
    public void setMediaInfo(MediaInfo aliyunMediaInfo, String currentQuality) {
        mAliyunMediaInfo = aliyunMediaInfo;
        mCurrentQuality = currentQuality;
        updateLargeInfoBar();
//        updateChangeQualityBtn();
    }


    public void showMoreButton() {
        mTitleMore.setVisibility(VISIBLE);
    }

    public void hideMoreButton() {
        mTitleMore.setVisibility(GONE);
    }


    /**
     * 更新当前主题色
     *
     * @param theme 设置的主题色
     */
    private void updateSeekBarTheme(AliyunVodPlayerView.Theme theme) {
        //获取不同主题的图片
        int progressDrawableResId = R.drawable.alivc_info_seekbar_bg_blue;
        int thumbResId = R.drawable.alivc_info_seekbar_thumb_blue;
        if (theme == AliyunVodPlayerView.Theme.Blue) {
            progressDrawableResId = (R.drawable.alivc_info_seekbar_bg_blue);
            thumbResId = (R.drawable.alivc_seekbar_thumb_blue);
        } else if (theme == AliyunVodPlayerView.Theme.Green) {
            progressDrawableResId = (R.drawable.alivc_info_seekbar_bg_green);
            thumbResId = (R.drawable.alivc_info_seekbar_thumb_green);
        } else if (theme == AliyunVodPlayerView.Theme.Orange) {
            progressDrawableResId = (R.drawable.alivc_info_seekbar_bg_orange);
            thumbResId = (R.drawable.alivc_info_seekbar_thumb_orange);
        } else if (theme == AliyunVodPlayerView.Theme.Red) {
            progressDrawableResId = (R.drawable.alivc_info_seekbar_bg_red);
            thumbResId = (R.drawable.alivc_info_seekbar_thumb_red);
        }


        //这个很有意思。。哈哈。不同的seekbar不能用同一个drawable，不然会出问题。
        // https://stackoverflow.com/questions/12579910/seekbar-thumb-position-not-equals-progress

        //设置到对应控件中
        Resources resources = getResources();
        Drawable smallProgressDrawable = ContextCompat.getDrawable(getContext(), progressDrawableResId);
        Drawable smallThumb = ContextCompat.getDrawable(getContext(), thumbResId);
        mSmallSeekbar.setProgressDrawable(smallProgressDrawable);
        mSmallSeekbar.setThumb(smallThumb);

        Drawable largeProgressDrawable = ContextCompat.getDrawable(getContext(), progressDrawableResId);
        Drawable largeThumb = ContextCompat.getDrawable(getContext(), thumbResId);
        mLargeSeekbar.setProgressDrawable(largeProgressDrawable);
        mLargeSeekbar.setThumb(largeThumb);
    }

    /**
     * 是否锁屏。锁住的话，其他的操作界面将不会显示。
     *
     * @param screenLocked true：锁屏
     */
    public void setScreenLockStatus(boolean screenLocked) {
        mScreenLocked = screenLocked;
        updateScreenLockBtn();
        updateAllTitleBar();
        updateAllControlBar();
        updateShowMoreBtn();
        updateScreenShotBtn();
        updateScreenRecorderBtn();
        updateDownloadBtn();
        updateRightIcon();
    }


    /**
     * 更新视频进度
     *
     * @param position 位置，ms
     */
    public void setVideoPosition(int position) {
        mVideoPosition = position;
        updateSmallInfoBar();
        updateLargeInfoBar();
    }

    /**
     * 获取视频进度
     *
     * @return 视频进度
     */
    public int getVideoPosition() {
        return mVideoPosition;
    }

    private void updateAllViews() {
        updateTitleView();//更新标题信息，文字
        updateScreenLockBtn();//更新锁屏状态
        updatePlayStateBtn();//更新播放状态
        updateLargeInfoBar();//更新大屏的显示信息
        updateSmallInfoBar();//更新小屏的显示信息
//        updateChangeQualityBtn();//更新分辨率按钮信息
        updateScreenModeBtn();//更新大小屏信息
        updateAllTitleBar(); //更新标题显示
        updateAllControlBar();//更新控制栏显示
        updateShowMoreBtn();
        updateScreenShotBtn();
        updateScreenRecorderBtn();
        updateDownloadBtn();
    }

    /**
     * 更新切换清晰度的按钮是否可见，及文字。
     * 当forceQuality的时候不可见。
     */
    private void updateChangeQualityBtn() {
        if (mLargeChangeQualityBtn != null) {
            VcPlayerLog.d(TAG, "mCurrentQuality = " + mCurrentQuality + " , isMts Source = " + isMtsSource + " , mForceQuality = " + mForceQuality);
            mLargeChangeQualityBtn.setText(QualityItem.getItem(getContext(), mCurrentQuality, isMtsSource).getName());
            mLargeChangeQualityBtn.setVisibility(mForceQuality ? GONE : VISIBLE);
        }
    }

    /**
     * 更新控制条的显示
     */
    private void updateAllControlBar() {
        //单独设置可以显示，并且没有锁屏的时候才可以显示
        boolean canShow = mControlBarCanShow && !mScreenLocked;
        if (mControlBar != null) {
            mControlBar.setVisibility(canShow ? VISIBLE : INVISIBLE);
        }
    }

    /**
     * 更新标题栏的显示
     */
    private void updateAllTitleBar() {
        //单独设置可以显示，并且没有锁屏的时候才可以显示
        boolean canShow = mTitleBarCanShow && !mScreenLocked;
        if (mTitleBar != null) {
            mTitleBar.setVisibility(canShow ? VISIBLE : INVISIBLE);
        }
    }


    /**
     * 更新播放按钮的状态
     */
    private void updatePlayStateBtn() {

        if (mPlayState == PlayState.NotPlaying) {
            mPlayStateBtn.setImageResource(R.drawable.alivc_playstate_play);
        } else if (mPlayState == PlayState.Playing) {
            mPlayStateBtn.setImageResource(R.drawable.alivc_playstate_pause);
        }
    }

    /**
     * 判断播放按钮是否可以点击
     *
     * @param isClick
     */
    public void updataPlayStatbtnCanClick(boolean isClick) {
        mPlayStateBtn.setClickable(isClick);
    }

    /**
     * 监听view是否可见。从而实现5秒隐藏的功能
     *
     * @param changedView
     * @param visibility
     */
    @Override
    protected void onVisibilityChanged(@Nullable View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            //如果变为可见了。启动五秒隐藏。
            hideDelayed();
        }
    }

    public void setHideType(HideType hideType) {
        this.mHideType = hideType;
    }

    /**
     * 隐藏类
     */
    private static class HideHandler extends Handler {
        private WeakReference<ControlView> controlViewWeakReference;

        public HideHandler(ControlView controlView) {
            controlViewWeakReference = new WeakReference<ControlView>(controlView);
        }

        @Override
        public void handleMessage(Message msg) {

            ControlView controlView = controlViewWeakReference.get();
            if (controlView != null) {
                if (!controlView.isSeekbarTouching) {
//                    controlView.hide(HideType.Normal);
                }
            }

            super.handleMessage(msg);
        }
    }

    private HideHandler mHideHandler = new HideHandler(this);

    private static final int WHAT_HIDE = 0;
    private static final int DELAY_TIME = 5 * 1000; //5秒后隐藏

    private void hideDelayed() {
        mHideHandler.removeMessages(WHAT_HIDE);
        mHideHandler.sendEmptyMessageDelayed(WHAT_HIDE, DELAY_TIME);
    }

    /**
     * 重置状态
     */
    @Override
    public void reset() {
        mHideType = null;
        mAliyunMediaInfo = null;
        mVideoPosition = 0;
        mPlayState = PlayState.NotPlaying;
        isSeekbarTouching = false;
        updateAllViews();
    }

    /**
     * 显示画面
     */
    @Override
    public void show() {
        if (mHideType == HideType.End) {
            //如果是由于错误引起的隐藏，那就不能再展现了
            setVisibility(GONE);
            hideQualityDialog();
        } else {
            updateAllViews();
            setVisibility(VISIBLE);
        }
    }

    /**
     * 隐藏画面
     */
    @Override
    public void hide(HideType hideType) {
        if (mHideType != HideType.End) {
            mHideType = hideType;
        }
        setVisibility(GONE);
        hideQualityDialog();
    }

    /**
     * 隐藏清晰度对话框
     */
    private void hideQualityDialog() {
        if (mOnQualityBtnClickListener != null) {
            mOnQualityBtnClickListener.onHideQualityView();
        }
    }

    /**
     * 设置当前缓存的进度，给seekbar显示
     *
     * @param mVideoBufferPosition 进度，ms
     */
    public void setVideoBufferPosition(int mVideoBufferPosition) {
        this.mVideoBufferPosition = mVideoBufferPosition;
        updateSmallInfoBar();
        updateLargeInfoBar();
    }

    public void setOnMenuClickListener(OnMenuClickListener l) {
        mOnMenuClickListener = l;
    }


    public interface OnMenuClickListener {
        /**
         * 按钮点击事件
         */
        void onMenuClick();
    }

    public interface OnDownloadClickListener {
        /**
         * 下载点击事件
         */
        void onDownloadClick();
    }

    public void setOnDownloadClickListener(
            OnDownloadClickListener onDownloadClickListener) {
        this.onDownloadClickListener = onDownloadClickListener;
    }

    public void setOnQualityBtnClickListener(OnQualityBtnClickListener l) {
        mOnQualityBtnClickListener = l;
    }

    public interface OnQualityBtnClickListener {
        /**
         * 清晰度按钮被点击
         *
         * @param v              被点击的view
         * @param qualities      支持的清晰度
         * @param currentQuality 当前清晰度
         */
        void onQualityBtnClick(View v, List<TrackInfo> qualities, String currentQuality);

        /**
         * 隐藏
         */
        void onHideQualityView();
    }


    public void setOnScreenLockClickListener(OnScreenLockClickListener l) {
        mOnScreenLockClickListener = l;
    }

    public interface OnScreenLockClickListener {
        /**
         * 锁屏按钮点击事件
         */
        void onClick();
    }

    public void setOnScreenModeClickListener(OnScreenModeClickListener l) {
        mOnScreenModeClickListener = l;
    }

    public interface OnScreenModeClickListener {
        /**
         * 大小屏按钮点击事件
         */
        void onClick();
    }


    public void setOnBackClickListener(OnBackClickListener l) {
        mOnBackClickListener = l;
    }

    public interface OnBackClickListener {
        /**
         * 返回按钮点击事件
         */
        void onClick();
    }

    public interface OnSeekListener {
        /**
         * seek结束事件
         */
        void onSeekEnd(int position);

        /**
         * seek开始事件
         */
        void onSeekStart();


    }


    public void setOnSeekListener(OnSeekListener onSeekListener) {
        mOnSeekListener = onSeekListener;
    }

    /**
     * 播放状态
     */
    public static enum PlayState {
        /**
         * Playing:正在播放
         * NotPlaying: 停止播放
         */
        Playing, NotPlaying
    }

    public interface OnPlayStateClickListener {
        /**
         * 播放按钮点击事件
         */
        void onPlayStateClick();
    }


    public void setOnPlayStateClickListener(OnPlayStateClickListener onPlayStateClickListener) {
        mOnPlayStateClickListener = onPlayStateClickListener;
    }

    /**
     * 横屏下显示更多
     */
    public interface OnShowMoreClickListener {
        void showMore();
    }

    public void setOnShowMoreClickListener(
            OnShowMoreClickListener listener) {
        this.mOnShowMoreClickListener = listener;
    }

    /**
     * 屏幕截图
     */
    public interface OnScreenShotClickListener {
        void onScreenShotClick();
    }

    public void setOnScreenShotClickListener(OnScreenShotClickListener listener) {
        this.mOnScreenShotClickListener = listener;
    }

    /**
     * 章节
     */
    public interface OnSectionsClickListener {
        void onSectionsClick();
    }

    public void setOnSectionsClickListener(OnSectionsClickListener listener) {
        this.onSectionsClickListener = listener;
    }

    /**
     * 录制
     */
    public interface OnScreenRecoderClickListener {
        void onScreenRecoderClick();
    }

    public void setOnScreenRecoderClickListener(OnScreenRecoderClickListener listener) {
        this.mOnScreenRecoderClickListener = listener;
    }

    /**
     * 笔记按钮
     */
    public interface OnNoteClicklistener {
        void onNoteClick();
    }

    public void setOnNoteClicklistener(OnNoteClicklistener onNoteClicklistener) {
        this.onNoteClicklistener = onNoteClicklistener;
    }

    /**
     * 提示按钮
     */
    public interface OnHintClickListener {
        void onHintClick();
    }

    public void setOnHintClickListener(OnHintClickListener onHintClickListener) {
        this.onHintClickListener = onHintClickListener;
    }

    /**
     * 题目按钮
     */
    public interface OnExerciseClickListener {
        void onExerciseClick();
    }

    public void setOnExerciseClickListener(OnExerciseClickListener onExerciseClickListener) {
        this.onExerciseClickListener = onExerciseClickListener;
    }

    /**
     * 答 按钮
     */
    public interface OnAnalysisClickListener {
        void onAnalysisClick();
    }

    public OnAnalysisClickListener onAnalysisClickListener;

    public void setOnAnalysisClickListener(OnAnalysisClickListener onAnalysisClickListener) {
        this.onAnalysisClickListener = onAnalysisClickListener;
    }

    /**
     * 倍速切换按钮
     */
    public interface OnSpeedChangeClickLitener {
        void onSpeedChange();
    }

    public void setOnSpeedChangeClickLitener(OnSpeedChangeClickLitener onSpeedChangeClickLitener) {
        this.onSpeedChangeClickLitener = onSpeedChangeClickLitener;
    }

    /**
     * 设置点位是否能够显示出来
     *
     * @param isShow
     */
    public void setBooleanIsShowPoint(boolean isShow) {
        if (mLargeSeekbar != null) {
            mLargeSeekbar.setBoolean(isShow);
        }
    }

    /**
     * 设置点位数据
     */
    public void setMutiPoints(List<PointListBean.PointBean> pointBeans, List<PointListBean.PointBean> points, long duration) {
        this.points = pointBeans;
        if (mLargeSeekbar != null) {
            mLargeSeekbar.setMutiPoints(pointBeans, points, duration);
        }
    }

    /**
     * 更新标题栏的标题文字
     */
    private void updateTitleView() {
        if (mAliyunMediaInfo != null && mAliyunMediaInfo.getTitle() != null && !("null".equals(mAliyunMediaInfo.getTitle()))) {
            mTitlebarText.setText(mAliyunMediaInfo.getTitle());
            if (TextUtils.isEmpty(mAliyunMediaInfo.getTitle())) {
                if (!TextUtils.isEmpty(downloadVideoTitle)) {
                    mTitlebarText.setText(downloadVideoTitle);
                }
            }
            Log.e("======", "updateTitleView");
        }
    }

    private String downloadVideoTitle;

    public void setTiTitle(String title) {
        this.downloadVideoTitle = title;

    }

    /**
     * 更新小屏下的控制条信息
     */
    private void updateSmallInfoBar() {
        if (mAliyunScreenMode == AliyunScreenMode.Full) {
            mSmallInfoBar.setVisibility(INVISIBLE);
        } else if (mAliyunScreenMode == AliyunScreenMode.Small) {
            //先设置小屏的info数据
            if (mAliyunMediaInfo != null) {
                mSmallDurationText.setText("/" + TimeFormater.formatMs(mAliyunMediaInfo.getDuration()));
                mSmallSeekbar.setMax((int) mAliyunMediaInfo.getDuration());
            } else {
                mSmallDurationText.setText("/" + TimeFormater.formatMs(0));
                mSmallSeekbar.setMax(0);
            }

            if (isSeekbarTouching) {
                //用户拖动的时候，不去更新进度值，防止跳动。
            } else {
                mSmallSeekbar.setSecondaryProgress(mVideoBufferPosition);
                mSmallSeekbar.setProgress(mVideoPosition);
                mSmallPositionText.setText(TimeFormater.formatMs(mVideoPosition));
            }
            //然后再显示出来。
            mSmallInfoBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 更新大屏下的控制条信息
     */
    private void updateLargeInfoBar() {
        if (mAliyunScreenMode == AliyunScreenMode.Small) {
            //里面包含了很多按钮，比如切换清晰度的按钮之类的
            mLargeInfoBar.setVisibility(INVISIBLE);
        } else if (mAliyunScreenMode == AliyunScreenMode.Full) {
            //先更新大屏的info数据
            if (mAliyunMediaInfo != null) {
                mLargeDurationText.setText("/" + TimeFormater.formatMs(mAliyunMediaInfo.getDuration()));
                mLargeSeekbar.setMax((int) mAliyunMediaInfo.getDuration());
            } else {
                mLargeDurationText.setText("/" + TimeFormater.formatMs(0));
                mLargeSeekbar.setMax(0);
            }

            if (isSeekbarTouching) {
                //用户拖动的时候，不去更新进度值，防止跳动。
            } else {
                mLargeSeekbar.setSecondaryProgress(mVideoBufferPosition);
                mLargeSeekbar.setProgress(mVideoPosition);
                mLargePositionText.setText(TimeFormater.formatMs(mVideoPosition));
            }
//            mLargeChangeQualityBtn.setText(QualityItem.getItem(getContext(), mCurrentQuality, isMtsSource).getName());
            //然后再显示出来。
            mLargeInfoBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 更新切换大小屏按钮的信息
     */
    private void updateScreenModeBtn() {
        if (mAliyunScreenMode == AliyunScreenMode.Full) {
            mScreenModeBtn.setImageResource(R.drawable.alivc_screen_mode_small);
        } else {
            mScreenModeBtn.setImageResource(R.drawable.alivc_screen_mode_large);
        }
    }

    /**
     * 更新锁屏按钮的信息
     */
    private void updateScreenLockBtn() {
        if (mScreenLocked) {
            mScreenLockBtn.setImageResource(R.drawable.alivc_screen_lock);
        } else {
            mScreenLockBtn.setImageResource(R.drawable.alivc_screen_unlock);
        }

        if (mAliyunScreenMode == AliyunScreenMode.Full) {
//            mScreenLockBtn.setVisibility(VISIBLE);
            mTitleMore.setVisibility(VISIBLE);
        } else {
//            mScreenLockBtn.setVisibility(GONE);
            mTitleMore.setVisibility(GONE);
        }
    }


    /**
     * 点击了学习模式是否显示
     */
    public interface OnStudyStatusListener {
        void onStudyStatusCilck(boolean b);
    }

    public void setmOnStudyStatusListener(OnStudyStatusListener mOnStudyStatusListener) {
        this.mOnStudyStatusListener = mOnStudyStatusListener;
    }

    /**
     * 弹出框
     */
    public void showTipsInSeekBar(int type, String title, double progress, int index) {
        if (mLargeSeekbar != null && mLargeSeekbar.isShown()) {
            mLargeSeekbar.showTipsInSeekBar(type, title, progress, index);
        }
    }

    /**
     * 点击了popwindow的视图
     */
    public interface OnPopWindowListener {
        void OnPopWindowClick(Object tag);
    }

    public void setOnPopWindowListener(OnPopWindowListener onPopWindowListener) {
        this.onPopWindowListener = onPopWindowListener;
    }

    /**
     * 动态改变速度值
     *
     * @param s
     */
    public void setTvSpeedValue(String s) {
        if (mTvSpeedValue != null) {
            mTvSpeedValue.setText(s);
        }
    }

    /**
     * 根据数据判断学习模式的UI是否应该显示出来
     *
     * @param isShow
     */
    public void setStudyViewShow(boolean isShow) {
        setNoteIcon(isShow);
        setHintIcon(isShow);
        setExerciseIcon(isShow);
        setAnalysisIcon(isShow);
        if (isShow) {
            if (isStudyShow) {
                if (mIvStudyStatus != null) {
                    mIvStudyStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.exercise_open));
                }
            } else {
                if (mIvStudyStatus != null) {
                    mIvStudyStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.exercise_open_close));
                }
            }
        } else {
            if (mIvStudyStatus != null) {
                mIvStudyStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.exercise_open_close));
            }
        }
    }

    /**
     * 第一次加载数据时候控制，如果没有点位的话，点击学习模式按钮时就将其屏蔽
     *
     * @param isStudyShow
     */
    public void setStudyUI(boolean isStudyShow) {
        this.isStudyShow = isStudyShow;
    }

    public void showDownloadingTag(boolean show) {
        ivDownloadingTag.setVisibility(show ? VISIBLE : GONE);
    }

    public void onDestory() {
        if (mLargeSeekbar != null) {
            mLargeSeekbar.closeDialog();
        }
    }

    /**
     * 控制笔记标签是展示还是隐藏
     *
     * @param isshow true 展示，false隐藏
     */
    public void setNoteIcon(boolean isshow) {
        if (mIvNote != null) {
            mIvNote.setVisibility(isshow ? VISIBLE : GONE);
        }
    }

    /**
     * 控制提示标签是展示还是隐藏
     *
     * @param isshow true 展示，false隐藏
     */
    public void setHintIcon(boolean isshow) {
        if (mIvHint != null) {
            mIvHint.setVisibility(isshow ? VISIBLE : GONE);
        }
    }

    /**
     * 控制题目的标签是展示还是隐藏
     *
     * @param isshow true 展示，false隐藏
     */
    public void setExerciseIcon(boolean isshow) {
        if (mIvExercise != null) {
            mIvExercise.setVisibility(isshow ? VISIBLE : GONE);
        }
    }

    /**
     * 控制术语的标签是展示还是隐藏
     *
     * @param isshow true 展示，false隐藏
     */
    public void setAnalysisIcon(boolean isshow) {
        if (mIvAnalysis != null) {
            mIvAnalysis.setVisibility(isshow ? VISIBLE : GONE);
        }
    }

    /**
     * 学习模式的开关闭UI变动
     *
     * @param open
     */
    public void StudyIconChange(boolean open) {
        if (mIvStudyStatus != null) {
            mIvStudyStatus.setImageDrawable(open ? mContext.getResources().getDrawable(R.drawable.exercise_open) : mContext.getResources().getDrawable(R.drawable.exercise_open_close));
        }
    }
}
