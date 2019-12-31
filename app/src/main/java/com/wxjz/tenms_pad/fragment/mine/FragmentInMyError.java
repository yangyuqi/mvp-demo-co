package com.wxjz.tenms_pad.fragment.mine;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.ErrorProblemBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.event.HaveErrorProblemEvent;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.adapter.ErrorProblemAdapter;
import com.wxjz.tenms_pad.mvp.contract.FragmentInMyErrorContract;
import com.wxjz.tenms_pad.mvp.presenter.FragmentInMyErrorPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by a on 2019/9/18.
 */

@SuppressLint("ValidFragment")
public class FragmentInMyError extends BaseMvpFragment<FragmentInMyErrorPresenter> implements FragmentInMyErrorContract.View {
    private int subId;
    private RecyclerView rvError;
    private ErrorProblemAdapter problemAdapter;
    private RelativeLayout rlEmpty;
    private String userId1;
    private MyErrorFragment fragment;
    private int levelId;
    private ErrorProblemBean errorProblemBean;
    private int page = 1;
    private List<ErrorProblemBean> problemBeans;

    @SuppressLint("ValidFragment")
    public FragmentInMyError(int subId, MyErrorFragment fragment) {
        this.subId = subId;
        this.fragment = fragment;
    }

    @Override
    protected FragmentInMyErrorPresenter createPresenter() {
        return new FragmentInMyErrorPresenter(this);
    }

    @Override
    protected void initView(View view) {
        rvError = view.findViewById(R.id.rv_error);
        rlEmpty = view.findViewById(R.id.rl_empty);

    }

    @Override
    protected void initData() {
        page = 1;
        UserInfoBean currentUserInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        userId1 = currentUserInfo.getUserId();
        levelId = CheckGradeDao.getInstance().queryleveId();
        /**
         * domtype=2 错题
         */
        mPresenter.getErrorProblem(userId1, subId == 0 ? null : subId, 2, page, Integer.MAX_VALUE, levelId);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_in_my_error;
    }

    public static FragmentInMyError getInstance(int subId, MyErrorFragment fragment) {
        return new FragmentInMyError(subId, fragment);
    }

    @Override
    public void onErrorProblem(final List<ErrorProblemBean> problemBeans) {
        this.problemBeans = problemBeans;
        if (problemBeans.size() == 0) {
            rvError.setVisibility(View.GONE);
            rlEmpty.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvError.setLayoutManager(linearLayoutManager);
        problemAdapter = new ErrorProblemAdapter(R.layout.layout_my_error_item, this.problemBeans, this);
        rvError.setAdapter(problemAdapter);
        problemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                errorProblemBean = problemBeans.get(position);
                mPresenter.getPlayAuthByVid(errorProblemBean.getVideAlipayVideoId());
            }
        });
//        problemAdapter.setEnableLoadMore(true);
//        problemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                page++;
//                mPresenter.getErrorProblem(userId1, subId == 0 ? null : subId, 2, page, 5, levelId);
//            }
//        }, rvError);
    }

    @Override
    public void onErrorProblemMore(List<ErrorProblemBean> problemBeans) {
        if (problemBeans.size() == 0) {
            problemAdapter.loadMoreEnd();
            return;
        } else {
            problemAdapter.addCheckList(problemBeans.size());
            int position = this.problemBeans.size() - 1;
            this.problemBeans.addAll(problemBeans);
            problemAdapter.notifyItemRangeInserted(position,problemBeans.size());
            problemAdapter.loadMoreComplete();
        }
    }


    @Override
    public void onDeleteError() {

        /**删除后刷新数据
         * domtype=2 错题
         */
        page = 1;
        mPresenter.getErrorProblem(userId1, subId == 0 ? null : subId, 2, page, Integer.MAX_VALUE, levelId);
        fragment.setManageButtonVisible(true);

    }

    @Override
    public void onPlayAuth(PlayAuthBean playAuthBean) {
        String gradeId = CheckGradeDao.getInstance().getGuestChooseGrade().getGradeId();
        JumpUtil.jump2VideoActivity(mContext, LandscapeVideoActivity.class, playAuthBean.getPlayAuth(), errorProblemBean.getVideAlipayVideoId(), errorProblemBean.getVideoId(),
                subId, String.valueOf(errorProblemBean.getChapterId()), String.valueOf(errorProblemBean.getSectionId()), gradeId, errorProblemBean.getVideoDuration());
    }

    public void deleteError(String ids) {
        if (ids.length() == 0) {

            return;
        }

        String id = ids.substring(0, ids.length() - 1);
        mPresenter.deleteError(id, 2);

    }

    /**
     * 显示条目上的checkbox
     *
     * @param show
     */
    public void showCheckBox(boolean show) {
        if (problemAdapter != null) {
            problemAdapter.setCheckBoxVisible(show);

        }

    }

    /**
     * 全选
     *
     * @param chooseAll
     */
    public void chooseAll(boolean chooseAll) {
        problemAdapter.checkAll(chooseAll);

    }

    /**
     * 删除选中的条目
     */
    public void deleteCheckedItem() {
        problemAdapter.deleteCheckItem();
    }

    /**
     * 获取是否有radiobutton被选中
     *
     * @return
     */
    public boolean getRadioCheckStatus() {
        return problemAdapter.getCheckStatus();
    }
}
