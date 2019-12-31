package com.wxjz.module_base.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.wxjz.module_base.R;
import com.wxjz.module_base.dialog.MemberPromptDialog;
import com.wxjz.module_base.dialog.TouristChooseDialog;
import com.wxjz.module_base.listener.GetToMemberTipDialogListner;
import com.wxjz.module_base.listener.GoToLoginDialogListener;
import com.wxjz.module_base.listener.OnChooseSchoolDialogListener;
import com.wxjz.module_base.listener.OnLoginDialogListenr;
import com.wxjz.module_base.login.ChooseSchoolDialog;
import com.wxjz.module_base.login.LoginDialog;
import com.wxjz.module_base.login.LoginTipsDialog;
import com.wxjz.module_base.login.LosePasswordTipDialog;
import com.wxjz.module_base.login.NotFindSchoolDialog;


/**
 * Created by a on 2019/9/3.
 */

public class DialogUtil {

    private Dialog dialogLoginTips = null;
    private Dialog loginDialog = null;
    private Dialog chooseDialog = null;
    private Dialog losePassWordDialog = null;
    private Dialog memberTipsDialog = null;
    private Dialog notFIndDialog = null;

    //先私有化这个类
    private DialogUtil() {

    }

    private static volatile DialogUtil instance = null;

    //初始化成功
    //单例模式
    public static DialogUtil getInstance() {
        if (instance == null) {
            synchronized (DialogUtil.class) {
                if (instance == null) {
                    instance = new DialogUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 中间Dialog
     */
    public static Dialog getCenterDialog(Context context, int id) {
        Dialog dialog = new Dialog(context, R.style.Custom_Dialog_Fullscreen);
        dialog.setContentView(id);

        Window dialogWindow = dialog.getWindow();
        //dialogWindow.setWindowAnimations(R.style.mystyle);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) context.getResources().getDimension(R.dimen.dp_121);
        lp.height = (int) context.getResources().getDimension(R.dimen.dp_100);
        lp.alpha = 1.0f;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        return dialog;
    }

    /**
     * 没有学校的提示
     */
    public static Dialog getNotFindSchool(Context context) {
        Dialog notFIndDialog = new NotFindSchoolDialog(context);
        return notFIndDialog;
    }

    /**
     * 游客模式下弹出对话框
     *
     * @return
     */
    public static Dialog getGuseDialog(Context context) {
        Dialog guseDialog = new TouristChooseDialog(context);
        return guseDialog;
    }

    /********************************************重构的代码***********************************************************/

    /**
     * 登录提示的dialog
     *
     * @param context
     * @return
     */
    public void getLoginTip(final Context context, final GoToLoginDialogListener loginListener) {
        dialogLoginTips = new LoginTipsDialog(context);
        ((LoginTipsDialog) dialogLoginTips).setOnLoginDialog(new LoginTipsDialog.onLoginDialog() {
            @Override
            public void onLogin() {
                if (loginListener != null) {
                    loginListener.onLogin();
                }
            }
        });
        dialogLoginTips.show();
    }

    /**
     * 打开登陆页面的对框框
     *
     * @param context
     * @return
     */
    public void getToLogin(Context context, final OnLoginDialogListenr loginListenr) {
        loginDialog = new LoginDialog(context);
        ((LoginDialog) loginDialog).setOnloginViewItemClick(new LoginDialog.OnloginViewClick() {

            @Override
            public void beginRequest() {
                if (loginListenr != null) {
                    loginListenr.onBeginRequest();
                }
            }

            @Override
            public void onLoginSuccess() {
                if (loginListenr != null) {
                    loginListenr.onLoginSuccess(loginDialog);
                }
            }

            @Override
            public void onLoginError() {
                if (loginListenr != null) {
                    loginListenr.onLoginError();
                }
            }

            @Override
            public void onChooseSchool() {
                if (loginListenr != null) {
                    loginListenr.onChooseSchool();
                }
            }

            @Override
            public void onLosePassword() {
                if (loginListenr != null) {
                    loginListenr.onLosePassword();
                }
                loginDialog.dismiss();
            }
        });
        loginDialog.show();
    }

    /**
     * 选取学校
     *
     * @param context
     * @return
     */
    public void getToChooseSchoolDialog(Context context, final OnChooseSchoolDialogListener onChooseSchoolDialogListener) {
        chooseDialog = new ChooseSchoolDialog(context);
        ((ChooseSchoolDialog) chooseDialog).setChooseSchoolItemView(new ChooseSchoolDialog.ChooseSchoolItemView() {
            @Override
            public void onLoginShow() {
                if (onChooseSchoolDialogListener != null) {
                    onChooseSchoolDialogListener.onLoginDialogShow();
                }
            }

            @Override
            public void onNotFindSchool() {
                if (onChooseSchoolDialogListener != null) {
                    onChooseSchoolDialogListener.onNotFindSchool();
                }
            }
        });
        chooseDialog.show();
    }

    /**
     * 忘记密码
     *
     * @param context
     * @return
     */

    public void getToLosePassWordTips(Context context) {
        losePassWordDialog = new LosePasswordTipDialog(context);
        losePassWordDialog.show();
    }

    /**
     * 没有学校的提示
     */
    public void getToNotFindSchool(Context context) {
        notFIndDialog = new NotFindSchoolDialog(context);
        notFIndDialog.show();
    }


    /**
     * 弹出不是会员的dialog
     */
    public void getToMemberTip(Context context, final GetToMemberTipDialogListner getToMemberTipDialogListner) {
        memberTipsDialog = new MemberPromptDialog(context);
        ((MemberPromptDialog) memberTipsDialog).setOnLoginDialog(new MemberPromptDialog.onLoginDialog() {
            @Override
            public void onLogin() {
                if (getToMemberTipDialogListner != null) {
                    getToMemberTipDialogListner.onGotologin();
                }
            }
        });
        memberTipsDialog.show();
    }


    /**
     * 释放对话框的引用
     */
    public void release() {
        if (dialogLoginTips != null) {
            dialogLoginTips = null;
        }

        if (loginDialog != null) {
            loginDialog = null;
        }

        if (chooseDialog != null) {
            chooseDialog = null;
        }

        if (losePassWordDialog != null) {
            losePassWordDialog = null;
        }
        if (memberTipsDialog != null) {
            memberTipsDialog = null;
        }
        if (notFIndDialog != null) {
            notFIndDialog = null;
        }
    }
}
