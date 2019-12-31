package com.wxjz.module_base.util;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * @ClassName CountDownUtil
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-03 16:14
 * @Version 1.0
 */
public class CountDownUtil extends CountDownTimer {
    private TextView mTextView;


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    mTextView.setClickable(false);
                    break;
                case 0x02:
                    mTextView.setText("获取验证码");
                    mTextView.setClickable(true);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    public CountDownUtil(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        mTextView = textView;
    }

    @Override
    public void onTick(long l) {
        Message message = mHandler.obtainMessage(0x01);
        mHandler.sendMessage(message);
        mTextView.setText(l / 1000 + "s后重发");
    }

    @Override
    public void onFinish() {
        Message message = mHandler.obtainMessage(0x02);
        mHandler.sendMessage(message);
    }
}
