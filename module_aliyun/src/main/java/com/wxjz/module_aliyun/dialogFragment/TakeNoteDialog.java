package com.wxjz.module_aliyun.dialogFragment;

import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_aliyun.adapter.TakeNotesLoadMoreAdapter;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.event.DialogDissmissEvent;
import com.wxjz.module_base.bean.PointListBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/29
 * Time: 11:25
 * 笔记Dialog
 */
public class TakeNoteDialog extends BaseDialog {

    private static TakeNoteDialog mTakeNoteDialog;

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TakeNotesLoadMoreAdapter takeNotesLoadMoreAdapter;

    private List<PointListBean.PointBean> list = new ArrayList<>();
    //判断当前是否是正在上拉加载
    private boolean isLoadmore;
    //获取当前视频的时长
    private long progcesscurrent;
    /**
     * 是否删除笔记对话框
     */
    private ConfirmdeleteDialog confirmdeleteDialog;
    private onDeleteNotesItemLisenter onDeleteNotesItemLisenter;
    /**
     * 添加/修改笔记对话框
     */
    private TakeNoteSendDialog takeNoteSendDialog;

    /**
     * 保存笔记的对话框
     */
    private onSaveNoteClickListener onSaveNoteClickListener;


    public static TakeNoteDialog newInstance() {
        if (mTakeNoteDialog == null) {
            mTakeNoteDialog = new TakeNoteDialog();
        }
        return mTakeNoteDialog;
    }

    public List<PointListBean.PointBean> getList() {
        return list;
    }

    public long getProgcesscurrent() {
        return progcesscurrent;
    }

    public void setProgcesscurrent(long progcesscurrent) {
        this.progcesscurrent = progcesscurrent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_take_notes;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        initView(holder);
        initData(holder);
        initListener(holder, dialog);

    }


    private void initView(ViewHolder holder) {
        mRecyclerView = (RecyclerView) holder.findView(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) holder.findView(R.id.swipe_refresh_layout);
        confirmdeleteDialog = new ConfirmdeleteDialog();
        //禁止下拉刷新
        mSwipeRefreshLayout.setEnabled(false);
    }


