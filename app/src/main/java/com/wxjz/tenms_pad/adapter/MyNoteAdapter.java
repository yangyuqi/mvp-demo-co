package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxjz.module_base.bean.ClassRankBean;
import com.wxjz.module_base.bean.NoteBean;
import com.wxjz.module_base.event.NoteItemChekEvent;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.fragment.mine.FragmentInMyNote;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 我的笔记
 */

public class MyNoteAdapter extends BaseQuickAdapter<NoteBean, BaseViewHolder> {
    private boolean boxVisible, checkAll;
    /**
     * 保存checkbox的状态集合
     */
    private List<Boolean> mCheckStatusList;
    private List<NoteBean> noteList;
    private FragmentInMyNote fragmentInMyNote;

    public MyNoteAdapter(int layoutResId, @Nullable List<NoteBean> data, FragmentInMyNote fragmentInMyNote) {
        super(layoutResId, data);
        this.noteList = data;
        this.fragmentInMyNote = fragmentInMyNote;
        mCheckStatusList = new ArrayList<>(data.size());
        for (int i = 0; i < data.size(); i++) {
            mCheckStatusList.add(i, false);
        }

    }


    @Override
    protected void convert(final BaseViewHolder helper, NoteBean item) {
        helper.setText(R.id.tv_note, item.getDomContent());
        helper.setText(R.id.tv_title, item.getVideoTitle());
        helper.addOnClickListener(R.id.ll_play);
        CheckBox checkBox = helper.getView(R.id.cb_note);
        checkBox.setOnCheckedChangeListener(null);
        final int layoutPosition = helper.getLayoutPosition();
        try {
            checkBox.setChecked(mCheckStatusList.get(layoutPosition));

        }catch (Exception e){

        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    checkAll = false;
                }
                mCheckStatusList.set(layoutPosition, isChecked);
                EventBus.getDefault().post(new NoteItemChekEvent(true));
            }
        });
        checkBox.setVisibility(boxVisible ? View.VISIBLE : View.GONE);
//        if (checkAll) {
//            checkBox.setChecked(true);
//            for (int i = 0; i < mCheckStatusList.size(); i++) {
//                mCheckStatusList.set(i, true);
//            }
//        }

    }

    public void setCheckBoxVisible(boolean boxVisible) {
        this.boxVisible = boxVisible;
        setBoxUnChecked();
        notifyDataSetChanged();
    }

    public void checkAll(boolean checkAll) {
       // this.checkAll = checkAll;
        for (int i = 0; i < mCheckStatusList.size(); i++) {
           mCheckStatusList.set(i,checkAll);
        }
        notifyDataSetChanged();
        EventBus.getDefault().post(new NoteItemChekEvent(true));
    }

    public void deleteCheckItem() {
        String ids = "";
        for (int i = mCheckStatusList.size() - 1; i >= 0; i--) {
            if (mCheckStatusList.get(i)) {
                ids += noteList.get(i).getId() + "-";
                noteList.remove(i);
                mCheckStatusList.remove(i);
            }
        }
        notifyDataSetChanged();
        fragmentInMyNote.deleteCheckedNotes(ids);
        EventBus.getDefault().post(new NoteItemChekEvent(true));
    }

    public boolean getCheckStatus() {
        for (int i = 0; i < mCheckStatusList.size(); i++) {
            if (mCheckStatusList.get(i)) {
                return true;
            }

        }
        return false;
    }

    /**
     * 将checkbox恢复未选中状态
     */
    private void setBoxUnChecked() {
        for (int i = 0; i < mCheckStatusList.size(); i++) {
            mCheckStatusList.set(i, false);
        }
    }
}
