package com.wxjz.module_aliyun.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_base.bean.ExerciseListBean;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.db.bean.ChoiceQuestion;
import com.wxjz.module_base.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/8
 * Time: 9:05
 */
public class CheckBoxOptionAdapter extends BaseQuickAdapter<PointListBean.ChooseItem, BaseViewHolder> {

    private CheckBox checkBox;
    private ImageView imageView;
    private OnOptionCheckListener onOptionCheckListener;
    private ImageView ivVoiceSpeek;

    public CheckBoxOptionAdapter(int layoutResId, @Nullable List<PointListBean.ChooseItem> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final PointListBean.ChooseItem item) {
        checkBox = helper.itemView.findViewById(R.id.id_option_check);
        imageView = helper.itemView.findViewById(R.id.iv_option_image);
        ivVoiceSpeek = helper.itemView.findViewById(R.id.iv_option_voice);
        if (!TextUtils.isEmpty(item.getOptionContent())) {
            helper.setText(R.id.tv_option_context, item.getOptionContent());
        }
        //加载图片
        if (!TextUtils.isEmpty(item.getUrl())) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).asBitmap().format(DecodeFormat.PREFER_RGB_565).load(item.getUrl()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int actualHight = resource.getHeight();
                    int actualWidth = resource.getWidth();

                    float viewHeight = 100;
                    float viewWidth = 200;
                    if (actualHight > 0 && actualWidth > 0) {
                        // 要保证图片的长宽比不变
                        float ratio = (actualHight * 1.0f) / actualWidth;
                        viewHeight = (actualHight > viewHeight ? viewHeight : actualHight);
                        viewWidth = viewHeight / ratio;

                        float scaleWidth = (viewWidth * 1.0f) / actualWidth;
                        float scaleHeight = (viewHeight * 1.0f) / actualHight;

                        Log.i("xixi", "scaleWidth : " + scaleWidth + ", scaleHeight : " + scaleHeight + ", ratio : " + ratio);

                        Matrix matrix = new Matrix();
                        matrix.postScale(scaleWidth, scaleHeight);

                        Log.i("xixi", "actualHight : " + actualHight + ", actualWidth : " + actualWidth);

                        resource = Bitmap.createBitmap(resource, 0, 0, actualWidth, actualHight, matrix, true);
                        Log.i("xixi", "width : " + viewWidth + ", height : " + viewHeight);
                        helper.setImageBitmap(R.id.iv_option_image, resource);
                    }
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onOptionCheckListener != null) {
                        onOptionCheckListener.onImageClick(item.getUrl());
                    }
                }
            });
        } else {
            imageView.setVisibility(View.GONE);
        }

        final int position = helper.getLayoutPosition();

        if (item.isSelect()) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (onOptionCheckListener != null) {
                    onOptionCheckListener.onCheckChange(b, position);
                }
            }
        });

        ivVoiceSpeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(item.getOptionContent())) {
                    if (onOptionCheckListener != null) {
                        onOptionCheckListener.onSpeekContent(item.getOptionContent());
                    }
                } else {
                    ToastUtil.show(mContext, "当前选项没有朗读内容");
                }
            }
        });

    }

    public interface OnOptionCheckListener {
        void onCheckChange(boolean isChecked, int i);

        void onImageClick(String url);

        void onSpeekContent(String text);
    }

    public void setOnOptionCheckListener(OnOptionCheckListener onOptionCheckListener) {
        this.onOptionCheckListener = onOptionCheckListener;
    }

}
