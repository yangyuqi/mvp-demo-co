package com.wxjz.tenms_pad.fragment.mine;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.LearnRecordBean;
import com.wxjz.module_base.db.bean.GuestChooseGrade;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.CourseDetailActivity;
import com.wxjz.tenms_pad.adapter.LearnRecordAdapter;
import com.wxjz.tenms_pad.mvp.contract.DownloadManageContract;
import com.wxjz.tenms_pad.mvp.contract.LearnRecordContract;
import com.wxjz.tenms_pad.mvp.presenter.DownloadManagePresenter;
import com.wxjz.tenms_pad.mvp.presenter.LearnRecordPresenter;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 学习记录
 */

public class LearnRecordFragment extends BaseMvpFragment<LearnRecordPresenter> implements LearnRecordContract.View, View.OnClickListener {

    private RecyclerView rvLearnRecord;
    private String userId = "";
    private TextView tv_choose_all;
    private TextView tv_delete;
    private LearnRecordAdapter mRecordAdapter;
    private boolean showCheckbox = false;
    private RelativeLayout rl_empty;
    private LinearLayout ll_content;
    private TextView tvLearnDuration;
    private int levelId;
    private LinearLayout llChoose;
    private TextView tvManage;

    public static LearnRecordFragment getInstance() {
        return new LearnRecordFragment();
    }

    @Override
    protected LearnRecordPresenter createPresenter() {
        return new LearnRecordPresenter(this);
    }

    @Override
    protected void initView(View view) {
        rvLearnRecord = view.findViewById(R.id.rv_learn_record);
        tvLearnDuration = view.findViewById(R.id.tv_learn_duration);
        tv_choose_all = view.findViewById(R.id.tv_choose_all);
        tv_delete = view.findViewById(R.id.tv_delete);
        rl_empty = view.findViewById(R.id.rl_empty);
        ll_content = view.findViewById(R.id.ll_content);
        tvManage = view.findViewById(R.id.tv_manage);
        llChoose = view.findViewById(R.id.llChoose);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_choose_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tvManage.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        setTvDeleteClickable(false);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        levelId = CheckGradeDao.getInstance().queryleveId();

        mPresenter.getLearnRecord(levelId);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_learn_record;
    }

    @Override
    public void onLearnRecord(LearnRecordBean recordBeanList) {
        int time = (int) Math.floor((double) recordBeanList.getTodayTime() / 60);
        tvLearnDuration.setText(String.valueOf(time));
        final List<LearnRecordBean.ListBean> list = recordBeanList.getList();
        if (list.size() > 0) {
            setEmptyLayoutVisible(false);
        } else {
            setEmptyLayoutVisible(true);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        rvLearnRecord.setLayoutManager(layoutManager);
        mRecordAdapter = new LearnRecordAdapter(R.layout.layout_learn_record_item, list, this);
        rvLearnRecord.setAdapter(mRecordAdapter);
        mRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (showCheckbox) {
                    mRecordAdapter.setPositionCheck(position);
                } else {
                    String gradeId = CheckGradeDao.getInstance().getGuestChooseGrade().getGradeId();
                    LearnRecordBean.ListBean listBean = list.get(position);
                    // mPresenter.addCourseClickCount(list.get(position).getId());
                    JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, listBean.getId(), listBean.getChapterId(), listBean.getSectionId(), gradeId);

                }
            }
        });
    }

    @Override
    public void onDeleteLearnRecord() {
        //删除完毕 刷新数据
        mPresenter.getLearnRecord(levelId);
        setTvDeleteClickable(false);
        setTvManageVisible(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_choose_all:
                mRecordAdapter.showCheckBox(true, true);
                showCheckbox = true;
                break;
            case R.id.tv_delete:
                mRecordAdapter.removeCheckedItem();
                break;
            case R.id.tv_manage:
                setTvManageVisible(false);
                break;
            case R.id.tv_cancel:
                setTvManageVisible(true);
                break;
        }

    }

    private void setTvManageVisible(boolean visible) {
        tvManage.setVisibility(visible ? View.VISIBLE : View.GONE);
        llChoose.setVisibility(visible ? View.GONE : View.VISIBLE);

        showCheckbox = !visible;
        boolean show = mRecordAdapter.showCheckBox(showCheckbox, false);
        if (show) {
            setTvDeleteClickable(showCheckbox);
        } else {
            setTvDeleteClickable(false);
        }
    }

    /**
     * 设置删除按钮状态
     *
     * @param clickable
     */
    public void setTvDeleteClickable(boolean clickable) {
        tv_delete.setClickable(clickable);
        tv_delete.setTextColor(clickable ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray909399));
    }

    public void setEmptyLayoutVisible(boolean visible) {
        ll_content.setVisibility(visible ? View.GONE : View.VISIBLE);
        rl_empty.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 删除学习记录
     *
     * @param courseId
     */
    public void deleteLearnRecord(String courseId) {
        if (TextUtils.isEmpty(courseId)) {
            return;
        }
        String courseIds = courseId.substring(0, courseId.length() - 1);
        mPresenter.deleteLearnRecord(courseIds);

    }
}
