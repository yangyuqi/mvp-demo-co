package com.wxjz.module_base.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wxjz.module_base.R;
import com.wxjz.module_base.base.BaseDialog;
import com.wxjz.module_base.listener.OnLimitDoubleListener;

/**
 * @ClassName MemberPromptDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-07 11:04
 * @Version 1.0
 */
public class MemberPromptDialog extends BaseDialog {
    private ImageView ivClose;
    private Button btnGotoLogin;
    private onLoginDialog onLoginDialog;

    public MemberPromptDialog(Context context) {
        super(context);
        init(context, 0);
    }

    public MemberPromptDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context, 0);
    }

    private void init(Context context, int themeResId) {
        contentView(R.layout.dialog_member_prompt_tips);
        dimAmount(0.5f);
        canceledOnTouchOutside(false);
        gravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        initData(context);
    }

    private void initData(final Context context) {
        ivClose = findViewById(R.id.iv_dialog_close);
        btnGotoLogin = findViewById(R.id.btn_go_to_login);

        ivClose.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (this != null && isShowing()) {
                    dismiss();
                }
            }
        });

        btnGotoLogin.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (onLoginDialog != null) {
                    onLoginDialog.onLogin();
                }
                dismiss();
            }
        });
    }


    public interface onLoginDialog {
        void onLogin();
    }

    public void setOnLoginDialog(MemberPromptDialog.onLoginDialog onLoginDialog) {
        this.onLoginDialog = onLoginDialog;
    }
}
