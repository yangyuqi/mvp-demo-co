package com.wxjz.tenms_pad.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.db.bean.SubjectHomeBean;
import com.wxjz.tenms_pad.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName SubjectHomeAdapter
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-01 16:38
 * @Version 1.0
 */
public class SubjectHomeAdapter extends RecyclerView.Adapter<SubjectHomeAdapter.ViewHolder> {

    private List<SubjectHomeBean.SectionBean> sectionBeans = new ArrayList<>();

    private List<SubjectHomeBean.NavBean> navBeans = new ArrayList<>();

    private OnSecondListSelcetListener listSelcetListener;

    private OnItemClickListener onItemClickListener;

    private FestivalAdapter festivalAdapter;

    private Context mContext;


    public SubjectHomeAdapter(Context context, List<SubjectHomeBean.NavBean> navBeans) {
        this.mContext = context;
        this.navBeans = navBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_subject_chapter_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectHomeAdapter.ViewHolder viewHolder, final int i) {
        if (!TextUtils.isEmpty((CharSequence) navBeans.get(i).getChapterName())) {
            viewHolder.tvChapterTitle.setText((CharSequence) navBeans.get(i).getChapterName());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(i);
                }
            }
        });
        sectionBeans = navBeans.get(i).getSectionList();
        if (navBeans.get(i).getSectionList() != null && navBeans.get(i).getSectionList().size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            festivalAdapter = new FestivalAdapter(R.layout.layout_festival_item, sectionBeans);
            viewHolder.recyclerView.setLayoutManager(layoutManager);
            viewHolder.recyclerView.setNestedScrollingEnabled(false);//禁止滑动
            viewHolder.recyclerView.setAdapter(festivalAdapter);
            festivalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (listSelcetListener != null) {
                        listSelcetListener.onSecondIndex(i, position);
                    }
                }
            });
        }
        if (navBeans.get(i).isExpand()) {
            //展开状态
            viewHolder.ivSelectStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.black_show));
            if (sectionBeans != null && sectionBeans.size() > 0) {
                //说明是有数据的
                festivalAdapter.setPosition(navBeans.get(i).getChildSelect());
                viewHolder.recyclerView.setVisibility(View.VISIBLE);
            }

            if (navBeans.get(i).getChildSelect() != -1) {
                //如果二级子控件有选中的话并且是展开的话
                viewHolder.viewSelect.setBackgroundColor(mContext.getResources().getColor(R.color.whiteF5F6F8));
            } else if (navBeans.get(i).getGroupSelect() != -1) {
                //如果父控件被选择了
                viewHolder.viewSelect.setBackgroundColor(mContext.getResources().getColor(R.color.yellowFBD80B));
            } else {
                viewHolder.viewSelect.setBackgroundColor(mContext.getResources().getColor(R.color.whiteF5F6F8));
            }
        } else {
            //收起状态
            viewHolder.ivSelectStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.more));
            viewHolder.recyclerView.setVisibility(View.GONE);
            if (navBeans.get(i).getChildSelect() != -1) {
                //如果二级子控件有选中的话
                viewHolder.viewSelect.setBackgroundColor(mContext.getResources().getColor(R.color.whiteF5F6F8));
            } else if (navBeans.get(i).getGroupSelect() != -1) {
                //如果父级控件选中了
                viewHolder.viewSelect.setBackgroundColor(mContext.getResources().getColor(R.color.yellowFBD80B));
            } else {
                viewHolder.viewSelect.setBackgroundColor(mContext.getResources().getColor(R.color.whiteF5F6F8));
            }
        }
    }

    @Override
    public int getItemCount() {
        return navBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //recyclerView加载状态
        private RecyclerView recyclerView;
        //章Id
        private TextView tvChapterTitle;
        //是否是展开的状态
        private ImageView ivSelectStatus;
        //单选的标示
        private View viewSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.ry_festival);
            tvChapterTitle = itemView.findViewById(R.id.tv_chapter_title);
            ivSelectStatus = itemView.findViewById(R.id.iv_select_status);
            viewSelect = itemView.findViewById(R.id.view_select);
        }
    }

    //点击二级列表的时候所对应的position 包括一级列表的position
    public interface OnSecondListSelcetListener {
        void onSecondIndex(int firstIndex, int secondIndex);
    }

    public void setListSelcetListener(OnSecondListSelcetListener listSelcetListener) {
        this.listSelcetListener = listSelcetListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setNavBeans(List<SubjectHomeBean.NavBean> navBeans) {
        this.navBeans = navBeans;
        notifyDataSetChanged();
    }
}
