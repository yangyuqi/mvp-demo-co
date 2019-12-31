package com.wxjz.module_aliyun.aliyun.view.more;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;
import com.wxjz.module_base.util.ToastUtil;

public class ShowMoreView extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Context context;
    private SeekBar seekLight;
    private SeekBar seekVoice;
    private TextView tvDonwload;
    private TextView tvCastScreen;
    private TextView tvBarrage;
    private TextView tvSetStudyStatus;
    private TextView tvSeekVoiceValue;
    private TextView tvSeekLightValue;
    private ImageView ivImageStudyStatus;
    private RadioGroup rgSpeed, rgSpeed1, rgQuality;
    private AliyunShowMoreValue moreValue;
    private OnDownloadButtonClickListener mOnDownloadButtonClickListener;
    private OnSpeedCheckedChangedListener mOnSpeedCheckedChangedListener;
    private OnPictureQualityChangedListener mOnPictureQualityChangedListener;
    private OnLightSeekChangeListener mOnLightSeekChangeListener;
    private OnVoiceSeekChangeListener mOnVoiceSeekChangeListener;
    private OnScreenCastButtonClickListener mOnScreenCastButtonClickListener;
    private OnBarrageButtonClickListener mOnBarrageButtonClickListener;
    private OnStudyStatusChangeListener mOnStudyStatusChangeListener;
    //学习状态，默认开启
    private boolean studyStatus;
    //用于RadioGroup互斥判断
    private boolean isCheck;

    public ShowMoreView(Activity context, AliyunShowMoreValue moreValue) {
        super(context);
        this.context = context;
        this.moreValue = moreValue;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.alivc_dialog_more, this, true);
        findAllViews(view);
    }

    private void findAllViews(View view) {
        seekLight = view.findViewById(R.id.seek_light);
        seekVoice = view.findViewById(R.id.seek_voice);
//        tvDonwload = view.findViewById(R.id.tv_download);
//        tvCastScreen = view.findViewById(R.id.tv_cast_screen);
//        tvBarrage = view.findViewById(R.id.tv_barrage);
        tvSetStudyStatus = findViewById(R.id.tv_set_study_status);
        ivImageStudyStatus = findViewById(R.id.iv_image_change_study_status);
        tvSeekVoiceValue = findViewById(R.id.tv_seek_voice_value);
        tvSeekLightValue = findViewById(R.id.tv_seek_light_value);
        rgSpeed = findViewById(R.id.alivc_rg_speed);
        rgSpeed1 = findViewById(R.id.alivc_rg_speed1);
        rgQuality = findViewById(R.id.alivc_rg_pixel);
        configViews();
        addListener();

    }

    private void configViews() {
        if (moreValue == null) {
            return;
        }
        seekLight.setProgress(moreValue.getScreenBrightness());
        seekVoice.setProgress(moreValue.getVolume());
        if (tvSeekLightValue != null) {
            tvSeekLightValue.setText(moreValue.getScreenBrightness() + "%");
        }
        if (tvSeekVoiceValue != null) {
            tvSeekVoiceValue.setText(moreValue.getVolume() + "%");
        }

        if (!moreValue.isStudyStatus()) {
            if (ivImageStudyStatus != null) {
                ivImageStudyStatus.setImageResource(R.drawable.close_study);

            }
            if (tvSetStudyStatus != null) {
                tvSetStudyStatus.setText("已关闭");
            }
        } else {
            if (LandscapeVideoActivity.isStudyShow) {
                if (ivImageStudyStatus != null) {
                    ivImageStudyStatus.setImageResource(R.drawable.open_study);
                }
                if (tvSetStudyStatus != null) {
                    tvSetStudyStatus.setText("已开启");
                }
            } else {
                if (ivImageStudyStatus != null) {
                    ivImageStudyStatus.setImageResource(R.drawable.close_study);

                }
                if (tvSetStudyStatus != null) {
                    tvSetStudyStatus.setText("已关闭");
                }
            }

        }
        int currentRbIndex = 1;
        int rgSpeedLayout = 0;
        float curentSpeed = moreValue.getSpeed();
        if (curentSpeed == 0.75f) {
            currentRbIndex = 0;
            rgSpeedLayout = 0;
        } else if (curentSpeed == 1.0f) {
            currentRbIndex = 1;
            rgSpeedLayout = 0;
        } else if (curentSpeed == 1.25f) {
            currentRbIndex = 2;
            rgSpeedLayout = 0;
        } else if (curentSpeed == 1.5f) {
            currentRbIndex = 3;
            rgSpeedLayout = 0;
        } else if (curentSpeed == 1.75f) {
            currentRbIndex = 0;
            rgSpeedLayout = 1;
//        } else if (curentSpeed == 2.0f) {
//            currentRbIndex = 1;
//            rgSpeedLayout = 1;
        }
        Log.d("LF123", "速度" + curentSpeed);
        if (rgSpeedLayout == 0) {
            rgSpeed.check(rgSpeed.getChildAt(currentRbIndex).getId());
        } else {
            rgSpeed1.check(rgSpeed1.getChildAt(currentRbIndex).getId());
        }
        rgQuality.check(rgQuality.getChildAt(1).getId());
    }


    private void addListener() {
//        tvDonwload.setOnClickListener(this);
//        tvCastScreen.setOnClickListener(this);
//        tvBarrage.setOnClickListener(this);
        ivImageStudyStatus.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (!moreValue.isStudyStatus()) {
                    ToastUtil.show(getContext(), "当前视频暂无学习模式");
                } else {
                    if (LandscapeVideoActivity.isStudyShow) {
                        if (ivImageStudyStatus != null) {
                            ivImageStudyStatus.setImageResource(R.drawable.close_study);

                        }
                        if (tvSetStudyStatus != null) {
                            tvSetStudyStatus.setText("已关闭");
                        }
                        if (mOnStudyStatusChangeListener != null) {
                            mOnStudyStatusChangeListener.OnStudyStatusChange(false);
                        }
                    } else {
                        if (ivImageStudyStatus != null) {
                            ivImageStudyStatus.setImageResource(R.drawable.open_study);
                        }
                        if (tvSetStudyStatus != null) {
                            tvSetStudyStatus.setText("已开启");
                        }
                        if (mOnStudyStatusChangeListener != null) {
                            mOnStudyStatusChangeListener.OnStudyStatusChange(true);
                        }
                    }
                }

            }
        });

        rgSpeed.setOnCheckedChangeListener(this);
        rgSpeed1.setOnCheckedChangeListener(this);
        rgQuality.setOnCheckedChangeListener(this);
        seekLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mOnLightSeekChangeListener != null) {
                    mOnLightSeekChangeListener.onStart(seekBar);
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (tvSeekLightValue != null) {
                    tvSeekLightValue.setText(progress + "%");
                }
                if (mOnLightSeekChangeListener != null) {
                    mOnLightSeekChangeListener.onProgress(seekBar, progress, fromUser);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mOnLightSeekChangeListener != null) {
                    mOnLightSeekChangeListener.onStop(seekBar);
                }
            }
        });

        seekVoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mOnVoiceSeekChangeListener != null) {
                    mOnVoiceSeekChangeListener.onStart(seekBar);
                }
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (tvSeekVoiceValue != null) {
                    tvSeekVoiceValue.setText(progress + "%");
                }
                if (mOnVoiceSeekChangeListener != null) {
                    mOnVoiceSeekChangeListener.onProgress(seekBar, progress, fromUser);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mOnVoiceSeekChangeListener != null) {
                    mOnVoiceSeekChangeListener.onStop(seekBar);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.tv_download) {
//            // 下载
//            if (mOnDownloadButtonClickListener != null) {
//                mOnDownloadButtonClickListener.onDownloadClick();
//            }
//        } else if (id == R.id.tv_cast_screen) {
//            // 投屏
//            if (mOnScreenCastButtonClickListener != null) {
//                mOnScreenCastButtonClickListener.onScreenCastClick();
//            }
//
//        } else if (id == R.id.tv_barrage) {
//            // 弹幕
//            if (mOnBarrageButtonClickListener != null) {
//                mOnBarrageButtonClickListener.onBarrageClick();
//            }
//        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (rgQuality.getId() == group.getId()) {
            if (mOnPictureQualityChangedListener != null) {
                mOnPictureQualityChangedListener.onQualityChanged(group, checkedId);
            }
        } else {
            //多个radioGroup选项互斥事件
            if (isCheck) {
                return;
            }
            if (rgSpeed.getId() == group.getId()) {
                isCheck = true;
                rgSpeed1.clearCheck();
                isCheck = false;
            } else if (rgSpeed1.getId() == group.getId()) {
                isCheck = true;
                rgSpeed.clearCheck();
                isCheck = false;
            }
            if (mOnSpeedCheckedChangedListener != null) {
                mOnSpeedCheckedChangedListener.onSpeedChanged(group, checkedId);
            }
        }

    }

    public interface OnDownloadButtonClickListener {
        /**
         * 下载按钮点击
         */
        void onDownloadClick();
    }

    public void setOnDownloadButtonClickListener(OnDownloadButtonClickListener listener) {
        this.mOnDownloadButtonClickListener = listener;
    }

    public interface OnScreenCastButtonClickListener {
        /**
         * 投屏按钮点击
         */
        void onScreenCastClick();
    }

    public void setOnScreenCastButtonClickListener(OnScreenCastButtonClickListener listener) {
        this.mOnScreenCastButtonClickListener = listener;
    }

    public interface OnBarrageButtonClickListener {
        /**
         * 弹幕按钮点击
         */
        void onBarrageClick();
    }

    public void setOnBarrageButtonClickListener(OnBarrageButtonClickListener listener) {
        this.mOnBarrageButtonClickListener = listener;
    }

    public interface OnSpeedCheckedChangedListener {
        /**
         * 速度切换
         *
         * @param group
         * @param checkedId
         */
        void onSpeedChanged(RadioGroup group, int checkedId);
    }

    public void setOnSpeedCheckedChangedListener(OnSpeedCheckedChangedListener listener) {
        this.mOnSpeedCheckedChangedListener = listener;
    }

    public interface OnPictureQualityChangedListener {
        /**
         * 画面像素切换
         */
        void onQualityChanged(RadioGroup group, int checkedId);
    }

    public void setOnPictureQualityChangedListener(OnPictureQualityChangedListener mOnPictureQualityChangedListener) {
        this.mOnPictureQualityChangedListener = mOnPictureQualityChangedListener;
    }

    /**
     * 亮度调节
     */
    public interface OnLightSeekChangeListener {
        void onStart(SeekBar seekBar);

        void onProgress(SeekBar seekBar, int progress, boolean fromUser);

        void onStop(SeekBar seekBar);
    }

    public void setOnLightSeekChangeListener(OnLightSeekChangeListener listener) {
        this.mOnLightSeekChangeListener = listener;
    }

    /**
     * 音量调节
     */
    public interface OnVoiceSeekChangeListener {
        void onStart(SeekBar seekBar);

        void onProgress(SeekBar seekBar, int progress, boolean fromUser);

        void onStop(SeekBar seekBar);
    }

    public void setOnVoiceSeekChangeListener(OnVoiceSeekChangeListener listener) {
        this.mOnVoiceSeekChangeListener = listener;
    }

    /**
     * 观看模式的改变
     */
    public interface OnStudyStatusChangeListener {
        void OnStudyStatusChange(boolean status);
    }

    public void setmOnStudyStatusChangeListener(OnStudyStatusChangeListener mOnStudyStatusChangeListener) {
        this.mOnStudyStatusChangeListener = mOnStudyStatusChangeListener;
    }

    /**
     * 设置音量
     */
    public void setVoiceVolume(float volume) {
        if (seekVoice != null) {
            seekVoice.setProgress((int) (volume * 100));
        }
    }

    /**
     * 设置亮度
     */
    public void setBrightness(int value) {
        if (seekLight != null) {
            seekLight.setProgress(value);
        }
    }
}

