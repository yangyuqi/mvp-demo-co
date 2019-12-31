package com.wxjz.tenms_pad.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.bean.FreeVideoListBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.tenms_pad.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by a on 2019/9/6.
 */

public class FreeCourseActivityAdapter extends BaseQuickAdapter<FreeVideoListBean.ListBean, BaseViewHolder> {


    public FreeCourseActivityAdapter(int layoutResId, @Nullable List<FreeVideoListBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FreeVideoListBean.ListBean item) {
        if (!TextUtils.isEmpty(item.getCoverUrl())) {
            Glide.with(mContext).load(item.getCoverUrl()).apply(RequestOptions.bitmapTransform(new RoundedCorners(10))).placeholder
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

//        int videoCount = item.getVideoCount();
//        helper.setText(R.id.tv_course_count, videoCount + "节课");
        HashMap<String, Integer> colorMap = BaseApplication.getColorMap();
        if (!colorMap.containsKey(item.getSubjectName())) {
            //去1到10随机数
            int random = (int) (1 + Math.random() * (9));
            String key = (String) colorMap.keySet().toArray()[random];
            helper.setBackgroundRes(R.id.iv_course,  colorMap.get(key));
        } else {
            int color = colorMap.get(item.getSubjectName());
            helper.setBackgroundRes(R.id.iv_course,  color);

        }
        helper.setText(R.id.tv_sub_name, item.getSubjectName());


    }
}
