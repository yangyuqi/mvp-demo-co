package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.ClassRankBean;
import com.wxjz.module_base.bean.GradeRankBean;
import com.wxjz.tenms_pad.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 本班学习时长
 */

public class GradeRankAdapter extends BaseQuickAdapter<GradeRankBean, BaseViewHolder> {

    /**
     * 第一名的学习时长作为进度条的length
     */
    private int mStudyTime;

    public GradeRankAdapter(int layoutResId, @Nullable List<GradeRankBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GradeRankBean item) {
        int position = helper.getLayoutPosition();
        if (position == 0) {
            mStudyTime = (int) Math.round((double) item.getLearingRealTime() / 60);
            helper.setImageResource(R.id.iv_rank, R.drawable.first);
        } else if (position == 1) {
            helper.setImageResource(R.id.iv_rank, R.drawable.second);
        } else if (position == 2) {
            helper.setImageResource(R.id.iv_rank, R.drawable.third);
        } else {
            helper.setImageResource(R.id.iv_rank, R.drawable.after_third);
        }
        helper.setText(R.id.tv_class, item.getGradeName() + item.getClassName() + "班");
        double studyTime = (double) item.getLearingRealTime() / 60;
        double floor = Math.floor(studyTime);
        DecimalFormat decimalFormat = new DecimalFormat("0");
        String format = decimalFormat.format(floor);
        helper.setText(R.id.tv_duration, format + "分钟");
        helper.setProgress(R.id.progress_learn, (int) Math.round(studyTime), mStudyTime);
    }
}
