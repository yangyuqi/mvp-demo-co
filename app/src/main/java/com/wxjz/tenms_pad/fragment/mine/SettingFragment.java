package com.wxjz.tenms_pad.fragment.mine;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.login.ChangePasswordDialog;
import com.wxjz.module_base.util.FileUtil;
import com.wxjz.module_base.util.GlideCatchUtil;
import com.wxjz.module_base.util.SystemManager;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.tenms_pad.R;

import com.wxjz.tenms_pad.mvp.contract.SettingContract;
import com.wxjz.tenms_pad.mvp.presenter.SettingPresenter;

/**
 * Created by a on 2019/9/11.
 * 设置
 */

public class SettingFragment extends BaseMvpFragment<SettingPresenter> implements SettingContract.View, View.OnClickListener {

    private TextView tvLoginNum;
    private TextView tvCache;
    private ChangePasswordDialog changePasswordDialog;

    public static SettingFragment getInstance() {
        return new SettingFragment();
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected void initView(View view) {
        tvLoginNum = view.findViewById(R.id.tv_login_num);
        tvCache = view.findViewById(R.id.tv_clear_cache);
        RelativeLayout rl_clear_cache = view.findViewById(R.id.rl_clear_cache);
        rl_clear_cache.setOnClickListener(this);
        RelativeLayout rlChangePass = view.findViewById(R.id.rl_change_pass);
        Button bt_exit_login = view.findViewById(R.id.bt_exit_login);
        bt_exit_login.setOnClickListener(this);
        rlChangePass.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        UserInfoBean currentUserInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (currentUserInfo != null) {
            tvLoginNum.setText(currentUserInfo.getLoginName());
        }
        //glide
        String cacheSize = GlideCatchUtil.getInstance().getCacheSize();
        tvCache.setText(cacheSize);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.rl_change_pass:
                showChangePassWord();
                break;

            case R.id.bt_exit_login:
                SystemManager.getInstance().exit(getActivity());
                break;
            case R.id.rl_clear_cache:
                GlideCatchUtil.getInstance().cleanCatchDisk();
                String cacheSize = GlideCatchUtil.getInstance().getCacheSize();
                tvCache.setText(cacheSize);
                break;
        }
    }


    /**
     * 修改密码
     */
    private void showChangePassWord() {
        changePasswordDialog = new ChangePasswordDialog(mContext, new ChangePasswordDialog.OnChangePassClickListener() {
            @Override
            public void onChangePassClick(String currentPass, String newPass) {
                String userId = UserInfoDao.getInstence().getCurrentUserInfo().getUserId();
                if (userId != null) {
                    mPresenter.changePassword(userId, currentPass, newPass);

                }
            }
        });
        changePasswordDialog.show();
    }

    @Override
    public void onChangePassSuccess() {
        toast("修改密码成功！");
        if (changePasswordDialog!=null){
            changePasswordDialog.dismiss();
        }
    }
}
