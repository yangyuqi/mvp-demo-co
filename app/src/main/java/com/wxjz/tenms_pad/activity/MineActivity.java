package com.wxjz.tenms_pad.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxjz.module_base.apppickimagev3.ui.MultiImageSelectorActivity;
import com.wxjz.module_base.bean.UpdateInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.event.UpdateHeadEvent;
import com.wxjz.module_base.event.UpdateUserInfoEvent;
import com.wxjz.module_base.util.CropUtil;
import com.wxjz.module_base.util.LogUtils;
import com.wxjz.module_base.util.PermissionUtil;
import com.wxjz.module_base.base.BaseFragment;
import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.util.UploadHelper;
import com.wxjz.module_base.widgt.ActionSheetDialog;
import com.wxjz.module_base.constant.PermissionConstants;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.fragment.mine.DownloadManageFragment;
import com.wxjz.tenms_pad.fragment.mine.DownloadManageFragment2;
import com.wxjz.tenms_pad.fragment.mine.LearnDurationFragment;
import com.wxjz.tenms_pad.fragment.mine.LearnRecordFragment;
import com.wxjz.tenms_pad.fragment.mine.MyErrorFragment;
import com.wxjz.tenms_pad.fragment.mine.MyNoteFragment;
import com.wxjz.tenms_pad.fragment.mine.SelfMessageFragment;
import com.wxjz.tenms_pad.fragment.mine.SettingFragment;
import com.wxjz.tenms_pad.mvp.contract.MineContract;
import com.wxjz.tenms_pad.mvp.presenter.MinePresenter;
import com.yalantis.ucrop.UCrop;


