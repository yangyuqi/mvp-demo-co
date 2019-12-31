package com.wxjz.tenms_pad.fragment.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.ClassRankBean;
import com.wxjz.module_base.bean.GradeRankBean;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.adapter.ClassRankAdapter;
import com.wxjz.tenms_pad.adapter.GradeRankAdapter;
import com.wxjz.tenms_pad.mvp.contract.LearnDurationContract;
import com.wxjz.tenms_pad.mvp.contract.MyErrorContract;
import com.wxjz.tenms_pad.mvp.presenter.LearnDurationPresenter;
import com.wxjz.tenms_pad.mvp.presenter.MyErrorPresenter;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 学习时长
 */

public class LearnDurationFragment extends BaseMvpFragment<LearnDurationPresenter> implements LearnDurationContract.View {
    /**
     * 本日
     */
    private final String TODAY = "today";
    /**
     * 本周
     */
    private final String TOWEEK = "thisWeek";
    /**
     * 累计
     */
    private final String TOTAL = "";
    private RecyclerView rvClassRank;
    private RadioGroup rgClassRank;
    private RadioGroup rgGradeRank;
    private RecyclerView rvGradeRank;
    private TextView tvTotalLearnTime;
    private TextView tvClassRank;
    private TextView tvTodayLearnTime;
    private LinearLayout llContent;

    public static LearnDurationFragment getInstance() {
        return new LearnDurationFragment();
    }

    @Override
    protected LearnDurationPresenter createPresenter() {
        return new LearnDurationPresenter(this);
    }

    @Override
    protected void initView(View view) {
        rvClassRank = view.findViewById(R.id.rv_class_rank);
        rgClassRank = view.findViewById(R.id.rg_class_rank);
        rvGradeRank = view.findViewById(R.id.rv_grade_rank);
        rgGradeRank = view.findViewById(R.id.rg_grade_rank);
        tvTotalLearnTime = view.findViewById(R.id.tv_total_learn_time);
        tvTodayLearnTime = view.findViewById(R.id.tv_today_learn_time);
        tvClassRank = view.findViewById(R.id.tv_class_rank);
        llContent = view.findViewById(R.id.ll_content);
        initListener();

    }

    private void initListener() {
        rgClassRank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_class_today:
                        mPresenter.getClassRank(TODAY);
                        break;
                    case R.id.rb_class_week:
                        mPresenter.getClassRank(TOWEEK);
                        break;
                    case R.id.rb_class_total:
                        mPresenter.getClassRank(TOTAL);
                        break;
                }
            }
        });
        rgGradeRank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_grade_today:
                        mPresenter.getGradeRank(TODAY);
                        break;
                    case R.id.rb_grade_week:
                        mPresenter.getGradeRank(TOWEEK);
                        break;
                    case R.id.rb_grade_total:
                        mPresenter.getGradeRank(TOTAL);
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getClassRank(TODAY);
        mPresenter.getGradeRank(TODAY);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_learn_duration;
    }

    @Override
    public void onClassRank(ClassRankBean classRankBeans, String data) {
        if (data.equals(TODAY)) {
            double total_learn_time = Math.floor((double) classRankBeans.getLearingTime() / 60);
            DecimalFormat decimalFormat = new DecimalFormat("0");
            String format = decimalFormat.format(total_learn_time);
            tvTotalLearnTime.setText(String.valueOf(format));
            tvClassRank.setText(String.valueOf(classRankBeans.getRank()));
            double floor = Math.floor((double) classRankBeans.getTodayTime() / 60);
            String format1 = decimalFormat.format(floor);
            tvTodayLearnTime.setText(format1);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        List<ClassRankBean.ListBean> list = classRankBeans.getList();
        ClassRankAdapter classRankAdapter = new ClassRankAdapter(R.layout.layout_class_rank_item, list);
        rvClassRank.setLayoutManager(layoutManager);
        rvClassRank.setAdapter(classRankAdapter);


    }

    @Override
    public void onGradeRank(List<GradeRankBean> gradeRankBeans, String data) {
        if (gradeRankBeans.size() == 0) {
            // setEmptyLayoutVisible(true);
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        GradeRankAdapter gradeRankAdapter = new GradeRankAdapter(R.layout.layout_grade_rank_item, gradeRankBeans);
        rvGradeRank.setLayoutManager(layoutManager);
        rvGradeRank.setAdapter(gradeRankAdapter);

    }

    private void setEmptyLayoutVisible(boolean visible) {
        llContent.setVisibility(visible ? View.GONE : View.VISIBLE);
    }
}
