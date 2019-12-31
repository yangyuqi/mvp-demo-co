package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.ClassRankBean;
import com.wxjz.module_base.bean.CourseListItemBean;
import com.wxjz.tenms_pad.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 本班学习时长
 */

public class ClassRankAdapter extends BaseQuickAdapter<ClassRankBean.ListBean, BaseViewHolder> {
    public ClassRankAdapter(int layoutResId, @Nullable List<ClassRankBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassRankBean.ListBean item) {
        int position = helper.getLayoutPosition();
        if (position == 0) {
            helper.setImageResource(R.id.iv_rank, R.drawable.first);
        } else if (position == 1) {
            helper.setImageResource(R.id.iv_rank, R.drawable.second);
        } else if (position == 2) {
            helper.setImageResource(R.id.iv_rank, R.drawable.third);
        } else {
            helper.setImageResource(R.id.iv_rank, R.drawable.after_third);
        }
        helper.setText(R.id.tv_nick_name, item.getUserName());
        double studyTime = (double) item.getLearingRealTime() / 60;
        double floor = Math.floor(studyTime);
        DecimalFormat decimalFormat = new DecimalFormat("0"
        );
        String format = decimalFormat.format(floor);
        helper.setText(R.id.tv_learn_duration, format + "分钟");
        helper.setText(R.id.tv_learn_num, item.getCourseCount() + "课");
    }
}