import org.greenrobot.eventbus.EventBus;
import org.litepal.util.LogUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineActivity extends BaseMvpActivity<MinePresenter> implements MineContract.View, View.OnClickListener {
    private static final int IMAGE_URL_HEAD = 100;
    private RadioGroup radio_group;
    private List<BaseFragment> mFragments;
    private int mCurFragmentPos = -1;
    private TextView tvNickName;
    private TextView tvXueDuan;
    private CircleImageView ivHead;
    private File cameraFile;
    private String localImg;
    private NoLeakHandler mHandler = new NoLeakHandler(this);
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        radio_group = findViewById(R.id.radio_group);
        ImageView ivBack = findViewById(R.id.iv_back);
        tvNickName = findViewById(R.id.tv_nick_name);
        tvXueDuan = findViewById(R.id.tv_xue_duan);
        ivHead = findViewById(R.id.iv_head);
        ivHead.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        initListener();

    }

    private void initListener() {
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    /**
                     * 下载管理
                     */
                    case R.id.rb_download:
                        switchFragmentByPosition(0);
                        break;
                    /**
                     * 学习记录
                     */
                    case R.id.rb_learn_record:
                        switchFragmentByPosition(1);
                        break;
                    /**
                     * 我的笔记
                     */
                    case R.id.rb_my_note:
                        switchFragmentByPosition(2);
                        break;
                    /**
                     * 我的错题
                     */
                    case R.id.rb_error_problem:
                        switchFragmentByPosition(3);
                        break;
                    /**
                     * 学习时长
                     */
                    case R.id.rb_learn_duration:
                        switchFragmentByPosition(4);
                        break;
                    /**
                     * 个人信息
                     */
                    case R.id.rb_self_message:
                        switchFragmentByPosition(5);
                        break;
                    /**
                     * 设置
                     */
                    case R.id.rb_setting:
                        switchFragmentByPosition(6);
                        break;

                }
            }
        });

        findViewById(R.id.tv_xue_jifen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,IntegralActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        initFragments();
        getIntentData();
        mPresenter.getUserInfo();


    }

    private void getIntentData() {
        int checkPosition = getIntent().getIntExtra("checkPosition", -1);
        if (checkPosition == -1) {
            //默认选中最后一个
            switchFragmentByPosition(mFragments.size() - 1);
        } else if (checkPosition == 0) {
            RadioButton radioButton = findViewById(R.id.rb_download);
            radioButton.setChecked(true);
        } else if (checkPosition == 1) {
            RadioButton radioButton = findViewById(R.id.rb_learn_record);
            radioButton.setChecked(true);
        }

    }

    private void setViewData(com.wxjz.module_base.bean.UserInfoBean currentUserInfo) {
        int   levelId = CheckGradeDao.getInstance().queryleveId();
        if (levelId == 1) {
            tvXueDuan.setText("小学");
        } else if (levelId == 2) {
            tvXueDuan.setText("初中");
        }
        if (currentUserInfo != null) {

            tvNickName.setText(currentUserInfo.getUser().getFullName());
            Glide.with(this).load(currentUserInfo.getUser().getHeadUrl()).error(R.drawable.default_head).into(ivHead);
        }

    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        DownloadManageFragment2 dmFragment2 = DownloadManageFragment2.getInstance();
        // DownloadManageFragment dmFragment = DownloadManageFragment.getInstance();
        LearnRecordFragment lrFragment = LearnRecordFragment.getInstance();
        MyNoteFragment noteFragment = MyNoteFragment.getInstance();
        MyErrorFragment errorFragment = MyErrorFragment.getInstance();
        LearnDurationFragment durationFragment = LearnDurationFragment.getInstance();
        SelfMessageFragment messageFragment = SelfMessageFragment.getInstance();
        SettingFragment settingFragment = SettingFragment.getInstance();
        mFragments.add(dmFragment2);
        // mFragments.add(dmFragment);
        mFragments.add(lrFragment);
        mFragments.add(noteFragment);
        mFragments.add(errorFragment);
        mFragments.add(durationFragment);
        mFragments.add(messageFragment);
        mFragments.add(settingFragment);
    }

    /**
     * 根据tab位置切换fragment
     *
     * @param position
     */
    private void switchFragmentByPosition(int position) {
        if (mFragments.size() <= 0 || position >= mFragments.size()) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment showFragment = mFragments.get(position);
        BaseFragment currentFragment = mCurFragmentPos == -1 ? null : mFragments.get(mCurFragmentPos);
        if (currentFragment != null && currentFragment.isAdded()) {
            transaction.hide(currentFragment);
        }
        if (!showFragment.isAdded()) {
            transaction.add(R.id.frameLayout, showFragment, String.valueOf(position));
        }
        transaction.show(showFragment);
        transaction.commit();
        mCurFragmentPos = position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            /**
             * 修改头像
             */
            case R.id.iv_head:
                initReplaceSheetDialog("修改头像");
                break;
        }
    }

    public void initReplaceSheetDialog(String title) {
        ActionSheetDialog replaceSheetDialog = new ActionSheetDialog(this).builder();
        replaceSheetDialog.setTitle(title);
        replaceSheetDialog.addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new
                ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        PermissionUtil.requestPermission(MineActivity.this, new PermissionUtil.OnPermissionListener() {


                            @Override
                            public void onDenied() {

                            }

                            @Override
                            public void onGranted() {
                                cameraFile = CropUtil.createTmpFile();
                                CropUtil.openCamera(MineActivity.this, cameraFile);
                            }
                        }, PermissionConstants.getPermissionsFromGroup(PermissionConstants.CAMERA));
                    }
                });

        replaceSheetDialog.addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Blue, new
                ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        CropUtil.openSinglePhoto(MineActivity.this);
                    }
                });

        replaceSheetDialog.setSheetItems();
        replaceSheetDialog.showDialog();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CropUtil.OPEN_CAMERA_REQUEST_CODE) {
            //拍照返回
            if (cameraFile != null && cameraFile.exists()) {
                CropUtil.startCrop(this, cameraFile.getAbsolutePath());
            }
        } else if (resultCode == RESULT_OK && requestCode == CropUtil.OPEN_PHOTO_REQUEST_CODE) {
            //选择相册返回
            List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if (paths != null && paths.size() > 0) {
                CropUtil.startCrop(this, paths.get(0));
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //裁剪返回
            Uri resultUri = UCrop.getOutput(data);
            operationImage(resultUri);
        }
    }

    /**
     * 处理图片
     */
    private void operationImage(Uri cropImageUri) {
        if (cropImageUri == null) {
            return;
        }
        localImg = CropUtil.getRealFilePath(this, cropImageUri);
        uploadImg(new File(localImg), ivHead);
        Glide.with(this).load(localImg).error(R.drawable.default_head).into(ivHead);
        //saveEditInfo();
    }


    private void uploadImg(final File file, final ImageView imageView) {
        long size = file.length() / 1024 / 1024;
        if (size >= 10) {
            toast("图片大小不能超过10M");
            return;
        }
        showLoading();

        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadHelper helper = new UploadHelper();
                imgUrl = helper.upload(file, new UploadHelper.OnUploadListener() {
                    @Override
                    public void onError() {
                        hideLoading();
                    }

                    @Override
                    public void onFinish(String img) {

                    }
                });
                LogUtils.e("ql", "imgUrl:" + imgUrl);
                Message obtain = Message.obtain();
                obtain.what = IMAGE_URL_HEAD;
                obtain.obj = imgUrl;
                mHandler.sendMessage(obtain);
            }
        }).start();
    }

    @Override
    public void onUpdateHeadUrl(UpdateInfoBean updateInfoBean) {
        toast("上传头像成功");
        EventBus.getDefault().post(new UpdateUserInfoEvent(true));
        EventBus.getDefault().post(new UpdateHeadEvent(imgUrl));
        hideLoading();

    }

    @Override
    public void onUpdateHeadError(String error) {
        toast(error);
        hideLoading();

    }

    @Override
    public void onUserInfo(com.wxjz.module_base.bean.UserInfoBean infoBean) {
        setViewData(infoBean);

    }


    private class NoLeakHandler extends Handler {
        private WeakReference<MineActivity> mActivity;

        public NoLeakHandler(MineActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mActivity.get() != null) {
                switch (msg.what) {
                    case IMAGE_URL_HEAD:
                        String imageUrlHead = (String) msg.obj;
                        updateImgUrl(imageUrlHead);
                        break;

                }
            }
        }


    }

    /**
     * 修改服务器头像路径
     *
     * @param imageUrlHead
     */
    public void updateImgUrl(String imageUrlHead) {
        mPresenter.updateHeadUrl(imageUrlHead);

    }
}
