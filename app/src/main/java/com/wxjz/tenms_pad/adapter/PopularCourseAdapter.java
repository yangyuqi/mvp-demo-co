package com.wxjz.tenms_pad.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.tenms_pad.R;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by a on 2019/9/6.
 */

public class PopularCourseAdapter extends BaseQuickAdapter<PopularMutiItem.ListBean, BaseViewHolder> {
    public PopularCourseAdapter() {

        super(null);
        //Step.1
        setMultiTypeDelegate(new MultiTypeDelegate<PopularMutiItem.ListBean>() {
            @Override
            protected int getItemType(PopularMutiItem.ListBean entity) {
                return PopularMutiItem.ListBean.NOT_LEARN;
                //根据你的实体类来判断布局类型
//                if (entity.isStudy()) {
//                    return PopularMutiItem.ListBean.HAS_LEARN;
//                } else {
//                    return PopularMutiItem.ListBean.NOT_LEARN;
//                }
            }
        });
        //Step.2
        getMultiTypeDelegate()
                // .registerItemType(PopularMutiItem.ListBean.HAS_LEARN, R.layout.layout_popular_course_haslearn_item)
                .registerItemType(PopularMutiItem.ListBean.NOT_LEARN, R.layout.layout_popular_course_unlearn_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PopularMutiItem.ListBean item) {
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
//        switch (item.getSubjectName()) {
//
//            case "生物":
//                helper.setBackgroundColor(R.id.iv_course, Color.parseColor(colorMap.get()));
//
//                break;
//            case 2:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.shuxue);
//
//                break;
//            case 3:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.yingyu);
//
//                break;
//            case 4:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.yuwen);
//
//                break;
//            case 5:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.shuxue);
//
//                break;
//            case 6:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.yingyu);
//
//                break;
//            case 7:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.wuli);
//
//
//                break;
//            case 8:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.huaxue);
//
//                break;
//            case 9:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.shengwu);
//
//                break;
//            case 10:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.zhengzhi);
//
//                break;
//            case 11:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.lishi);
//
//                break;
//            case 12:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.dili);
//
//                break;
//            case 13:
//                helper.setBackgroundRes(R.id.iv_course, R.drawable.sipin);
//
//                break;
        //  }
//        switch (helper.getItemViewType()) {
//            case PopularMutiItem.ListBean.HAS_LEARN:
//                helper.setProgress(R.id.progress_learn, (int) (item.getProgress() * 100));
//                helper.setText(R.id.tv_progress, String.format("%.0f", new BigDecimal(item.getProgress() * 100)) + "%");
//                break;
//
//            case PopularMutiItem.ListBean.NOT_LEARN:
//                break;
//        }

    }
}
