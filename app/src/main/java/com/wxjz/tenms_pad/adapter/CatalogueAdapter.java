package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.apppickimagev3.utils.TimeUtils;
import com.wxjz.module_base.bean.CourseOutlineBean;
import com.wxjz.module_base.util.DateUtil;
import com.wxjz.module_base.util.LogUtils;
import com.wxjz.tenms_pad.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by a on 2019/9/20.
 */

public class CatalogueAdapter extends BaseQuickAdapter<CourseOutlineBean.ListBean, BaseViewHolder> {
    public CatalogueAdapter(int layoutResId, @Nullable List<CourseOutlineBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseOutlineBean.ListBean item) {
        Glide.with(mContext).load(item.getCoverUrl()).apply(RequestOptions.bitmapTransform(new RoundedCorners(10))).error(R.drawable.example2)
                .into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_video_name, item.getVideoTitle());
        String minute = String.format("%02d", item.getVideoDuration() / 60);
        String second = String.format("%02d", item.getVideoDuration() % 60);

        helper.setText(R.id.tv_video_time, minute + "分" + second + "秒");
        TextView tvHasNotLearn = helper.getView(R.id.tv_has_not_learn);
        RelativeLayout rl_has_learn = helper.getView(R.id.rl_has_learn);
        boolean study = item.isIsStudy();
        tvHasNotLearn.setVisibility(study ? View.GONE : View.VISIBLE);
        rl_has_learn.setVisibility(study ? View.VISIBLE : View.GONE);
        if (study) {
            DecimalFormat df = new DecimalFormat("0");
            String e = df.format((double) item.getProgress() / item.getVideoDuration() * 100); // 此处可以用上面b/c/d任意一种
            helper.setText(R.id.tv_progress, e + "%");
            helper.setProgress(R.id.progress_learn, (int) Math.round(item.getProgress()), item.getVideoDuration());
        }
    }
}
