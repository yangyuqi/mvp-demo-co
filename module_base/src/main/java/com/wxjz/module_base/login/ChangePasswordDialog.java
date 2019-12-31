package com.wxjz.module_base.login;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wxjz.module_base.R;
import com.wxjz.module_base.base.BaseDialog;
import com.wxjz.module_base.util.StringUtils;
import com.wxjz.module_base.util.ToastUtil;

/**
 * Created by a on 2019/9/16.
 */

public class ChangePasswordDialog extends BaseDialog {
    private Context context;
    private OnChangePassClickListener mOnChagePassClickListener;
    private boolean originPassInput = false;
    private boolean newPassInput = false;
    private boolean newPassAgaginInput = false;
    private Button btChange;

    public ChangePasswordDialog(Context context, OnChangePassClickListener onChangePassClickListener) {
        super(context);
        this.context = context;
        this.mOnChagePassClickListener = onChangePassClickListener;
        init();
    }

    private void init() {
        contentView(R.layout.change_password_dialog);
        dimAmount(0.5f);
        canceledOnTouchOutside(true);
        gravity(Gravity.CENTER);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        final EditText et_origin_password = findViewById(R.id.et_origin_password);
        final EditText et_new_password = findViewById(R.id.et_new_password);
        final EditText et_new_password_again = findViewById(R.id.et_new_password_again);
        btChange = findViewById(R.id.bt_change);
        btChange.setClickable(false);
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = et_new_password.getText().toString().trim();
                String newPasswordAgain = et_new_password_again.getText().toString().trim();
                String originPass = et_origin_password.getText().toString().trim();
                if (StringUtils.isEmpty(originPass)) {
                    ToastUtil.show(context, "请输入初始密码");
                    return;
                }
                if (StringUtils.isEmpty(newPassword)) {
                    ToastUtil.show(context, "请输入新密码");
                    return;
                }
                if (StringUtils.isEmpty(newPasswordAgain)) {
                    ToastUtil.show(context, "请再次输入新密码");
                    return;
                }
                mOnChagePassClickListener.onChangePassClick(originPass, newPassword);
            }
        });
        et_origin_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(s.toString().trim())) {
                    originPassInput = true;
                } else {
                    originPassInput = false;
                }
                buttonChagePassActive();

            }
        });
        et_new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(s.toString().trim())) {
                    newPassInput = true;
                } else {
                    newPassInput = false;
                }
                buttonChagePassActive();

            }
        });
        et_new_password_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(s.toString().trim())) {
                    newPassAgaginInput = true;
                } else {
                    newPassAgaginInput = false;
                }
                buttonChagePassActive();

            }
        });
    }

    private void buttonChagePassActive() {
        if (originPassInput && newPassInput && newPassAgaginInput) {
            btChange.setBackground(context.getResources().getDrawable(R.drawable.change_password_btn_yellow));
            btChange.setTextColor(Color.parseColor("#000000"));
            btChange.setClickable(true);
        } else {
            btChange.setBackground(context.getResources().getDrawable(R.drawable.change_password_btn_gray));
            btChange.setTextColor(Color.parseColor("#BBBBBB"));
            btChange.setClickable(false);
        }
    }

    public interface OnChangePassClickListener {
        void onChangePassClick(String currentPass, String newPass);
    }
}
