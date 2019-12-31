package com.wxjz.module_aliyun.dialogFragment;

import android.view.View;

import com.wxjz.module_aliyun.R;


/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/25
 * Time: 13:28
 */
public class AnswerCorrectDialog extends BaseDialog {

    private static AnswerCorrectDialog answerCorrectDailog;

    public static AnswerCorrectDialog newInstance() {
        if (answerCorrectDailog == null) {
            answerCorrectDailog = new AnswerCorrectDialog();
        }
        return answerCorrectDailog;
    }


    @Override
    public int setUpLayoutId() {
        return R.layout.dialog_normal_correct;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseDialog dialog) {
        holder.setTextBold(R.id.tv_answer_true);
        holder.setOnClickListener(R.id.tv_dialog_answer_correct, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }
}
