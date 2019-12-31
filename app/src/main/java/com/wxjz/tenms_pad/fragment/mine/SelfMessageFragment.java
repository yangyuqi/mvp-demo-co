package com.wxjz.tenms_pad.fragment.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxjz.module_base.apppickimagev3.ui.MultiImageSelectorActivity;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.constant.PermissionConstants;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.event.UpdateHeadEvent;
import com.wxjz.module_base.util.CropUtil;
import com.wxjz.module_base.util.LogUtils;
import com.wxjz.module_base.util.PermissionUtil;
import com.wxjz.module_base.util.SystemManager;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.module_base.util.UploadHelper;
import com.wxjz.module_base.widgt.ActionSheetDialog;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.MineActivity;
import com.wxjz.tenms_pad.mvp.contract.MyNoteContract;
import com.wxjz.tenms_pad.mvp.contract.SelfMessageContract;
import com.wxjz.tenms_pad.mvp.presenter.MyNotePresenter;
import com.wxjz.tenms_pad.mvp.presenter.SelfMessagePresenter;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by a on 2019/9/11.
 * 个人信息
 */

public class SelfMessageFragment extends BaseMvpFragment<SelfMessagePresenter> implements SelfMessageContract.View, View.OnClickListener {

    private CircleImageView ivHead;
    private TextView tvNickName;
    private TextView tvLearnStage;
    private TextView tvSchool;
    private TextView tvGrade;

    public static SelfMessageFragment getInstance() {
        return new SelfMessageFragment();
    }

    @Override
    protected SelfMessagePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {
        Button bt_exit_login = view.findViewById(R.id.bt_exit_login);
        bt_exit_login.setOnClickListener(this);
        ivHead = view.findViewById(R.id.iv_head);
        tvNickName = view.findViewById(R.id.tv_nick_name);
        tvLearnStage = view.findViewById(R.id.tv_learn_stage);
        tvSchool = view.findViewById(R.id.tv_school);
        tvGrade = view.findViewById(R.id.tv_grade);
        ivHead.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        UserInfoBean currentUserInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        String gradeName = CheckGradeDao.getInstance().queryGradeName();
        if (currentUserInfo != null) {
            Glide.with(this).load(currentUserInfo.getHeadUrl()).error(R.drawable.default_head).into(ivHead);
            tvNickName.setText(currentUserInfo.getFullName());
            int levelId = CheckGradeDao.getInstance().queryleveId();
            tvLearnStage.setText(levelId == 1 ? "小学" : "初中");
            tvSchool.setText(currentUserInfo.getSchName());
            tvGrade.setText(currentUserInfo.getGradeName());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_self_message;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_exit_login:
                SystemManager.getInstance().exit(getActivity());
                break;
            case R.id.iv_head:
                ((MineActivity) mContext).initReplaceSheetDialog("修改头像");
                break;
        }
    }


    /**
     * 修改头像
     *
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateHeadEvent(UpdateHeadEvent e) {
        if (!TextUtils.isEmpty(e.getHeadUrl())) {
            Glide.with(this).load(e.getHeadUrl()).error(R.drawable.default_head).into(ivHead);

        }
    }

    @Override
    public boolean needEventBus() {
        return true;
    }

    /**
     * 提示权限不足
     */
    public void showInsufficientPermission() {
        ToastUtil.showTextToas(getContext(), "你尚未开通学习权限");
    }

    /**
     * 提示帐号过期
     */
    public void AccountExpired() {
        ToastUtil.showTextToas(getContext(), "您的账号已过期，请续费后继续使用");
    }

}
