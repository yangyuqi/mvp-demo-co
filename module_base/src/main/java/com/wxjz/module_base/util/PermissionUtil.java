package com.wxjz.module_base.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

/**
 * Created by a on 2019/8/30.
 */

public class PermissionUtil {
    public static void requestPermission(final Context context, final OnPermissionListener onPermissionListener, String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new Rationale<List<String>>() {
                    @Override
                    public void showRationale(Context context, List<String> data, final RequestExecutor executor) {
                        new AlertDialog.Builder(context)
                                .setTitle("权限申请")
                                .setMessage("这里需要申请" + data.get(0))
                                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        executor.execute();
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .create()
                                .show();
                    }
                })
                .onGranted(new Action<List<String>>() {
                               @Override
                               public void onAction(List<String> data) {
                                   onPermissionListener.onGranted();
                               }
                           }
                )
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, data)) {
                            new AlertDialog.Builder(context)
                                    .setTitle("权限获取失败")
                                    .setMessage("没有权限该功能不能使用，是否进入应用设置中进行权限中设置!")
                                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent();
                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                                    intent.setData(uri);
                                                    context.startActivity(intent);
                                                }
                                            }
                                    )
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        } else {
                            onPermissionListener.onDenied();
                            ToastUtil.show(context, "开启权限失败");
                        }
                    }
                })
                .start();


    }

   public interface OnPermissionListener {
        void onDenied();

        void onGranted();
    }
}
