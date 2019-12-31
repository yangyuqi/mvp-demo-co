package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_base.db.bean.SubjectHomeBean;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * @ClassName FestivalAdapter
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-01 17:51
 * @Version 1.0
 */
public class FestivalAdapter extends BaseQuickAdapter<SubjectHomeBean.SectionBean, BaseViewHolder> {

    int position = -1;

    public FestivalAdapter(int layoutResId, @Nullable List<SubjectHomeBean.SectionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectHomeBean.SectionBean item) {
        if (!TextUtils.isEmpty((CharSequence) item.getSectionName())) {
            helper.setText(R.id.tv_chapter_title, (CharSequence) item.getSectionName());
        }
        int layoutposition = helper.getLayoutPosition();
        if (position != layoutposition) {
            helper.setBackgroundColor(R.id.view_select, mContext.getResources().getColor(R.color.whiteF5F6F8));
            helper.setTextColor(R.id.tv_chapter_title, mContext.getResources().getColor(R.color.dark_gray));
        } else {
            helper.setBackgroundColor(R.id.view_select, mContext.getResources().getColor(R.color.yellowFBD80B));
            helper.setTextColor(R.id.tv_chapter_title, mContext.getResources().getColor(R.color.black));
        }
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }
}
