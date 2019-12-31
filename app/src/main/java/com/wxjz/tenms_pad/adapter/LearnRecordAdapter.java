package com.wxjz.tenms_pad.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.bean.LearnRecordBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.fragment.mine.LearnRecordFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by a on 2019/9/18.
 */

public class LearnRecordAdapter extends BaseQuickAdapter<LearnRecordBean.ListBean, BaseViewHolder> {
    private LearnRecordFragment mFragment;
    private boolean showCheckbox;
    /**
     * checkbox状态集合
     */
    private List<Boolean> mCheckStatusList;
    private List<LearnRecordBean.ListBean> recordList;

    public LearnRecordAdapter(int layoutResId, @Nullable List<LearnRecordBean.ListBean> data, LearnRecordFragment fragment) {
        super(layoutResId, data);
        this.recordList = data;
        mCheckStatusList = new ArrayList<>(data.size());
        this.mFragment = fragment;
        if (data.size() > 0) {
            //默认状态下是为选中状态
            for (int i = 0; i < data.size(); i++) {
                mCheckStatusList.add(i, false);
            }
        }

    }

    @Override
    protected void convert(BaseViewHolder helper, LearnRecordBean.ListBean item) {
        if (!TextUtils.isEmpty(item.getCoverUrl())) {
            Glide.with(mContext).load(item.getCoverUrl()).apply(RequestOptions.bitmapTransform(new RoundedCorners(5))).placeholder
                    (R.drawable.example2).error(R.drawable.example2).into((ImageView) helper.getView(R.id.iv_cover));

        }
        String clickCount = String.valueOf(item.getClickCount());
        if (!TextUtils.isEmpty(clickCount)) {
            helper.setText(R.id.tv_play_count, clickCount);

        } else {
            helper.setText(R.id.tv_play_count, "0");
        }
        String courseName = item.getVideoTitle();
        if (!TextUtils.isEmpty(courseName)) {
            helper.setText(R.id.tv_title, courseName);
        } else {
            helper.setText(R.id.tv_title, "无数据");
        }

        HashMap<String, Integer> colorMap = BaseApplication.getColorMap();
        if (!colorMap.containsKey(item.getSubjectName())) {
            //去1到10随机数
            int random = (int) (1 + Math.random() * (9));
            String key = (String) colorMap.keySet().toArray()[random];
            helper.setBackgroundRes(R.id.iv_course, colorMap.get(key));
        } else {
            int color = colorMap.get(item.getSubjectName());
            helper.setBackgroundRes(R.id.iv_course, color);

        }
        helper.setText(R.id.tv_sub_name, item.getSubjectName());


        CheckBox checkBox = helper.getView(R.id.cb_record);
        checkBox.setOnCheckedChangeListener(null);
        final int position = helper.getLayoutPosition();
        checkBox.setChecked(mCheckStatusList.get(position));
        if (showCheckbox) {
            checkBox.setVisibility(View.VISIBLE);
            checkBox.setChecked(mCheckStatusList.get(position));
        } else {
            checkBox.setChecked(false);
            checkBox.setVisibility(View.GONE);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //修改集合中checkbox状态
                mCheckStatusList.set(position, isChecked);
                updateCheckStatus();
            }
        });

    }

    private void updateCheckStatus() {
        for (int i = 0; i < mCheckStatusList.size(); i++) {
            if (mCheckStatusList.get(i)) {
                mFragment.setTvDeleteClickable(true);
                return;
            }
        }
        mFragment.setTvDeleteClickable(false);

    }

    /**
     * 显示隐藏check box
     *
     * @param showCheckbox
     */
    public boolean showCheckBox(boolean showCheckbox, boolean check) {
        if (mCheckStatusList.size() == 0) {
            return false;
        }
        this.showCheckbox = showCheckbox;
        for (int i = 0; i < mCheckStatusList.size(); i++) {
            mCheckStatusList.set(i, check);
        }
        notifyDataSetChanged();
        return true;
    }

    /**
     * 删除选装的条目
     */
    public void removeCheckedItem() {
        String ids = "";
        for (int i = mCheckStatusList.size() - 1; i >= 0; i--) {
            if (mCheckStatusList.get(i)) {
                ids += recordList.get(i).getId() + "-";
            }
        }
        mFragment.deleteLearnRecord(ids);
        if (mCheckStatusList.size() == 0) {
            mFragment.setEmptyLayoutVisible(true);
        }
    }

    public void setPositionCheck(int position) {
        boolean isCheck = mCheckStatusList.get(position);
        mCheckStatusList.set(position, !isCheck);
        notifyItemChanged(position);
    }
}
