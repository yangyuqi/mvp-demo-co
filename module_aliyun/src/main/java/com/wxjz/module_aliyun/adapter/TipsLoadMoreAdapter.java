package com.wxjz.module_aliyun.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.aliyun.utils.TimeFormater;
import com.wxjz.module_aliyun.event.DialogSeekToEvent;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.TipsListBean;
import com.wxjz.module_base.db.bean.TipsBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/3
 * Time: 9:46
 */
public class TipsLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PointListBean.PointBean> dataList = new ArrayList<>();
    /**
     * 普通布局
     */
    private final int TYPE_ITEM = 1;
    /**
     * 脚布局
     */
    private final int TYPE_FOOTER = 2;

    /**
     * 当前加载状态，默认为加载完成
     */
    private int loadState = 2;
    /**
     * 加载状态，正在加载中
     */
    public final int LOADING = 1;
    /**
     * 加载状态，加载完成
     */
    public final int LOADING_COMPLETE = 2;
    /**
     * 加载状态，加载结束
     */
    public final int LOADING_END = 3;
    /**
     * 返回不同的view的Type值
     */
    private View view;

    public TipsLoadMoreAdapter(List<PointListBean.PointBean> dataList) {
        this.dataList = dataList;
    }

    public AdapterOnItemViewClickListener itemViewClickListener;

    /**
     * 清除数据
     */
    public void clearData() {
        if (dataList != null) {
            dataList.clear();
        }
    }

    /**
     * 添加数据,整个list
     *
     * @param dataLists
     */
    public void addDataList(List<PointListBean.PointBean> dataLists) {
        if (dataList != null) {
            this.dataList.addAll(dataLists);
        } else {
            this.dataList = dataLists;
        }
        notifyDataSetChanged();
    }

    /**
     * 重新设置数据
     */
    public void setDataList(List<PointListBean.PointBean> dataLists) {
        if (dataLists != null) {
            this.dataList = dataLists;
            notifyDataSetChanged();
        }

    }

    /**
     * 添加单条数据
     *
     * @param data
     */
    public void addDataSingleData(PointListBean.PointBean data) {
        if (dataList != null) {
            this.dataList.add(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 根据位置移除数据
     *
     * @param index
     */
    public void removeSingleDate(int index) {
        if (dataList != null) {
            this.dataList.remove(index);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        if (i == TYPE_ITEM) {
//            //返回正常的ItemType
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycler_prompt_item, viewGroup, false);
        return new RecyclerViewHolder(view, i);
//        } else if (i == TYPE_FOOTER) {
//            //返回上拉加载的Item
//            view = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.layout_refresh_footer, viewGroup, false);
//            return new RecyclerViewHolder(view, i);
//        }
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//        if (getItemViewType(i) == TYPE_ITEM) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
        if (dataList != null) {
            final PointListBean.PointBean bean = dataList.get(i);
            recyclerViewHolder.tvRecyclerViewContent.setText(bean.getDomContent());
            recyclerViewHolder.tvTime.setText(TimeFormater.formatMs(bean.getVideoDom() * 1000) + " >");
        }
//        } else if (getItemViewType(i) == TYPE_FOOTER) {
//            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
//            switch (loadState) {
//                case LOADING://加载中
//                    recyclerViewHolder.pbLoading.setVisibility(View.VISIBLE);
//                    recyclerViewHolder.tvLoading.setVisibility(View.VISIBLE);
//                    recyclerViewHolder.llEnd.setVisibility(View.GONE);
//                    break;
//                case LOADING_COMPLETE://加载完成
//                    recyclerViewHolder.pbLoading.setVisibility(View.INVISIBLE);
//                    recyclerViewHolder.tvLoading.setVisibility(View.INVISIBLE);
//                    recyclerViewHolder.llEnd.setVisibility(View.GONE);
//                    break;
//                case LOADING_END://加载到底
//                    recyclerViewHolder.pbLoading.setVisibility(View.GONE);
//                    recyclerViewHolder.tvLoading.setVisibility(View.GONE);
//                    recyclerViewHolder.llEnd.setVisibility(View.VISIBLE);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    @Override
    public int getItemCount() {
//        return dataList.size() + 1;
        return dataList.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
//    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRecyclerViewContent;
        private TextView tvTime;
        private ProgressBar pbLoading;
        private TextView tvLoading;
        private LinearLayout llEnd;

        public RecyclerViewHolder(@NonNull View itemView, int i) {
            super(itemView);
//            if (i == TYPE_ITEM) {
            tvRecyclerViewContent = itemView.findViewById(R.id.tv_recyclerview_content);
            tvTime = itemView.findViewById(R.id.tv_tips_time);
//            } else if (i == TYPE_FOOTER) {
//                pbLoading = itemView.findViewById(R.id.pb_loading);
//                tvLoading = itemView.findViewById(R.id.tv_loading);
//                llEnd = itemView.findViewById(R.id.ll_end);
//            }
        }
    }

    /**
     * 设置上拉加载状态
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public interface AdapterOnItemViewClickListener {
        void OnItemClick(View view, int position);
    }

    public void setItemViewClickListener(AdapterOnItemViewClickListener itemViewClickListener) {
        this.itemViewClickListener = itemViewClickListener;
    }
}
