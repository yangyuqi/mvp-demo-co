package com.wxjz.module_aliyun.dialogFragment;

import android.view.View;

import com.wxjz.module_aliyun.R;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/25
 * Time: 17:17
 */
public class AnswerErrorDialog extends BaseDialog {
    private static AnswerErrorDialog answerErrorDialog;

    public static AnswerErrorDialog newInstance() {
        if (answerErrorDialog == null) {
            answerErrorDialog = new AnswerErrorDialog();
        }
        return answerErrorDialog;
    }

    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_normal_error;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseDialog dialog) {
        holder.setTextBold(R.id.tv_answer_error);
        holder.setOnClickListener(R.id.tv_dialog_answer_error, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }

    public void dimmiss() {
        if (answerErrorDialog != null) {
            answerErrorDialog.dimmiss();
        }
    }
}
