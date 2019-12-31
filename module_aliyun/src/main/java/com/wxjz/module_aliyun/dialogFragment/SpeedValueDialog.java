package com.wxjz.module_aliyun.dialogFragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.view.more.AliyunShowMoreValue;

/**
 * @ClassName SpeedValueDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-16 16:38
 * @Version 1.0
 */
public class SpeedValueDialog extends BaseDialog {
    private static SpeedValueDialog mSpeedValueDialog;
    private AliyunShowMoreValue moreValue;
    private TextView[] textViews = new TextView[5];
    public OnSpeedChangeLisener speedChangeLisener;
    private boolean ischeck;
    int j = 0;

    public static SpeedValueDialog newInstance() {
        if (mSpeedValueDialog == null) {
            mSpeedValueDialog = new SpeedValueDialog();
        }
        return mSpeedValueDialog;
    }

    public void setMoreValue(AliyunShowMoreValue moreValue) {
        this.moreValue = moreValue;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_speed_value;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        initView(holder);
        initData(holder);
        initListener(holder, dialog);
    }

    private void initView(ViewHolder holder) {
        textViews[0] = (TextView) holder.findView(R.id.tv_speed_value_1);
        textViews[1] = (TextView) holder.findView(R.id.tv_speed_value_2);
        textViews[2] = (TextView) holder.findView(R.id.tv_speed_value_3);
        textViews[3] = (TextView) holder.findView(R.id.tv_speed_value_4);
        textViews[4] = (TextView) holder.findView(R.id.tv_speed_value_5);
    }

    private void initData(ViewHolder holder) {
        //进来的时候设置速度
        setSpeedValue(moreValue);
    }

    /**
     * 进来的时候设置速度
     *
     * @param moreValue
     */
    private void setSpeedValue(AliyunShowMoreValue moreValue) {
        float curentSpeed = moreValue.getSpeed();
        int currentRbIndex = 1;
        if (curentSpeed == 0.75f) {
            currentRbIndex = 0;
        } else if (curentSpeed == 1.0f) {
            currentRbIndex = 1;
        } else if (curentSpeed == 1.25f) {
            currentRbIndex = 2;
        } else if (curentSpeed == 1.5f) {
            currentRbIndex = 3;
        } else if (curentSpeed == 1.75f) {
            currentRbIndex = 4;
        }
        setViewUI(currentRbIndex);
    }

    private void setViewUI(int currentRbIndex) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == currentRbIndex) {
                textViews[i].setTextColor(getResources().getColor(R.color.alivc_yellow));
            } else {
                textViews[i].setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    private void initListener(ViewHolder holder, BaseDialog dialog) {
        textViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewUI(0);
                if (speedChangeLisener != null) {
                    speedChangeLisener.OnSpeedChange(0);
                }
            }
        });
        textViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewUI(1);
                if (speedChangeLisener != null) {
                    speedChangeLisener.OnSpeedChange(1);
                }
            }
        });
        textViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewUI(2);
                if (speedChangeLisener != null) {
                    speedChangeLisener.OnSpeedChange(2);
                }
            }
        });
        textViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewUI(3);
                if (speedChangeLisener != null) {
                    speedChangeLisener.OnSpeedChange(3);
                }
            }
        });
        textViews[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewUI(4);
                if (speedChangeLisener != null) {
                    speedChangeLisener.OnSpeedChange(4);
                }
            }
        });
    }

    public interface OnSpeedChangeLisener {
        void OnSpeedChange(int i);
    }

    public void setSpeedChangeLisener(OnSpeedChangeLisener speedChangeLisener) {
        this.speedChangeLisener = speedChangeLisener;
    }
}
