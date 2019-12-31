package com.wxjz.module_aliyun.dialogFragment;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.adapter.ExerciseLoadMoreAdapter;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.event.DialogDissmissEvent;
import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.bean.AnswerQuestionBean;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.http.api.AliyunVideoApi;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/5
 * Time: 8:45
 */
public class ExerciseDialog extends BaseDialog {
    private static ExerciseDialog mExerciseDialogDialog;

    private RecyclerView mExerciseDialogRecyclerView;

    private SwipeRefreshLayout mExerciseDialogRefreshLayout;

    private ExerciseLoadMoreAdapter mExerciseLoadMoreAdapter;

    private List<PointListBean.PointBean> list = new ArrayList<>();

    private String userId;


    //判断当前是否是正在上拉加载
    private boolean isLoadmore;

    public OnAnswerQuestionCheck onAnswerQuestionCheck;

    public OnAnswerResult onAnswerResult;

    private OnTextToSpeek onTextToSpeek;

    public static ExerciseDialog newInstance() {
        if (mExerciseDialogDialog == null) {
            mExerciseDialogDialog = new ExerciseDialog();
        }
        return mExerciseDialogDialog;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_exercise;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        initView(holder);
        initData(holder);
        initListener(holder, dialog);
    }

    private void initView(ViewHolder holder) {
        UserInfoBean userInfoBean = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfoBean != null) {
            userId = userInfoBean.getUserId();
        }
        mExerciseDialogRecyclerView = (RecyclerView) holder.findView(R.id.recycler_view);
        mExerciseDialogRefreshLayout = (SwipeRefreshLayout) holder.findView(R.id.swipe_refresh_layout);
        //禁止下拉刷新
        mExerciseDialogRefreshLayout.setEnabled(false);
    }

    private void initData(ViewHolder holder) {
        mExerciseLoadMoreAdapter = new ExerciseLoadMoreAdapter(list, getContext());
        mExerciseDialogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mExerciseDialogRecyclerView.setAdapter(mExerciseLoadMoreAdapter);
        /**
         * 提交问题选项后回调的接口
         */
        mExerciseLoadMoreAdapter.setOnAnswerQuestionCheck(new ExerciseLoadMoreAdapter.OnAnswerQuestionCheck() {
            @Override
            public void OnAnsweCheck(String answer, int position, int id, String rightAnswer) {
                if (!TextUtils.isEmpty(userId)) {
                    getInsertUserAnswer(userId, id, answer, position);
                }
            }
        });

        mExerciseLoadMoreAdapter.setOnTextToSpeech(new ExerciseLoadMoreAdapter.OnTextToSpeech() {
            @Override
            public void OnSpeech(String domContent) {
                if (onTextToSpeek != null) {
                    onTextToSpeek.OnSpeek(domContent);
                }
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (isSendEventBus()) {
            EventBus.getDefault().post(new DialogDissmissEvent(true, BasisConstants.EXERCISE_TYPE));
        }
    }

    private void initListener(ViewHolder holder, BaseDialog dialog) {
        //点击了关闭按钮
        holder.setOnClickListener(R.id.iv_exercise_close, new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (getDialog() != null && getDialog().isShowing()) {
                    if (!mExerciseDialogRefreshLayout.isRefreshing() && !isLoadmore) {
                        getDialog().dismiss();
                    } else {
                        Toast.makeText(getContext(), "你当前正在执行刷新操作，请稍后", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mExerciseDialogRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mExerciseDialogRefreshLayout.isRefreshing() || isLoadmore) {
                    return true;
                }
                return false;
            }
        });

//        //上拉加载更多的监听
//        mExerciseDialogRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
//            @Override
//            public void onLoadMore() {
//                isLoadmore = true;
//                mExerciseLoadMoreAdapter.setLoadState(mExerciseLoadMoreAdapter.LOADING);
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mExerciseLoadMoreAdapter.setLoadState(mExerciseLoadMoreAdapter.LOADING_END);
//                                isLoadmore = false;
//                            }
//                        });
//                    }
//                }, 2000);
//            }
//
//            @Override
//            public void firstitemCanSee() {
//                mExerciseDialogRefreshLayout.setEnabled(false);
//            }
//
//            @Override
//            public void firstitemNotSee() {
//                mExerciseDialogRefreshLayout.setEnabled(true);
//            }
//        });
//        //下拉刷新的接口
//        mExerciseDialogRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                list.clear();
//                mExerciseLoadMoreAdapter.clearData();
//                //延迟一秒关闭下拉刷新
//                mExerciseDialogRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        FixedToastUtils.show(getContext(), "下拉刷新结束");
//                        if (mExerciseDialogRefreshLayout != null && mExerciseDialogRefreshLayout.isRefreshing()) {
//                            mExerciseDialogRefreshLayout.setRefreshing(false);
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
     * @param
     * @param exerciseBeans
     */
    public void setDataList(List<PointListBean.PointBean> exerciseBeans) {
        if (list != null) {
            list = exerciseBeans;
            if (mExerciseLoadMoreAdapter != null) {
                mExerciseLoadMoreAdapter.setDataList(list);
            }
        }
    }

    /**
     * 调接口去回答题目
     *
     * @param userId
     * @param domId
     * @param userAnswer
     * @param position
     */
    private void getInsertUserAnswer(String userId, final int domId, final String userAnswer, final int position) {
        makeRequest(create(AliyunVideoApi.class).getInsertUserAnswer(userId, domId, userAnswer), new BaseObserver<AnswerQuestionBean>() {
            @Override
            protected void onSuccess(AnswerQuestionBean answerQuestionBean) {
                if (answerQuestionBean != null) {
                    //去刷新UI
                    list.get(position).setUserAnswer(userAnswer);
                    list.get(position).setIsRight(answerQuestionBean.getIsRight());
                    list.get(position).setIsAnswer(1);
                    mExerciseLoadMoreAdapter.notifyItemChanged(position);
                    //去更新外面下标点位的数据
                    if (onAnswerResult != null) {
                        onAnswerResult.onAnswerResult(domId, userAnswer, answerQuestionBean.getIsRight());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                //去刷新UI
                list.get(position).setUserAnswer(userAnswer);
                list.get(position).setIsRight(0);
                list.get(position).setIsAnswer(1);
                mExerciseLoadMoreAdapter.notifyItemChanged(position);
                //去更新外面下标点位的数据
                if (onAnswerResult != null) {
                    onAnswerResult.onAnswerResult(domId, userAnswer, 0);
                }
                super.onError(e);
            }
        });
    }

    /**
     * 自定义方法去更新UI
     *
     * @param exercisePosition
     * @param exerciseAnswer
     */
    public void updateItemUI(int exercisePosition, String exerciseAnswer) {
        if (mExerciseLoadMoreAdapter != null) {
            mExerciseLoadMoreAdapter.updateItemUi(exercisePosition, exerciseAnswer);
        }
    }


    public interface OnAnswerQuestionCheck {
        void onAnswer(String answer, int position, int id, int inAllpointindex, String rightAnswer);
    }

    public void setOnAnswerQuestionCheck(OnAnswerQuestionCheck onAnswerQuestionCheck) {
        this.onAnswerQuestionCheck = onAnswerQuestionCheck;
    }


    public interface OnAnswerResult {
        void onAnswerResult(int domId, String userAnswer, int isRight);
    }

    public void setOnAnswerResult(OnAnswerResult onAnswerResult) {
        this.onAnswerResult = onAnswerResult;
    }

    public interface OnTextToSpeek {
        void OnSpeek(String domContent);
    }

    public void setOnTextToSpeek(OnTextToSpeek onTextToSpeek) {
        this.onTextToSpeek = onTextToSpeek;
    }
}
