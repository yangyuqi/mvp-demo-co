package com.wxjz.tenms_pad.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.bean.VideoDetailBean;
import com.wxjz.module_base.util.DateUtil;
import com.wxjz.tenms_pad.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by a on 2019/9/10.
 * 相关课程
 */

public class AboutCourseAdapter extends BaseQuickAdapter<VideoDetailBean.AboutListBean, BaseViewHolder> {
    private int currentId;

    public AboutCourseAdapter(int layoutResId, @Nullable List<VideoDetailBean.AboutListBean> data, int id) {
        super(layoutResId, data);
        this.currentId = id;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDetailBean.AboutListBean item) {
        if (!TextUtils.isEmpty(item.getCoverUrl())) {
            Glide.with(mContext).load(item.getCoverUrl()).apply(RequestOptions.bitmapTransform(new RoundedCorners(5)))
                    .error(R.drawable.example2).into((ImageView) helper.getView(R.id.iv_cover));
        }
        helper.setVisible(R.id.tv_free, item.isFree());
        helper.setText(R.id.tv_title, item.getVideoTitle());
        if (item.getId() == currentId) {
            helper.setTextColor(R.id.tv_title, Color.parseColor("#4486EB"));
        } else {
            helper.setTextColor(R.id.tv_title, Color.parseColor("#000000"));
        }
        ProgressBar progressBar = helper.getView(R.id.progress);
        TextView tvProgress = helper.getView(R.id.tv_progress);
        helper.setText(R.id.tv_time, DateUtil.formattedMSTime(item.getVideoDuration()));
        if (item.isStudy()) {
            progressBar.setVisibility(View.VISIBLE);
            tvProgress.setVisibility(View.VISIBLE);
            int myprogress = 0;
            if (item.getMyProgress() == null) {
                myprogress = 0;
            } else {
                myprogress = new Double((Double) item.getMyProgress()).intValue();
            }
            progressBar.setProgress((int) (myprogress * 100));
            String format = new DecimalFormat("0").format(myprogress * 100);
            tvProgress.setText(format + "%");
        } else {
            progressBar.setVisibility(View.GONE);
            tvProgress.setVisibility(View.GONE);
        }

    }
}
