package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.db.bean.HistoryBean;
import com.wxjz.tenms_pad.R;

import java.util.List;

/**
 * Created by a on 2019/9/10.
 */

public class SearchHistoryAdapter extends BaseQuickAdapter<HistoryBean,BaseViewHolder>{
    public SearchHistoryAdapter(int layoutResId, @Nullable List<HistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {
        helper.setText(R.id.tv_history,item.getHistory());
    }
}
