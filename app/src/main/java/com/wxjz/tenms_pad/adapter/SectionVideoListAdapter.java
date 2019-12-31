package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.db.bean.SubjectSectionBean;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * @ClassName SectionVideoListAdapter
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-04 09:21
 * @Version 1.0
 */
public class SectionVideoListAdapter extends BaseQuickAdapter<SubjectSectionBean, BaseViewHolder> {


    public SectionVideoListAdapter(int layoutResId, @Nullable List<SubjectSectionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectSectionBean item) {
        String coverUrl = (String) item.getCoverUrl();
        String clickCount = String.valueOf(new Double((Double) item.getClickCount()).intValue());
        String courseName = (String) item.getVideoTitle();
        boolean isFree = (boolean) item.getFree();
        if (!TextUtils.isEmpty(coverUrl)) {
            Glide.with(mContext).load(coverUrl).apply(RequestOptions.bitmapTransform(new RoundedCorners(10))).placeholder
                    (R.drawable.example2).error(R.drawable.example2).into((ImageView) helper.getView(R.id.iv_cover));
        }
        if (!TextUtils.isEmpty(clickCount)) {
            helper.setText(R.id.tv_play_count, clickCount);

        } else {
            helper.setText(R.id.tv_play_count, "0");
        }
        if (!TextUtils.isEmpty(courseName)) {
            helper.setText(R.id.tv_title, courseName);
        } else {
            helper.setText(R.id.tv_title, "无数据");
        }
        if (isFree) {
            helper.setImageDrawable(R.id.iv_course, mContext.getResources().getDrawable(R.drawable.shape_yellow_fdcf00_bg));
            helper.setText(R.id.tv_sub_name, "免费");
        } else {
            helper.setImageDrawable(R.id.iv_course, mContext.getResources().getDrawable(R.drawable.shape_yellow_fdcf00_bg));
            helper.setText(R.id.tv_sub_name, "vip");
        }
    }
}
