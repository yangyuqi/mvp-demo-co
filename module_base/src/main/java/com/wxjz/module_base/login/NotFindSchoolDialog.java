package com.wxjz.module_base.login;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.wxjz.module_base.R;
import com.wxjz.module_base.base.BaseDialog;
import com.wxjz.module_base.listener.OnLimitDoubleListener;

/**
 * @ClassName NotFindSchoolDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-12 09:49
 * @Version 1.0
 */
public class NotFindSchoolDialog extends BaseDialog {
    private ImageView ivClose;

    public NotFindSchoolDialog(Context context) {
        super(context);
        init(context, 0);
    }

    public NotFindSchoolDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context, 0);
    }

    private void init(Context context, int i) {
        contentView(R.layout.dialog_not_find_school_tip);
        dimAmount(0.5f);
        canceledOnTouchOutside(false);
        gravity(Gravity.CENTER);
        initData(context);
    }

    private void initData(Context context) {
        ivClose = findViewById(R.id.iv_dialog_close);
        ivClose.setOnClickListener(new OnLimitDoubleListener() {
            @Override
            public void onLimitDouble(View v) {
                if (this != null && isShowing()) {
                    dismiss();
                }
            }
        });
    }
}
