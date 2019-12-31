package com.wxjz.tenms_pad.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.ErrorProblemBean;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.PictureActivity;

import java.util.List;

/**
 * Created by a on 2019/9/18.
 */

public class ErrorProblemOptionsAdapter extends BaseQuickAdapter<ErrorProblemBean.TOwnDomOptionsBean, BaseViewHolder> {
    private List<ErrorProblemBean.TOwnDomOptionPicturesBean> tOwnDomOptionPictures;

    public ErrorProblemOptionsAdapter(int layoutResId, @Nullable List<ErrorProblemBean.TOwnDomOptionsBean> data, List<ErrorProblemBean.TOwnDomOptionPicturesBean> tOwnDomOptionPictures) {
        super(layoutResId, data);
        this.tOwnDomOptionPictures = tOwnDomOptionPictures;
    }

    @Override
    protected void convert(BaseViewHolder helper, ErrorProblemBean.TOwnDomOptionsBean item) {
        helper.setText(R.id.tv_option_left, item.getOption() + ".");
        helper.setText(R.id.tv_option, item.getOptionContent());
        ImageView ivOptionImg = helper.getView(R.id.iv_item);
        String option = item.getOption();
         String url = null;

        for (ErrorProblemBean.TOwnDomOptionPicturesBean picturesBean : tOwnDomOptionPictures) {
            if (!picturesBean.getLocation().equalsIgnoreCase("project")) {
                if (picturesBean.getOptionTag().equalsIgnoreCase(option)) {
                    ivOptionImg.setVisibility(View.VISIBLE);
                    url = picturesBean.getUrl();
                    Glide.with(mContext).load(picturesBean.getUrl()).error(R.drawable.example2).into(ivOptionImg);
                }
            }
        }
        final String finalUrl = url;
        ivOptionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(finalUrl)){
                    Intent intent = new Intent(mContext, PictureActivity.class);
                    intent.putExtra("imgUrl", finalUrl);
                    mContext.startActivity(intent);
                }

            }
        });

    }
}
