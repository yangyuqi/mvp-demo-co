package com.wxjz.module_base.login;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wxjz.module_base.R;
import com.wxjz.module_base.base.BaseDialog;
import com.wxjz.module_base.db.bean.CheckedSchool;
import com.wxjz.module_base.db.dao.CheckIdSchoolDao;
import com.wxjz.module_base.listener.OnLimitDoubleListener;

/**
 * @ClassName LosePasswordTipDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-11 15:46
 * @Version 1.0
 */
public class LosePasswordTipDialog extends BaseDialog {

    private Button btnIKnow;
    private String number;
    private TextView tvContactNumber;

    public LosePasswordTipDialog(Context context) {
        super(context);
        init(context, 0);
    }

    public LosePasswordTipDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context, themeResId);
    }

    private void init(Context context, int i) {
        contentView(R.layout.dialog_lose_password);
        dimAmount(0.5f);
        canceledOnTouchOutside(false);
        gravity(Gravity.CENTER);
        initData(context);
    }

    private void initData(Context context) {
        btnIKnow = findViewById(R.id.btn_i_know);
        tvContactNumber = findViewById(R.id.tv_contact_number);
        CheckedSchool checkedSchool = CheckIdSchoolDao.getInstance().getCheckedSchool();
        if (checkedSchool != null) {
            number = checkedSchool.getLxdh();
            if (TextUtils.isEmpty(number)) {
                tvContactNumber.setText("19283029292");
            } else {
                tvContactNumber.setText(number);
            }
        } else {
            tvContactNumber.setText("19283029292");
        }
        btnIKnow.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (this != null && isShowing()) {
                    dismiss();
                }
            }
        });
    }
}
