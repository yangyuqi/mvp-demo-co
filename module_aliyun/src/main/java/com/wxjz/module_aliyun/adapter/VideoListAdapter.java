package com.wxjz.module_aliyun.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_aliyun.R;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.bean.VideoInPlayPageBean;

import java.util.List;

/**
 * Created by a on 2019/10/12.
 */

public class VideoListAdapter extends BaseQuickAdapter<VideoInPlayPageBean.VideoListBean, BaseViewHolder> {
    private String currentVid;

    public VideoListAdapter(int layoutResId, @Nullable List<VideoInPlayPageBean.VideoListBean> data, String currentVid) {
        super(layoutResId, data);
        this.currentVid = currentVid;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInPlayPageBean.VideoListBean item) {
        if (!TextUtils.isEmpty(currentVid)) {
            if (item.getVideoId().equals(currentVid)) {
                helper.setTextColor(R.id.tv_video_title, mContext.getResources().getColor(R.color.yellowFDCF00));
            } else {
                helper.setTextColor(R.id.tv_video_title, mContext.getResources().getColor(R.color.white));
            }
        }
        helper.setText(R.id.tv_video_title, item.getVideoTitle());
        helper.setText(R.id.tv_play_count, "播放量" + item.getClickCount());
        ImageView ivCover = helper.getView(R.id.iv_video_cover);
        Glide.with(mContext).load(item.getCoverUrl()).into(ivCover);
        ImageView ivStatus = helper.getView(R.id.iv_status);
        int download_status = item.getDownload_status();
        if (download_status == 0) {
            ivStatus.setVisibility(View.GONE);
        } else if (download_status == 1) {
            ivStatus.setVisibility(View.VISIBLE);
            ivStatus.setBackgroundResource(R.drawable.download_loading);
        } else if (download_status == 2) {
            ivStatus.setVisibility(View.VISIBLE);
            ivStatus.setBackgroundResource(R.drawable.download_success);
        }

    }
}
