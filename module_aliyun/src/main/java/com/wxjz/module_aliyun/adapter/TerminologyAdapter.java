package com.wxjz.module_aliyun.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_aliyun.aliyun.utils.TimeFormater;
import com.wxjz.module_aliyun.event.DialogSeekToEvent;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.bean.TerminologyListBean;
import com.wxjz.module_base.bean.TipsListBean;
import com.wxjz.module_base.db.bean.AnalysisBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/3
 * Time: 17:40
 */
public class TerminologyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PointListBean.PointBean> dataList;
    private Context context;
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


    public OnItemTimeClickListener onItemTimeClickListener;

    public OnItemTimeLongClickListener onItemTimeLongClickListener;

    public TerminologyAdapter(List<PointListBean.PointBean> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

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
        //返回正常的ItemType
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recycler_knowledge_item, viewGroup, false);
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
//        if (getItemViewType(i) == TYPE_ITEM) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
        if (dataList != null) {
            final PointListBean.PointBean bean = dataList.get(i);
            recyclerViewHolder.tvRecyclerViewTitle.setText(bean.getDomTitle());
            recyclerViewHolder.tvRecyclerViewContent.setText(bean.getDomContent());
            recyclerViewHolder.tvTime.setText(TimeFormater.formatMs(bean.getVideoDom() * 1000) + " >");
            final int[] tvRecyclerViewContent = {0};
            final int[] tvRecyclerViewTitle = {0};
            //获取省略的字符数，0表示没和省略
            recyclerViewHolder.tvRecyclerViewContent.post(new Runnable() {
                @Override
                public void run() {
                    tvRecyclerViewContent[0] = recyclerViewHolder.tvRecyclerViewContent.getLayout().getEllipsisCount(recyclerViewHolder.tvRecyclerViewContent.getLineCount());
                    if (tvRecyclerViewContent[0] > 0 || tvRecyclerViewTitle[0] > 0) {
                        recyclerViewHolder.tvShow.setVisibility(View.VISIBLE);
                        recyclerViewHolder.ivShowImage.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewHolder.tvShow.setVisibility(View.GONE);
                        recyclerViewHolder.ivShowImage.setVisibility(View.GONE);
                    }
                }
            });
            recyclerViewHolder.tvRecyclerViewTitle.post(new Runnable() {
                @Override
                public void run() {
                    tvRecyclerViewTitle[0] = recyclerViewHolder.tvRecyclerViewTitle.getLayout().getEllipsisCount(recyclerViewHolder.tvRecyclerViewTitle.getLineCount());
                    if (tvRecyclerViewContent[0] > 0 || tvRecyclerViewTitle[0] > 0) {
                        recyclerViewHolder.tvShow.setVisibility(View.VISIBLE);
                        recyclerViewHolder.ivShowImage.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewHolder.tvShow.setVisibility(View.GONE);
                        recyclerViewHolder.ivShowImage.setVisibility(View.GONE);
                    }
                }
            });
            if (onItemTimeClickListener != null) {
                recyclerViewHolder.tvRecyclerViewTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = viewHolder.getLayoutPosition();
                        onItemTimeClickListener.onItemClick(recyclerViewHolder.tvRecyclerViewTitle, position);

                    }
                });

                recyclerViewHolder.tvRecyclerViewContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = viewHolder.getLayoutPosition();
                        onItemTimeClickListener.onItemClick(recyclerViewHolder.tvRecyclerViewContent, position);

                    }
                });
                recyclerViewHolder.llShowContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (recyclerViewHolder.tvRecyclerViewTitle.getEllipsize() == null) {
                            Log.d("LF123", "展开");
                            //收起
                            recyclerViewHolder.tvRecyclerViewTitle.setSingleLine(true);
                            recyclerViewHolder.tvRecyclerViewTitle.setEllipsize(TextUtils.TruncateAt.END);
                            recyclerViewHolder.tvRecyclerViewContent.setSingleLine(true);
                            recyclerViewHolder.tvRecyclerViewContent.setEllipsize(TextUtils.TruncateAt.END);
                            recyclerViewHolder.ivShowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.show));
                        } else {
                            Log.d("LF123", "收起");
                            //展开
                            recyclerViewHolder.tvRecyclerViewTitle.setSingleLine(false);
                            recyclerViewHolder.tvRecyclerViewTitle.setEllipsize(null);
                            recyclerViewHolder.tvRecyclerViewContent.setSingleLine(false);
                            recyclerViewHolder.tvRecyclerViewContent.setEllipsize(null);
                            recyclerViewHolder.ivShowImage.setImageDrawable(context.getResources().getDrawable(R.drawable.hide));
                        }
                    }
                });

            }
        }

//        } else if (getItemViewType(i) == TYPE_FOOTER) {
//            RecyclerViewHolder footViewHolder = (RecyclerViewHolder) viewHolder;
//            switch (loadState) {
//                case LOADING://加载中
//                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
//                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
//                    footViewHolder.llEnd.setVisibility(View.GONE);
//                    break;
//                case LOADING_COMPLETE://加载完成
//                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
//                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
//                    footViewHolder.llEnd.setVisibility(View.GONE);
//                    break;
//                case LOADING_END://加载到底
//                    footViewHolder.pbLoading.setVisibility(View.GONE);
//                    footViewHolder.tvLoading.setVisibility(View.GONE);
//                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
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
        private TextView tvRecyclerViewTitle, tvRecyclerViewContent;
        private TextView tvTime;

        private TextView tvShow;
        private LinearLayout llShowContent;
        private ImageView ivShowImage;

        private ProgressBar pbLoading;
        private TextView tvLoading;
        private LinearLayout llEnd;

        public RecyclerViewHolder(@NonNull View itemView, int i) {
            super(itemView);
//            if (i == TYPE_ITEM) {
            tvRecyclerViewTitle = itemView.findViewById(R.id.tv_recyclerview_title);
            tvRecyclerViewContent = itemView.findViewById(R.id.tv_recyclerview_content);
            tvTime = itemView.findViewById(R.id.tv_knowledge_time);
            tvShow = itemView.findViewById(R.id.tv_show_content);
            llShowContent = itemView.findViewById(R.id.ll_show_content);
            ivShowImage = itemView.findViewById(R.id.iv_show_image);
//            } else if (i == TYPE_FOOTER) {
//                pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
//                tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
//                llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
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

    public interface OnItemTimeClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemTimeLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemTimeClickListener(OnItemTimeClickListener onItemTimeClickListener) {
        this.onItemTimeClickListener = onItemTimeClickListener;
    }

    public void setOnItemTimeLongClickListener(OnItemTimeLongClickListener onItemTimeLongClickListener) {
        this.onItemTimeLongClickListener = onItemTimeLongClickListener;
    }
}

