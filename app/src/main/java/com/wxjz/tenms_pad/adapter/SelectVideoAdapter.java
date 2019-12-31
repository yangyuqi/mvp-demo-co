package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.SelectVideoBean;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * Created by a on 2019/9/12.
 */

public class SelectVideoAdapter extends BaseQuickAdapter<SelectVideoBean.ListBean, BaseViewHolder> {
    public SelectVideoAdapter(int layoutResId, @Nullable List<SelectVideoBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectVideoBean.ListBean item) {
        ImageView ivCover = helper.getView(R.id.iv_cover);
        if (!TextUtils.isEmpty(item.getCoverUrl())) {
            Glide.with(mContext).load(item.getCoverUrl()).apply(RequestOptions.bitmapTransform(new RoundedCorners(10))).error(R.drawable.example2).into(ivCover);
        }
        helper.setText(R.id.tv_play_count, "播放量"+String.valueOf(item.getClickCount()));
        helper.setText(R.id.video_title, String.valueOf(item.getVideoTitle()));
    }
}
