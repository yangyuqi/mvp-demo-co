package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.CourseListItemBean;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * Created by a on 2019/9/12.
 */

public class PickCourseRightAdapter extends BaseQuickAdapter<CourseListItemBean.CourseListBean, BaseViewHolder> {
    private  int mSelectPosition = -1;
    public PickCourseRightAdapter(int layoutResId, @Nullable List<CourseListItemBean.CourseListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseListItemBean.CourseListBean item) {
        if (!TextUtils.isEmpty(item.getCourseName())) {
            helper.setText(R.id.check_right, item.getCourseName());
            int position = helper.getAdapterPosition();
            if (mSelectPosition == position){
                helper.setVisible(R.id.ivCheck,true);
                helper.setBackgroundColor(R.id.llRoot,mContext.getResources().getColor(R.color.user_center_bac));
            }else{
                helper.setVisible(R.id.ivCheck,false);
                helper.setBackgroundColor(R.id.llRoot,mContext.getResources().getColor(R.color.white));
            }
        }
    }
    public void setSelectPosition(int selectPosition){
        mSelectPosition = selectPosition;
        notifyDataSetChanged();
    }
}
