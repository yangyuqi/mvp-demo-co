package com.wxjz.tenms_pad.popwindow.MyErrorPopWindow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wxjz.module_base.bean.FilterErrorExerciseBean;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * @ClassName MyErrorChapterAdapter
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-27 10:07
 * @Version 1.0
 */
public class MyErrorChapterAdapter extends RecyclerView.Adapter<MyErrorChapterAdapter.ViewHolder> {

    private Context context;
    private List<FilterErrorExerciseBean.chapterBean> chapterlist;
    private ItemViewOnClickListener onClickListener;

    public MyErrorChapterAdapter(Context mContext, List<FilterErrorExerciseBean.chapterBean> filterErrorChapterBeans, ItemViewOnClickListener listener) {
        context = mContext;
        chapterlist = filterErrorChapterBeans;
        onClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_ry_item, viewGroup, false);
        return new MyErrorChapterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        FilterErrorExerciseBean.chapterBean chapterBean = chapterlist.get(i);
        String chapterName = chapterBean.getChapterName();
        if (!TextUtils.isEmpty(chapterName)) {
            viewHolder.textView.setText(chapterName);
        }
        if (null != chapterBean) {
            if (chapterBean.isSelect()) {
                //是否是选中状态
                viewHolder.llBackGroud.setBackground(context.getResources().getDrawable(R.color.grayD9D9DB));
            } else {
                viewHolder.llBackGroud.setBackground(context.getResources().getDrawable(R.color.whiteF5F6FA));
            }
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //填写的内容
        private TextView textView;
        private LinearLayout llBackGroud;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
            llBackGroud = itemView.findViewById(R.id.ll_pop_ry_item);
        }
    }


    public interface ItemViewOnClickListener {
        void onItemClick(int position);
    }
}
