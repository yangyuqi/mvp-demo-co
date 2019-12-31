package com.wxjz.module_aliyun.dialogFragment;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.adapter.TerminologyAdapter;
import com.wxjz.module_aliyun.aliyun.listener.EndlessRecyclerOnScrollListener;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.aliyun.utils.FixedToastUtils;
import com.wxjz.module_aliyun.event.DialogDissmissEvent;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.TerminologyListBean;
import com.wxjz.module_base.db.bean.AnalysisBean;
import com.wxjz.module_base.db.bean.ExerciseDBBean;
import com.wxjz.module_base.db.dao.DialogManger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/3
 * Time: 14:47
 */
public class TerminologyDialog extends BaseDialog {

    private static TerminologyDialog mTerminologyDialog;

    private RecyclerView mKnowledgeAnalysisRecyclerView;

    private SwipeRefreshLayout mKnowledgeAnalysisRefreshLayout;

    private TerminologyAdapter terminologyAdapter;

    private List<PointListBean.PointBean> list = new ArrayList<>();

    //判断当前是否是正在上拉加载
    private boolean isLoadmore;

    public static TerminologyDialog newInstance() {
        if (mTerminologyDialog == null) {
            mTerminologyDialog = new TerminologyDialog();
        }
        return mTerminologyDialog;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_knowledge_analysis_dialog;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        initView(holder);
        initData(holder);
        initListener(holder, dialog);
    }

    private void initView(ViewHolder holder) {
        mKnowledgeAnalysisRecyclerView = (RecyclerView) holder.findView(R.id.recycler_view);
        mKnowledgeAnalysisRefreshLayout = (SwipeRefreshLayout) holder.findView(R.id.swipe_refresh_layout);
        //禁止下拉刷新
        mKnowledgeAnalysisRefreshLayout.setEnabled(false);
    }

    private void initData(ViewHolder holder) {
        terminologyAdapter = new TerminologyAdapter(list, getContext());
        mKnowledgeAnalysisRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mKnowledgeAnalysisRecyclerView.setAdapter(terminologyAdapter);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (isSendEventBus()) {
            EventBus.getDefault().post(new DialogDissmissEvent(true));
        }
    }

    private void initListener(final ViewHolder holder, BaseDialog dialog) {
        //点击了关闭按钮
        holder.setOnClickListener(R.id.iv_knowledge_analysis_close, new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (getDialog() != null && getDialog().isShowing()) {
                    if (!mKnowledgeAnalysisRefreshLayout.isRefreshing() && !isLoadmore) {
                        getDialog().dismiss();
                    } else {
                        Toast.makeText(getContext(), "你当前正在执行刷新操作，请稍后", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        mKnowledgeAnalysisRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mKnowledgeAnalysisRefreshLayout.isRefreshing() || isLoadmore) {
//                    return true;
//                }
//                return false;
//            }
//        });

        /**
         * 短按监听
         */
        terminologyAdapter.setOnItemTimeClickListener(new TerminologyAdapter.OnItemTimeClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId() == R.id.tv_recyclerview_content || view.getId() == R.id.tv_recyclerview_title) {

                } else if (view.getId() == R.id.tv_terminology_time) {
                    //点击进度之后，关闭当前的dialog
                    getDialog().dismiss();
                }

            }
        });
//        //上拉加载更多的监听
//        mKnowledgeAnalysisRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                isLoadmore = true;
//                terminologyAdapter.setLoadState(terminologyAdapter.LOADING);
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                terminologyAdapter.setLoadState(terminologyAdapter.LOADING_END);
//                                isLoadmore = false;
//                            }
//                        });
//                    }
//                }, 2000);
//            }
//
//            @Override
//            public void firstitemCanSee() {
//                mKnowledgeAnalysisRefreshLayout.setEnabled(false);
//            }
//
//            @Override
//            public void firstitemNotSee() {
//                mKnowledgeAnalysisRefreshLayout.setEnabled(true);
//            }
//        });
//        //下拉刷新的接口
//        mKnowledgeAnalysisRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                list.clear();
//                terminologyAdapter.clearData();
//                //延迟一秒关闭下拉刷新
//                mKnowledgeAnalysisRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        FixedToastUtils.show(getContext(), "下拉刷新结束");
//                        if (mKnowledgeAnalysisRefreshLayout != null && mKnowledgeAnalysisRefreshLayout.isRefreshing()) {
//                            mKnowledgeAnalysisRefreshLayout.setRefreshing(false);
//                        }
//                    }
//                }, 1000);
//            }
//        });
    }

    /**
     * 重新设置数据源
     *
     * @param
     * @param
     * @param terminologyBeans
     */
    public void setDataList(List<PointListBean.PointBean> terminologyBeans) {
        if (list != null) {
            list = terminologyBeans;
            if (terminologyAdapter != null) {
                terminologyAdapter.setDataList(list);
            }
        }

    }
}
