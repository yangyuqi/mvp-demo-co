package com.wxjz.tenms_pad.popwindow.MyErrorPopWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.wxjz.module_base.bean.FilterErrorExerciseBean;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * @ClassName MyErrorPopWindow
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-27 08:45
 * @Version 1.0
 */
public class MyErrorPopWindow extends PopupWindow {
    private Context mContext;
    private List<FilterErrorExerciseBean> filterErrorExerciseBeans;
    private List<FilterErrorExerciseBean.chapterBean> filterErrorChapterBeans;
    private List<FilterErrorExerciseBean.sectionBean> filterErrorsectionBeans;
    private RecyclerView gradeRecyclerView;
    private RecyclerView chapterRecyclerView;
    private RecyclerView sectionRecyclerView;
    private MyErrorGradeAdapter gradeAdapter;
    private MyErrorChapterAdapter chapterAdapter;
    private MyErrorSectionAdapter sectionAdapter;
    private View view;

    public MyErrorPopWindow(Context context, List<FilterErrorExerciseBean> filterErrorExerciseBeans, int width, int height) {
        super(context);
        mContext = context;
        this.filterErrorExerciseBeans = filterErrorExerciseBeans;
        setWidth(width);
        setHeight(height);
        //消除popwindow的外边框线
//        setBackgroundDrawable(new ColorDrawable());
        initView();
        initData(filterErrorExerciseBeans);
    }


    private void initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_my_error, null, false);
        setContentView(view);
        gradeRecyclerView = view.findViewById(R.id.pop_ry_grade);
        chapterRecyclerView = view.findViewById(R.id.pop_ry_chapter);
        sectionRecyclerView = view.findViewById(R.id.pop_ry_section);
    }

    private void initData(List<FilterErrorExerciseBean> filterErrorExerciseBeans) {
        if (filterErrorExerciseBeans != null && filterErrorExerciseBeans.size() > 0) {
            //设置一级recyclerview
            setgradeRecyclerView(filterErrorExerciseBeans);
        }

    }

    /**
     * 设置一级recyclerview
     *
     * @param filterErrorExerciseBeans
     */
    private void setgradeRecyclerView(final List<FilterErrorExerciseBean> filterErrorExerciseBeans) {
        for (int i = 0; i < filterErrorExerciseBeans.size(); i++) {
            filterErrorExerciseBeans.get(i).setSelect(false);
        }

        gradeAdapter = new MyErrorGradeAdapter(mContext, filterErrorExerciseBeans, new MyErrorGradeAdapter.ItemViewOnClickListener() {
            @Override
            public void onItemClick(int position) {
                //判断自身是否是选中状态
                if (!filterErrorExerciseBeans.get(position).getSelect()) {
                    for (int i = 0; i < filterErrorExerciseBeans.size(); i++) {
                        if (i == position) {
                            filterErrorExerciseBeans.get(i).setSelect(true);
                        } else {
                            filterErrorExerciseBeans.get(i).setSelect(false);
                        }
                    }
                    gradeAdapter.notifyDataSetChanged();
                    setChapterRecyclerView(position);
                }
            }
        });
        gradeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        gradeRecyclerView.setAdapter(gradeAdapter);
        setChapterRecyclerView(0);

    }

    /**
     * 设置二级的recyclerview
     *
     * @param fatherPosition 父级选项的选择位置
     */
    private void setChapterRecyclerView(final int fatherPosition) {
        if (null != filterErrorExerciseBeans.get(fatherPosition)) {
            filterErrorChapterBeans = filterErrorExerciseBeans.get(fatherPosition).getChapterId();
            for (int i = 0; i < filterErrorChapterBeans.size(); i++) {
                filterErrorChapterBeans.get(i).setSelect(false);
            }
            chapterAdapter = new MyErrorChapterAdapter(mContext, filterErrorChapterBeans, new MyErrorChapterAdapter.ItemViewOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (!filterErrorChapterBeans.get(position).isSelect()) {
                        //判断父级是否是选中状态，//如果没有选中，就需要刷新
                        if (!filterErrorExerciseBeans.get(fatherPosition).getSelect()) {
                            filterErrorExerciseBeans.get(fatherPosition).setSelect(true);
                            gradeAdapter.notifyDataSetChanged();
                        }
                        //自身不是选中状态，需要刷新
                        for (int i = 0; i < filterErrorChapterBeans.size(); i++) {
                            if (i == position) {
                                filterErrorChapterBeans.get(i).setSelect(true);
                            } else {
                                filterErrorChapterBeans.get(i).setSelect(false);
                            }
                        }
                        chapterAdapter.notifyDataSetChanged();
                        setSectionRecyclerView(fatherPosition, position);
                    }
                }
            });

            chapterRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            chapterRecyclerView.setAdapter(chapterAdapter);
            setSectionRecyclerView(fatherPosition, 0);
        }

    }

    /**
     * 设置三级的recyclerview
     *
     * @param firstposition  一级选项选择的位置
     * @param secondposition 二级选项选择的位置
     */
    private void setSectionRecyclerView(final int firstposition, final int secondposition) {
        if (null != filterErrorExerciseBeans.get(firstposition).getChapterId().get(secondposition).getSectionId()) {
            filterErrorsectionBeans = filterErrorExerciseBeans.get(firstposition).getChapterId().get(secondposition).getSectionId();
            for (int i = 0; i < filterErrorsectionBeans.size(); i++) {
                filterErrorsectionBeans.get(i).setSelect(false);
            }
            sectionAdapter = new MyErrorSectionAdapter(mContext, filterErrorsectionBeans, new MyErrorSectionAdapter.ItemViewOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (!filterErrorsectionBeans.get(position).isSelect()) {
                        //判断一级列表是否是选择状态
                        if (!filterErrorExerciseBeans.get(firstposition).getSelect()) {
                            filterErrorExerciseBeans.get(firstposition).setSelect(true);
                            gradeAdapter.notifyDataSetChanged();
                        }
                        //判断二级列表是否是选中状态
                        if (!filterErrorChapterBeans.get(secondposition).isSelect()) {
                            filterErrorChapterBeans.get(secondposition).setSelect(true);
                            chapterAdapter.notifyDataSetChanged();
                        }

                        for (int i = 0; i < filterErrorsectionBeans.size(); i++) {
                            if (i == position) {
                                filterErrorsectionBeans.get(i).setSelect(true);
                            } else {
                                filterErrorsectionBeans.get(i).setSelect(false);
                            }
                            sectionAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

            sectionRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            sectionRecyclerView.setAdapter(sectionAdapter);
        }
    }

    public void setFilterErrorExerciseBeans(List<FilterErrorExerciseBean> filterErrorExerciseBeans) {
        this.filterErrorExerciseBeans = filterErrorExerciseBeans;
        initData(filterErrorExerciseBeans);
    }


}
