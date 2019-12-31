package com.wxjz.module_base.base;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wxjz.module_base.receiver.NetworkChangeReceiver;
import com.wxjz.module_base.util.AppManager;
import com.wxjz.module_base.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by a on 2019/7/5.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private NetworkChangeReceiver networkChangeReceiver;
    protected BaseActivity mContext;
    protected String TAG = getClass().getSimpleName();
    private int lastClickViewId;
    private long lastClickTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        mContext = this;
        setStatusBarColor(Color.parseColor("#FBD80C"));
        // 基类中注册 eventbus
        if (needEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //这个字段就是把状态栏标记为浅色，然后状态栏的字体颜色自动转换为深色。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
        }
//       StatusBarUtil.setLightMode(this);

        registerNetReceiver();
        initView();
        initData();

    }

    protected void setStatusBarColor(int color) {
        StatusBarUtil.setColor(this, color, 0);
    }

    protected abstract boolean needEventBus();

    protected abstract void initData();

    protected abstract void initView();

    private void registerNetReceiver() {
        networkChangeReceiver = new NetworkChangeReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

    }

    protected abstract int getLayoutId();

    @Override
    public void onClick(View v) {
        if (notFastClick(v)) {
            widgetClick(v);
        }

    }

    private boolean notFastClick(View view) {
        if (view.getId() == lastClickViewId) {
            if (System.currentTimeMillis() - lastClickTime <= 1000) {
                return false;
            }
        }
        lastClickViewId = view.getId();
        lastClickTime = System.currentTimeMillis();
        return true;
    }

    protected void widgetClick(View view) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (needEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        AppManager.getAppManager().finishActivity(this);
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
            networkChangeReceiver.onDestroy();
            networkChangeReceiver = null;
        }
    }
}
