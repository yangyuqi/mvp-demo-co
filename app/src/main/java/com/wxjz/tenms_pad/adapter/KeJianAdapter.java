package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.VideoDetailBean;
import com.wxjz.module_base.util.FileUtil;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * Created by a on 2019/11/21.
 */

public class KeJianAdapter extends BaseQuickAdapter<VideoDetailBean.DetailBean.CoursewareListBean, BaseViewHolder> {
    public KeJianAdapter(int layoutResId, @Nullable List<VideoDetailBean.DetailBean.CoursewareListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDetailBean.DetailBean.CoursewareListBean item) {
        //Glide.with(mContext).load(item.get)
        ImageView ivCover = helper.getView(R.id.iv_cover);
        String houzhui = item.getFileType();
        if (houzhui.equalsIgnoreCase("pdf")) {
            ivCover.setBackground(mContext.getResources().getDrawable(R.drawable.pdf));
        } else if (houzhui.equalsIgnoreCase("word") || houzhui.equalsIgnoreCase("doc")) {
            ivCover.setBackground(mContext.getResources().getDrawable(R.drawable.word));
        } else if (houzhui.equalsIgnoreCase("ppt")) {
            ivCover.setBackground(mContext.getResources().getDrawable(R.drawable.ppt));
        }
        helper.setText(R.id.tv_title, (helper.getLayoutPosition() + 1) + ". " + item.getCoursewareName());
        helper.setText(R.id.tv_size, FileUtil.getFormatSize(item.getFileSize()));
    }
}
