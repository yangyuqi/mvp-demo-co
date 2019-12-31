package com.wxjz.module_base.dialog;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.R;
import com.wxjz.module_base.bean.LevelListBean;

import java.util.List;

/**
 * @ClassName GradeClassificationAdapter
 * @Description TODO
 * @Author liufang
 * @Date 2019-10-30 10:10
 * @Version 1.0
 */
public class GradeClassificationAdapter extends BaseQuickAdapter<LevelListBean.GradeBean, BaseViewHolder> {

    private int mcurrentposition = -1;

    public GradeClassificationAdapter(int layoutResId, @Nullable List<LevelListBean.GradeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LevelListBean.GradeBean item) {
        if (!TextUtils.isEmpty(item.getGradeName())) {
            helper.setText(R.id.tv_item_grade, item.getGradeName());
        }
        int position = helper.getLayoutPosition();
        if (position == mcurrentposition) {
            helper.itemView.findViewById(R.id.iv_select).setVisibility(View.VISIBLE);
            helper.setBackgroundColor(R.id.ll_item_layout, mContext.getResources().getColor(R.color.yellowFDCF00));
            helper.setTextColor(R.id.tv_item_grade, mContext.getResources().getColor(R.color.black));
        } else {
            helper.itemView.findViewById(R.id.iv_select).setVisibility(View.GONE);
            helper.setBackgroundColor(R.id.ll_item_layout, mContext.getResources().getColor(R.color.grayF2F2F2));
            helper.setTextColor(R.id.tv_item_grade, mContext.getResources().getColor(R.color.gray909399));
        }
    }

    public void setMcurrentposition(int mcurrentposition) {
        this.mcurrentposition = mcurrentposition;
        notifyDataSetChanged();
    }
}
