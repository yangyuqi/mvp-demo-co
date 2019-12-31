package com.wxjz.tenms_pad.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.CourseListItemBean;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * Created by a on 2019/9/12.
 */

public class PickCourseLeftAdapter extends BaseQuickAdapter<CourseListItemBean,BaseViewHolder> {
    private  int mSelectPosition = -1;
    public PickCourseLeftAdapter(int layoutResId, @Nullable List<CourseListItemBean> data) {
        super(layoutResId, data);
    }



    @SuppressLint("ResourceType")
    @Override
    protected void convert(BaseViewHolder helper, CourseListItemBean item) {
        if (!TextUtils.isEmpty(item.getKnowledge())){
            helper.setText(R.id.check_left,item.getKnowledge());
        }
        int position = helper.getLayoutPosition();
        if (position==mSelectPosition){
            helper.setBackgroundColor(R.id.check_left,mContext.getResources().getColor(R.color.user_center_bac));
        }else{
            helper.setBackgroundColor(R.id.check_left,mContext.getResources().getColor(R.color.white));
        }
    }
    public void setSelectPosition(int selectPosition){
        mSelectPosition = selectPosition;
        notifyDataSetChanged();
    }
}
