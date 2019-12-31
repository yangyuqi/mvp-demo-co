package com.wxjz.module_base.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wxjz.module_base.R;

public class ToastUtil {

    private static Toast toast;

    public ToastUtil() {

    }

    /**
     * 完全自定义布局Toast
     *
     * @param context
     * @param view
     */
    public ToastUtil(Context context, View view, int duration) {
        toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(duration);
    }

    public static void show(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(resId);
        }

        toast.show();
    }

    public static void show(Context context, CharSequence text) {
        try {
            if (toast == null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(text);
            }
            toast.show();
        } catch (Exception e) {
            Looper.prepare();
            if (toast == null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(text);
            }
            toast.show();
            Looper.loop();
        }
    }

    //    //显示文本+图片的Toast
//    public static void showImageToas(Context context,String message){
//        View toastview= LayoutInflater.from(context).inflate(R.layout.toast_image_layout,null);
//        TextView text = (TextView) toastview.findViewById(R.id.tv_message);
//        text.setText(message);    //要提示的文本
//        Toast toast=new Toast(context);   //上下文
//        toast.setGravity(Gravity.CENTER,0,0);   //位置居中
//        toast.setDuration(Toast.LENGTH_SHORT);  //设置短暂提示
//        toast.setView(toastview);   //把定义好的View布局设置到Toast里面
//        toast.show();
//    }
    //显示自定义背景文本的Toast
    public static void showTextToas(Context context, String message) {
        View toastview = LayoutInflater.from(context).inflate(R.layout.toast_study_status, null);
        TextView text = (TextView) toastview.findViewById(R.id.tv_message);
        text.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastview);
        toast.show();
    }

}
