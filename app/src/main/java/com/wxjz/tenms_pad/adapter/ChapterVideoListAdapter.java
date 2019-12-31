package com.wxjz.tenms_pad.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.db.bean.SubjectChapterBean;
import com.wxjz.module_base.db.bean.SubjectSectionBean;
import com.wxjz.tenms_pad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ChapterVideoListAdapter
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-04 08:50
 * @Version 1.0
 */
public class ChapterVideoListAdapter extends RecyclerView.Adapter<ChapterVideoListAdapter.ViewHolder> {
    private Context mContext;
    private List<SubjectSectionBean> subjectSectionBeans = new ArrayList<>();
    private List<SubjectChapterBean> subjectChapterBeans = new ArrayList<>();
    private int gradelayoutitem = 3;
    private OnChildItemClickListener childItemClickListener;
    private SectionVideoListAdapter sectionVideoListAdapter;

    public ChapterVideoListAdapter(Context mContext, List<SubjectChapterBean> chapterBeans, int gradelayoutitem) {
        this.mContext = mContext;
        this.subjectChapterBeans = chapterBeans;
        this.gradelayoutitem = gradelayoutitem;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chapter_item_video, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String sessionTitle = (String) subjectChapterBeans.get(i).getSectionName();
        if (!TextUtils.isEmpty(sessionTitle)) {
            viewHolder.tvSessionTitle.setText(sessionTitle);
        }
        subjectSectionBeans = subjectChapterBeans.get(i).getVideoModelList();
        if (subjectSectionBeans != null && subjectSectionBeans.size() > 0) {
            viewHolder.recyclerView.setVisibility(View.VISIBLE);
            sectionVideoListAdapter = new SectionVideoListAdapter(R.layout.layout_subject_item_video, subjectSectionBeans);
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, gradelayoutitem);
            viewHolder.recyclerView.setNestedScrollingEnabled(false);//禁止滑动
            viewHolder.recyclerView.setLayoutManager(layoutManager);
            viewHolder.recyclerView.setAdapter(sectionVideoListAdapter);
            sectionVideoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (childItemClickListener != null) {
                        childItemClickListener.OnChildItemOnClickListener(i, position);
                    }
                }
            });
        } else {
            viewHolder.recyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return subjectChapterBeans.size();
    }

    public interface OnChildItemClickListener {
        void OnChildItemOnClickListener(int layoutPosition, int position);
    }

    public void setChildItemClickListener(OnChildItemClickListener childItemClickListener) {
        this.childItemClickListener = childItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSessionTitle;

        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSessionTitle = itemView.findViewById(R.id.tv_section_name);
            recyclerView = itemView.findViewById(R.id.ry_section_video_list);
        }
    }

    /**
     * 动态去设置这个一行排列的数量
     *
     * @param gradelayoutitem
     */
    public void setGradelayoutitem(int gradelayoutitem) {
        this.gradelayoutitem = gradelayoutitem;
        notifyDataSetChanged();
    }

}
