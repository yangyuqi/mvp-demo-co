package com.wxjz.module_aliyun.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;
import com.wxjz.module_base.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/1
 * Time: 9:15
 */
public class TakeNoteSendDialog extends BaseDialog {

    private EditText mNotesContexteditText;

    private TextView mTvSaveNotes;

    private TextView mTvTitle;

    private ImageView mIvBack;

    private onSaveNoteClick mSaveNoteClick;

    private static TakeNoteSendDialog takeNoteSendDialog;
    /**
     * 获取当前视频的位置
     */
    private long progesscurrent;

    private String content;
    /**
     * 修改和新键都是复用这个界面
     * 所以需要type去判断。默认 0 是新建 1是编辑
     */
    private int type;
    /**
     * 修改笔记需要去接收当前是哪个position
     */
    private int position;

    public static TakeNoteSendDialog newInstance() {
        if (takeNoteSendDialog == null) {
            takeNoteSendDialog = new TakeNoteSendDialog();
        }
        return takeNoteSendDialog;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_take_notes_second;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        initView(holder);
        initData(holder);
        initListener(holder, dialog);
    }

    private void initView(ViewHolder holder) {
        mIvBack = (ImageView) holder.findView(R.id.iv_back);
        mTvSaveNotes = (TextView) holder.findView(R.id.tv_save_note);
        mNotesContexteditText = (EditText) holder.findView(R.id.ed_content);
        mTvTitle = (TextView) holder.findView(R.id.tv_title);
    }

    private void initData(ViewHolder holder) {
        mNotesContexteditText.setSaveEnabled(false);
        if (type == 0) {
            mTvTitle.setText("添加笔记");
        } else {
            mTvTitle.setText("修改笔记");
        }
        mNotesContexteditText.setText(content);
        mNotesContexteditText.setSelection(content.length());
    }

    private void initListener(ViewHolder holder, final BaseDialog dialog) {
        //点击了保存按钮
        mTvSaveNotes.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                String context = mNotesContexteditText.getText().toString();
                if (TextUtils.isEmpty(context)) {
                    ToastUtil.show(getContext(), "当前内容为空，请输入内容");
                } else {
                    if (mSaveNoteClick != null) {
                        mSaveNoteClick.onSaveNotes(mNotesContexteditText, type, position, context,getDialog());
                    }
                }
            }
        });
        //点击了返回按钮
        mIvBack.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                dialog.dismiss();
            }
        });

    }

    /**
     * 弹出键盘
     *
     * @param context
     */
    public void showSoftInput(Context context) {
        if (mNotesContexteditText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mNotesContexteditText, 0);
        }
    }

    /**
     * 隐藏键盘
     *
     * @param context
     */
    public void hideSoftInput(Context context) {
        if (mNotesContexteditText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mNotesContexteditText.getWindowToken(), 0);
        }
    }

    /**
     * 是否聚焦
     *
     * @param focus
     */
    public void setEdittextFocus(boolean focus) {
        mNotesContexteditText.setFocusable(focus);
        mNotesContexteditText.setFocusableInTouchMode(focus);
    }

    @Override
    public void dismiss() {
        hideSoftInput(getContext());
        setEdittextFocus(false);
        super.dismiss();
    }

    /**
     * 点击了保存按钮的回调
     * 传入当前的dialog，内容
     */
    public interface onSaveNoteClick {
        void onSaveNotes(EditText view, int type, int position, String context, Dialog dialog);
    }

    public void setOnSaveNoteClick(onSaveNoteClick mSaveNoteClick) {
        this.mSaveNoteClick = mSaveNoteClick;
    }

    /**
     * 获取保存的时间
     *
     * @return
     */
    private String getSaveTime() {
        long I = System.currentTimeMillis();
        Date date = new Date(I);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }


    public long getProgesscurrent() {
        return progesscurrent;
    }

    public void setProgesscurrent(long progesscurrent) {
        this.progesscurrent = progesscurrent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
