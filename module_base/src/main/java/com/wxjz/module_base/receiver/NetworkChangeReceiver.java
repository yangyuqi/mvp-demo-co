package com.wxjz.module_base.receiver;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wxjz.module_base.R;
import com.wxjz.module_base.http.ExceptionHandler;
import com.wxjz.module_base.util.AppManager;
import com.wxjz.module_base.util.DialogUtil;
import com.wxjz.module_base.util.NetworkUtil;
import com.wxjz.module_base.widgt.NoNetworkTip;

/**
 * Created by a on 2019/7/5.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static Dialog badNetDialog;
    private static Activity activity;
    private static Activity lastActivity;
    private NoNetworkTip noNetworkTip;

    public NetworkChangeReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (noNetworkTip == null) {
//            noNetworkTip = new NoNetworkTip(activity);
//        }
//        if (NetworkUtil.isNetworkAvailable(context)) {
//            if (noNetworkTip != null && noNetworkTip.isShowing()) {
//                noNetworkTip.dismiss();
//            }
//        } else {
//            if (noNetworkTip != null && !noNetworkTip.isShowing()) {
//                noNetworkTip.show();
//            }
//        }
        if (NetworkUtil.isNetworkAvailable(context)) {
            ExceptionHandler.isNetWorkContent = true;
            if (badNetDialog != null) {
                badNetDialog.dismiss();
                badNetDialog = null;
            }
        } else {
            showBadNetDialog();
            ExceptionHandler.isNetWorkContent = false;
            if (badNetDialog != null && !badNetDialog.isShowing()) {
                badNetDialog.show();
            }

        }
    }

    public void onDestroy() {
        if (noNetworkTip != null && noNetworkTip.isShowing()) {
            noNetworkTip.dismiss();
        }
        activity = null;
        noNetworkTip = null;
    }

    private static void showBadNetDialog() {
        if (lastActivity == null) {
            activity = AppManager.getAppManager().currentActivity();
            badNetDialog = DialogUtil.getCenterDialog(activity, R.layout.bad_net_dialog);
            lastActivity = activity;
        } else if (lastActivity != activity) {
            activity = AppManager.getAppManager().currentActivity();
            badNetDialog = DialogUtil.getCenterDialog(activity, R.layout.bad_net_dialog);
            lastActivity = activity;
        }

    }
}
