package com.wxjz.tenms_pad.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxjz.module_base.event.ClassifyClickEvent;
import com.wxjz.tenms_pad.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by a on 2019/9/3.
 */

public class DialogUtil {

    /**
     * 中间Dialog
     */
    public static Dialog getCenterDialog(Context context, int id) {
        Dialog dialog = new Dialog(context, R.style.Custom_Dialog_Fullscreen);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        return dialog;
    }

    /**
     * @param context
     * @param currentSelect 当前学段 1小学 2初中
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Dialog getClassifyDialog(Context context, int currentSelect) {
        final Dialog centerDialog = getCenterDialog(context, R.layout.classify_dialog);
        centerDialog.setCancelable(false);
        centerDialog.setCanceledOnTouchOutside(false);
        RelativeLayout rl_xiaoxue = centerDialog.findViewById(R.id.rl_xiaoxue);
        RelativeLayout rl_chuzhong = centerDialog.findViewById(R.id.rl_chuzhong);
        ImageView iv_check_chuzhong = centerDialog.findViewById(R.id.iv_check_chuzhong);
        ImageView iv_check_xiaoxue = centerDialog.findViewById(R.id.iv_check_xiaoxue);
        TextView tv_xiaoxue = centerDialog.findViewById(R.id.tv_xiaoxue);
        TextView tv_chuzhong = centerDialog.findViewById(R.id.tv_chuzhong);
        ImageView ivClose = centerDialog.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerDialog.dismiss();
            }
        });
        rl_xiaoxue.setBackground(currentSelect == 1 ? context.getDrawable(R.drawable.xueduan_select) : context.getDrawable(R.drawable.xueduan_unselect));
        rl_chuzhong.setBackground(currentSelect == 1 ? context.getDrawable(R.drawable.xueduan_unselect) : context.getDrawable(R.drawable.xueduan_select));

        iv_check_xiaoxue.setVisibility(currentSelect == 1 ? View.VISIBLE : View.GONE);
        iv_check_chuzhong.setVisibility(currentSelect == 1 ? View.GONE : View.VISIBLE);
        tv_xiaoxue.setTextColor(currentSelect == 1 ? Color.parseColor("#303133") : Color.parseColor("#BBBBBB"));
        tv_chuzhong.setTextColor(currentSelect == 1 ? Color.parseColor("#BBBBBB") : Color.parseColor("#303133"));
        rl_xiaoxue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerDialog.dismiss();
                EventBus.getDefault().post(new ClassifyClickEvent(1));
            }
        });
        rl_chuzhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerDialog.dismiss();
                EventBus.getDefault().post(new ClassifyClickEvent(2));
            }
        });
        return centerDialog;

    }
}
