package com.wxjz.module_base.login;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wxjz.module_base.R;
import com.wxjz.module_base.base.BaseDialog;
import com.wxjz.module_base.listener.OnLimitDoubleListener;


/**
 * @ClassName LoginTipsDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-11 09:18
 * @Version 1.0
 */
public class LoginTipsDialog extends BaseDialog {

    private ImageView ivClose;
    private Button btnGotoLogin;
    private onLoginDialog onLoginDialog;

    public LoginTipsDialog(Context context) {
        super(context);
        init(context, 0);
    }


    public LoginTipsDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context, themeResId);
    }

    private void init(Context context, int themeResId) {
        contentView(R.layout.dialog_login_tips);
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

    public void setOnLoginDialog(LoginTipsDialog.onLoginDialog onLoginDialog) {
        this.onLoginDialog = onLoginDialog;
    }
}
