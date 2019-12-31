package com.wxjz.module_aliyun.dialogFragment;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.adapter.TipsLoadMoreAdapter;
import com.wxjz.module_aliyun.aliyun.listener.EndlessRecyclerOnScrollListener;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.aliyun.utils.FixedToastUtils;
import com.wxjz.module_aliyun.event.DialogDissmissEvent;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.TipsListBean;
import com.wxjz.module_base.db.bean.TipsBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/2
 * Time: 18:35
 */
public class TipsDialog extends BaseDialog {

    private static TipsDialog mTipsDialog;

    private RecyclerView mPromptRecyclerView;

    private SwipeRefreshLayout mPromptSwipeRefreshLayout;

    private TipsLoadMoreAdapter tipsLoadMoreAdapter;

    private List<PointListBean.PointBean> datalist = new ArrayList<>();

    //判断当前是否是正在上拉加载
    private boolean isLoadmore;

    public static TipsDialog newInstance() {
        if (mTipsDialog == null) {
            mTipsDialog = new TipsDialog();
        }
        return mTipsDialog;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_prompt;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        initView(holder);
        initData(holder);
        initListener(holder, dialog);
    }

    private void initView(ViewHolder holder) {
        mPromptRecyclerView = (RecyclerView) holder.findView(R.id.recycler_view);
        mPromptSwipeRefreshLayout = (SwipeRefreshLayout) holder.findView(R.id.swipe_refresh_layout);
        //禁止下拉刷新
        mPromptSwipeRefreshLayout.setEnabled(false);
    }

    private void initData(ViewHolder holder) {
        tipsLoadMoreAdapter = new TipsLoadMoreAdapter(datalist);
        mPromptRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPromptRecyclerView.setAdapter(tipsLoadMoreAdapter);
        tipsLoadMoreAdapter.setItemViewClickListener(new TipsLoadMoreAdapter.AdapterOnItemViewClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                if (view.getId() == R.id.tv_terminology_time) {
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (isSendEventBus()) {
            EventBus.getDefault().post(new DialogDissmissEvent(true));
        }
    }

    private void initListener(ViewHolder holder, BaseDialog dialog) {
        //点击了关闭按钮
        holder.setOnClickListener(R.id.iv_dialog_prompt_close, new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (getDialog() != null && getDialog().isShowing()) {
                    if (!mPromptSwipeRefreshLayout.isRefreshing() && !isLoadmore) {
                        getDialog().dismiss();
                    } else {
                        Toast.makeText(getContext(), "你当前正在执行刷新操作，请稍后", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        //上拉加载或者下拉刷新的时候屏蔽滑动事件
//        mPromptRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mPromptSwipeRefreshLayout.isRefreshing() || isLoadmore) {
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        //上拉加载更多的监听
//        mPromptRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                isLoadmore = true;
//                tipsLoadMoreAdapter.setLoadState(tipsLoadMoreAdapter.LOADING);
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                tipsLoadMoreAdapter.setLoadState(tipsLoadMoreAdapter.LOADING_END);
//                                isLoadmore = false;
//                            }
//                        });
//                    }
//                }, 2000);
//            }
//
//            @Override
//            public void firstitemCanSee() {
//                mPromptSwipeRefreshLayout.setEnabled(false);
//            }
//
//            @Override
//            public void firstitemNotSee() {
//                mPromptSwipeRefreshLayout.setEnabled(true);
//            }
//        });
//        //下拉刷新的接口
//        mPromptSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //延迟一秒关闭下拉刷新
//                mPromptSwipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        FixedToastUtils.show(getContext(), "下拉刷新结束");
//                        if (mPromptSwipeRefreshLayout != null && mPromptSwipeRefreshLayout.isRefreshing()) {
//                            mPromptSwipeRefreshLayout.setRefreshing(false);
//                            tipsLoadMoreAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }, 2000);
//            }
//        });
    }

    /**
     * 重新设置数据源
     *
     * @param
     * @param tipsBeans
     */
    public void setDataList(List<PointListBean.PointBean> tipsBeans) {
        if (datalist != null) {
            datalist = tipsBeans;
            if (tipsLoadMoreAdapter != null) {
                tipsLoadMoreAdapter.setDataList(datalist);
            }
        }

    }
}
