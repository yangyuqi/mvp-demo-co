package com.wxjz.module_aliyun.dialogFragment;

import android.view.View;
import android.widget.TextView;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.listener.OnLimitDoubleListener;

/**
 * @ClassName ConfirmdeleteDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-25 15:24
 * @Version 1.0
 */
public class ConfirmdeleteDialog extends BaseDialog {
    /**
     * 确定删除
     */
    private TextView tvConfirmdelete;
    /**
     * 取消删除
     */
    private TextView tvCancel;

    private static ConfirmdeleteDialog mConfirmdeleteDialog;

    private OnItemViewClickListener listener;

    public static ConfirmdeleteDialog newInstance() {
        if (mConfirmdeleteDialog == null) {
            mConfirmdeleteDialog = new ConfirmdeleteDialog();
        }
        return mConfirmdeleteDialog;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_confirmedelete;
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialog dialog) {
        initView(holder);
        initData(holder);
        initListener(holder, dialog);
    }

    private void initView(ViewHolder holder) {
        tvConfirmdelete = (TextView) holder.findView(R.id.tv_btn_yes);
        tvCancel = (TextView) holder.findView(R.id.tv_btn_no);
    }

    private void initData(ViewHolder holder) {

    }

    private void initListener(ViewHolder holder, final BaseDialog dialog) {
        tvConfirmdelete.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (listener != null) {
                    listener.OnItemClick(tvConfirmdelete);
                }
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (listener != null) {
                    listener.OnItemClick(tvCancel);
                }
                dismiss();
            }
        });
    }

    public interface OnItemViewClickListener {
        void OnItemClick(View view);
    }

    public void setListener(OnItemViewClickListener listener) {
        this.listener = listener;
    }
}
