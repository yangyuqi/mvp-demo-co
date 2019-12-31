package com.wxjz.module_base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by a on 2019/8/29.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    protected Context mContext;
    private Unbinder bind;
    private int lastClickViewId;
    private long lastClickTime;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    protected abstract void initView(View view);

    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (needEventBus()) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
    }
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

    protected abstract int getLayoutId();

    public abstract boolean needEventBus();
}
