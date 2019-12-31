package com.wxjz.module_base.login;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxjz.module_base.R;
import com.wxjz.module_base.base.BaseDialog;
import com.wxjz.module_base.db.bean.CheckedSchool;
import com.wxjz.module_base.db.dao.CheckIdSchoolDao;
import com.wxjz.module_base.event.SchoolItemPosition;
import com.wxjz.module_base.listener.LoginListener;
import com.wxjz.module_base.listener.OnLimitDoubleListener;
import com.wxjz.module_base.util.SystemManager;
import com.wxjz.module_base.util.ToastUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @ClassName LoginDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-11 09:59
 * @Version 1.0
 */
public class LoginDialog extends BaseDialog {

    private Context mContext;
    /**
     * 登录号输入框
     */
    private EditText editTextNumber;
    /**
     * 密码输入框
     */
    private EditText editTextPassword;
    /**
     * 忘记密码
     */
    private TextView tvLosePassword;
    /**
     * 游客模式登录
     */
    private TextView tvGuestLogin;
    /**
     * 判断输入
     */
    private boolean edTextNumberLength;
    /**
     * 判断输入
     */
    private boolean edTextPasswordLength;
    /**
     * 登录按钮
     */
    private TextView tvLogin;


    /**
     * 切换学校
     */
    private ImageView ivImageSchoolIcon;

    private OnloginViewClick onloginViewItemClick;

    private TextView tvSchoolName;

    private String schoolName;

    private int schoolId;

    public LoginDialog(Context context) {
        super(context);
        init(context, 0);
    }


    public LoginDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context, themeResId);
    }


    private void init(Context context, int i) {
        contentView(R.layout.dialog_login);
        dimAmount(0.5f);
        canceledOnTouchOutside(true);
        gravity(Gravity.CENTER);
        initView(context);
        initData(context);
    }

    private void initView(Context context) {
        mContext = context;
        editTextNumber = findViewById(R.id.ed_login_codenumber);
        editTextPassword = findViewById(R.id.ed_login_password);
        tvLosePassword = findViewById(R.id.tv_lose_password);
        tvGuestLogin = findViewById(R.id.tv_guest_login);
        tvLogin = findViewById(R.id.tv_login_btn);
        ivImageSchoolIcon = findViewById(R.id.iv_school_icon);
        tvSchoolName = findViewById(R.id.tv_school_name);
    }

    private void initData(Context context) {
        CheckedSchool checkedSchool = CheckIdSchoolDao.getInstance().getCheckedSchool();
        if (checkedSchool != null) {
            schoolId = checkedSchool.getSchId();
            String schName = checkedSchool.getSchName();
            tvSchoolName.setText("当前为" + schName);
        }
        setListener(context);
    }

    private void setListener(final Context context) {
        //忘记密码
        if (tvLosePassword != null) {
            tvLosePassword.setOnClickListener(new OnLimitDoubleListener() {
                @Override
                public void onLimitDouble(View v) {
                    if (onloginViewItemClick != null) {
                        onloginViewItemClick.onLosePassword();
                    }
                }
            });
        }
        //随便看看
        if (tvGuestLogin != null) {
            tvGuestLogin.setOnClickListener(new OnLimitDoubleListener() {
                @Override
                public void onLimitDouble(View v) {
                    dismiss();
                }
            });
        }
        //登录按钮
        if (tvLogin != null) {
            tvLogin.setOnClickListener(new OnLimitDoubleListener() {
                @Override
                public void onLimitDouble(View v) {
                    if (edTextNumberLength) {
                        if (edTextPasswordLength) {
                            if (schoolId == 0) {
                                //说明是当前没有学校ID
                                ToastUtil.show(mContext, "请选择学校，当前学校代码有误");
                            } else {
                                if (onloginViewItemClick != null) {
                                    onloginViewItemClick.beginRequest();
                                }
                                SystemManager.getInstance().login(editTextNumber.getText().toString(), editTextPassword.getText().toString(), schoolId, new LoginListener() {
                                    @Override
                                    public void loginSuccess() {

                                        if (onloginViewItemClick != null) {
                                            onloginViewItemClick.onLoginSuccess();
                                        }
                                    }

                                    @Override
                                    public void onLoginFailed() {
                                        if (onloginViewItemClick != null) {
                                            onloginViewItemClick.onLoginError();
                                        }
                                    }
                                });
                            }

                        } else {
                            ToastUtil.show(context, "请输入密码");
                        }
                    } else {
                        ToastUtil.show(context, "请输入帐号");
                    }
                }
            });
        }
        //切换学校
        if (ivImageSchoolIcon != null) {
            ivImageSchoolIcon.setOnClickListener(new OnLimitDoubleListener() {
                @Override
                public void onLimitDouble(View v) {
                    if (onloginViewItemClick != null) {
                        onloginViewItemClick.onChooseSchool();
                    }
                }
            });
        }

        if (editTextNumber != null) {
            editTextNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    StringBuffer stringBuffer = new StringBuffer(editable);
                    if (stringBuffer.length() > 0) {
                        if (edTextPasswordLength) {
                            tvLogin.setBackground(getContext().getResources().getDrawable(R.drawable.shape_yellow_radius_5));
                            tvLogin.setTextColor(getContext().getResources().getColor(R.color.black));
                        }
                        edTextNumberLength = true;
                    } else {
                        tvLogin.setBackground(getContext().getResources().getDrawable(R.drawable.shape_login_btn_gray_radius_5));
                        tvLogin.setTextColor(getContext().getResources().getColor(R.color.grayBBBBBB));
                        edTextNumberLength = false;
                    }
                }
            });
        }
        if (editTextPassword != null) {
            editTextPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void afterTextChanged(Editable editable) {
                    StringBuffer stringBuffer = new StringBuffer(editable);
                    if (stringBuffer.length() > 0) {
                        if (edTextNumberLength) {
                            tvLogin.setBackground(getContext().getResources().getDrawable(R.drawable.shape_yellow_radius_5));
                            tvLogin.setTextColor(getContext().getResources().getColor(R.color.black));
                        }
                        edTextPasswordLength = true;
                    } else {
                        tvLogin.setBackground(getContext().getResources().getDrawable(R.drawable.shape_login_btn_gray_radius_5));
                        tvLogin.setTextColor(getContext().getResources().getColor(R.color.grayBBBBBB));
                        edTextPasswordLength = false;
                    }
                }
            });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChooseItem(SchoolItemPosition schoolItemPosition) {
        schoolId = schoolItemPosition.getSchoolId();
        schoolName = schoolItemPosition.getSchoolName();
        if (tvSchoolName != null) {
            if (!TextUtils.isEmpty(schoolItemPosition.getSchoolName())) {
                tvSchoolName.setText("当前为" + schoolItemPosition.getSchoolName());
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setOnloginViewItemClick(OnloginViewClick onloginViewItemClick) {
        this.onloginViewItemClick = onloginViewItemClick;
    }

    public interface OnloginViewClick {

        void beginRequest();

        void onLoginSuccess();

        void onLoginError();

        void onChooseSchool();

        void onLosePassword();
    }
}
