package com.wxjz.module_aliyun.aliyun.view.more;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxjz.module_aliyun.R;

/**
 * Created by a on 2019/7/16.
 */

public class ShowSectionView extends LinearLayout implements View.OnClickListener {


    private Context context;

    public ShowSectionView(Context context ) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_section_view, this, true);
        findAllViews(view);
    }

    private void findAllViews(View view) {
        TextView tv = view.findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv){
            onSectionSelectListener.onSectionSelect();
        }
    }
    public interface OnSectionSelectListener{
        void  onSectionSelect();
    }
    OnSectionSelectListener onSectionSelectListener;
    public void setOnSectionSelectListener(OnSectionSelectListener onSectionSelectListener){
        this.onSectionSelectListener = onSectionSelectListener;
    }
}
