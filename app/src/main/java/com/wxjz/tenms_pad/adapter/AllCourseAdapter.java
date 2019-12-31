package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.CourseListItemBean;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.CourseDetailActivity;
import com.wxjz.tenms_pad.fragment.CourseCommonFragment;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 单科下的所有学科适配器
 */

public class AllCourseAdapter extends BaseQuickAdapter<CourseListItemBean, BaseViewHolder> {
    private CourseCommonFragment courseCommonFragment;

    public AllCourseAdapter(int layoutResId, @Nullable List<CourseListItemBean> data, CourseCommonFragment courseCommonFragment) {
        super(layoutResId, data);
        this.courseCommonFragment = courseCommonFragment;

    }

    @Override
    protected void convert(BaseViewHolder helper, CourseListItemBean item) {

        if (item.getCourseList().size() > 0) {
            RecyclerView recyclerView = helper.getView(R.id.rv_course);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            final List<CourseListItemBean.CourseListBean> courseList = item.getCourseList();
            final ShaiXuanCourseAdapter shaiXuanCourseAdapter = new ShaiXuanCourseAdapter();
            shaiXuanCourseAdapter.addData(courseList);
            recyclerView.setAdapter(shaiXuanCourseAdapter);
            shaiXuanCourseAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    CourseListItemBean.CourseListBean bean = courseList.get(position);
                    courseCommonFragment.addCourseClickCount(bean.getId());
                    bean.setClickCount(bean.getClickCount() + 1);
                    shaiXuanCourseAdapter.notifyItemChanged(position);

                  //  JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, courseList.get(position).getId(),courseList.get(position).getSubId(),courseList.get(position).getIsFree());
                }
            });
            if (TextUtils.isEmpty(item.getKnowledge())) {
                helper.setText(R.id.top_course_name, "无数据");
            } else {
                helper.setText(R.id.top_course_name, item.getKnowledge());
            }
        }else{
            helper.getConvertView().setVisibility(View.GONE);
        }


    }
}