    private void initData(ViewHolder holder) {
        takeNotesLoadMoreAdapter = new TakeNotesLoadMoreAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(takeNotesLoadMoreAdapter);

        takeNotesLoadMoreAdapter.setItemViewClickListener(new TakeNotesLoadMoreAdapter.AdapterOnItemViewClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                if (view.getId() == R.id.tv_note_time) {
                    //点击进度之后，关闭当前的dialog
                    getDialog().dismiss();
                } else if (view.getId() == R.id.tv_content_delete) {
                    //点击了删除数据
                    initConfirmdeleteDialog(position);
                } else if (view.getId() == R.id.tv_content_edit) {
                    //点击了修改内容
                    initTakeNoteSendDialog(1, position, list.get(position).getDomContent());
                }
            }

        });
    }

    private void initListener(ViewHolder holder, final BaseDialog dialog) {
//        //上拉加载或者下拉刷新的时候屏蔽滑动事件
//        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mSwipeRefreshLayout.isRefreshing() || isLoadmore) {
//                    return true;
//                }
//                return false;
//            }
//        });
//        //上拉加载更多的监听
//        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                isLoadmore = true;
//                takeNotesLoadMoreAdapter.setLoadState(takeNotesLoadMoreAdapter.LOADING);
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                takeNotesLoadMoreAdapter.setLoadState(takeNotesLoadMoreAdapter.LOADING_END);
//                                isLoadmore = false;
//                            }
//                        });
//                    }
//                }, 2000);
//            }
//
//            @Override
//            public void firstitemCanSee() {
//                mSwipeRefreshLayout.setEnabled(false);
//            }
//
//            @Override
//            public void firstitemNotSee() {
//                mSwipeRefreshLayout.setEnabled(true);
//            }
//        });

//        //下拉刷新的接口
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //延迟一秒关闭下拉刷新
//                mSwipeRefreshLayout.setEnabled(true);
//                mSwipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        FixedToastUtils.show(getContext(), "下拉刷新结束");
//                        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
//                            mSwipeRefreshLayout.setEnabled(false);
//                            mSwipeRefreshLayout.setRefreshing(false);
//                            takeNotesLoadMoreAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }, 2000);
//            }
//        });

        //点击了添加笔记的接口
        holder.setOnClickListener(R.id.tv_add_new_notes, new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                initTakeNoteSendDialog(0, 0, "");
            }
        });

        //点击了关闭按钮
        holder.setOnClickListener(R.id.iv_dialog_take_notes_close, new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (getDialog() != null && getDialog().isShowing()) {
                    if (!mSwipeRefreshLayout.isRefreshing() && !isLoadmore) {
                        getDialog().dismiss();
                    } else {

                    }
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

    /**
     * 重新设置数据源
     *
     * @param datalist
     */
    public void setDataList(List<PointListBean.PointBean> datalist) {
        if (datalist != null) {
            list = datalist;
            if (takeNotesLoadMoreAdapter != null) {
                takeNotesLoadMoreAdapter.setDataList(datalist);
            }
        }

    }

    /**
     * 添加查询的数据源
     *
     * @param datalist
     */
    public void addDataList(List<PointListBean.PointBean> datalist) {
        if (datalist != null) {
            if (takeNotesLoadMoreAdapter != null) {
                takeNotesLoadMoreAdapter.addDataList(datalist);
            }

        }
    }

    /**
     * 点击了删除笔记或者修改笔记按钮
     */
    public interface onDeleteNotesItemLisenter {
        void deleteNoteItem(int position);
    }

    public void setOnDeleteNotesItemLisenter(TakeNoteDialog.onDeleteNotesItemLisenter onDeleteNotesItemLisenter) {
        this.onDeleteNotesItemLisenter = onDeleteNotesItemLisenter;
    }

    /**
     * 是否删除笔记对话框
     *
     * @param position
     */
    private void initConfirmdeleteDialog(final int position) {
        confirmdeleteDialog = (ConfirmdeleteDialog) ConfirmdeleteDialog.newInstance()
                .setOutCancel(false)
                .show(getActivity().getSupportFragmentManager());
        confirmdeleteDialog.setListener(new ConfirmdeleteDialog.OnItemViewClickListener() {
            @Override
            public void OnItemClick(View view) {
                if (view.getId() == R.id.tv_btn_yes) {
                    if (onDeleteNotesItemLisenter != null) {
                        onDeleteNotesItemLisenter.deleteNoteItem(list.get(position).getId());
                    }
                }
            }
        });
    }

    /**
     * 添加笔记对话框
     *
     * @type 类型，0是添加笔记，1是删除笔记
     * @position 是哪一列的笔记被修改了
     * @content 如果需要修改，提前传入内容
     */
    private void initTakeNoteSendDialog(int type, int position, String content) {
        takeNoteSendDialog = (TakeNoteSendDialog) TakeNoteSendDialog.newInstance().setOutCancel(false)
                .setAnimStyle(R.style.dialogWindowAnim)
                .setSize(LandscapeVideoActivity.getScreenWidth(getContext()), LandscapeVideoActivity.getScreenHeight(getContext()))
                .setGravity(Gravity.LEFT);
        takeNoteSendDialog.setType(type);
        takeNoteSendDialog.setPosition(position);
        takeNoteSendDialog.setContent(content);
        takeNoteSendDialog.show(getActivity().getSupportFragmentManager());
        takeNoteSendDialog.setOnSaveNoteClick(new TakeNoteSendDialog.onSaveNoteClick() {
            @Override
            public void onSaveNotes(EditText view, int type, int position, String context, Dialog dialog) {
                if (onSaveNoteClickListener != null) {
                    if (list != null && list.size() > 0) {
                        onSaveNoteClickListener.onSaveNote(type, list.get(position).getId(), progcesscurrent, context, dialog);
                    } else {

                        onSaveNoteClickListener.onSaveNote(type, 0, progcesscurrent, context, dialog);
                    }
                }
            }
        });
    }

    public interface onSaveNoteClickListener {
        /**
         * @param type        是更新还是添加
         * @param id          更新对应的主键id
         * @param currenttime 当前的视频进度，添加时候用
         * @param content     内容
         * @param dialog
         */
        void onSaveNote(int type, int id, long currenttime, String content, Dialog dialog);
    }

    public void setOnSaveNoteClickListener(TakeNoteDialog.onSaveNoteClickListener onSaveNoteClickListener) {
        this.onSaveNoteClickListener = onSaveNoteClickListener;
    }
}
