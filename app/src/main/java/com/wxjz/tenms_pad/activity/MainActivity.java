package com.wxjz.tenms_pad.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.LevelListBean;
import com.wxjz.module_base.bean.UserInfoBean;
import com.wxjz.module_base.constant.PermissionConstants;
import com.wxjz.module_base.db.bean.CheckedSchool;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.CheckIdSchoolDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.dialog.MemberPromptDialog;
import com.wxjz.module_base.dialog.TouristChooseDialog;
import com.wxjz.module_base.event.ClassifyClickEvent;
import com.wxjz.module_base.event.ExitEvent;
import com.wxjz.module_base.event.GuestChooseGradeEvent;
import com.wxjz.module_base.event.LoginSuccessEvent;
import com.wxjz.module_base.event.UpdateUserInfoEvent;
import com.wxjz.module_base.listener.GetToMemberTipDialogListner;
import com.wxjz.module_base.listener.GoToLoginDialogListener;
import com.wxjz.module_base.listener.OnChooseSchoolDialogListener;
import com.wxjz.module_base.listener.OnLoginDialogListenr;
import com.wxjz.module_base.login.LoginDialog;
import com.wxjz.module_base.login.LoginTipsDialog;
import com.wxjz.module_base.util.DialogUtil;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.module_base.util.PermissionUtil;
import com.wxjz.module_base.util.ScreenUtil;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.tenms_pad.adapter.HomePageCourseAdapter;
import com.wxjz.tenms_pad.mvp.contract.MainContract;
import com.wxjz.tenms_pad.mvp.presenter.Mainpresenter;
import com.wxjz.tenms_pad.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

@Route(path = "/main/main_activity")
public class MainActivity extends BaseMvpActivity<Mainpresenter> implements MainContract.View, View.OnClickListener {
    private SlidingTabLayout sl_tablayout;
    private ViewPager viewPager;
    /**
     * 当前选择的学段
     */
    private int currentSelect = 1;
    private int currentSubId = 0;
    private TextView tv_classify;
    private RelativeLayout edit_search, rl_classify;
    private ImageView iv_drop;
    private TextView tvSearchContent;
    private ImageView ivSearch, ivDown;

    private LoginTipsDialog tipsDialog;
    private LoginDialog loginDialog;
    private MemberPromptDialog memberPromptDialog;

    private long firstTime = 0;
    private com.wxjz.module_base.db.bean.UserInfoBean userInfo;
    private ImageView ivRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean needEventBus() {
        return true;
    }

    @Override
    protected Mainpresenter createPresenter() {
        return new Mainpresenter(this);
    }

    @Override
    protected void initData() {
        userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        mPresenter.getClassifyData();
        //LogUtils.d("TAG", "SHA==:" + Sha1Utils.getCertificateSHA1Fingerprint(this));
    }

    @Override
    protected void initView() {
        super.initView();
        ScreenUtil.init(this);
        sl_tablayout = findViewById(R.id.sl_tablayout);
        viewPager = findViewById(R.id.view_pager);
        edit_search = findViewById(R.id.rl_search);
        edit_search.setOnClickListener(this);
        tv_classify = findViewById(R.id.iv_classify);
        rl_classify = findViewById(R.id.rl_classify);
        ivSearch = findViewById(R.id.ivSearch);
        tvSearchContent = findViewById(R.id.tvSearchContent);
        iv_drop = findViewById(R.id.iv_drop);
        ivDown = findViewById(R.id.ivDownload);
        ivRecord = findViewById(R.id.ivRecord);
        ivRecord.setOnClickListener(this);
        ImageView ivMine = findViewById(R.id.ivMine);
        ivMine.setOnClickListener(this);
        rl_classify.setOnClickListener(this);
        ivDown.setOnClickListener(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private void requestPermission() {
        PermissionUtil.requestPermission(this, new PermissionUtil.OnPermissionListener() {
            @Override
            public void onDenied() {

            }

            @Override
            public void onGranted() {
                //先判断是否登录
                if (userInfo != null) {

                    JumpUtil.jump2Mine(mContext, MineActivity.class, 0);
                } else {
                    showLoginTipsDialog();
                }
            }
        }, PermissionConstants.getPermissionsFromGroup(PermissionConstants.STORAGE));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onTopTabData(final List<CourseItemPage> courseItemPages) {
        HomePageCourseAdapter homePageCourseAdapter = new HomePageCourseAdapter(getSupportFragmentManager());
        homePageCourseAdapter.setPages(courseItemPages);
        viewPager.setAdapter(homePageCourseAdapter);
        homePageCourseAdapter.notifyDataSetChanged();
        sl_tablayout.setViewPager(viewPager);
        sl_tablayout.setCurrentTab(0);
        sl_tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currentSubId = courseItemPages.get(position).getId();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * da当前选中的学段
     *
     * @param currentId 1小学 2初中
     */
    @Override
    public void onClassifyData(int currentId) {
        /**
         * 获取tab数据
         */
        mPresenter.getTopTabs(currentId);
        String gradeName = CheckGradeDao.getInstance().queryGradeName();
        if (userInfo == null) {
            if (TextUtils.isEmpty(gradeName)) {
                showGuestGradeDialog();
            }
        }
        if (TextUtils.isEmpty(gradeName)) {
            tv_classify.setText("一年级");
        } else {
            tv_classify.setText(gradeName);
        }

    }


    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        UserInfoDao.getInstence().saveUserInfo(userInfoBean);
        userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfo.getIsMember() == 1) {
            //如果是会员用户登陆以后，我存储年级的ID,高远说，只有年级的ID是一定可以拿到的，学习阶段拿不到
            CheckGradeDao.getInstance().updataGuestChooseGradeId(userInfo.getNianji());
            mPresenter.getLeveListNoLevelID(false);
        } else {
            showMemberPromptDialog();
        }
    }

    /**
     * 只要是会员用户登陆，我就拿选择的年级去匹配接口数据
     *
     * @param levelListBeans
     */
    @Override
    public void onLevelListByNoLevelID(List<LevelListBean> levelListBeans) {
        String gradeId = CheckGradeDao.getInstance().queryGradeId();
        userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (!TextUtils.isEmpty(gradeId)) {
            for (int i = 0; i < levelListBeans.size(); i++) {
                for (int j = 0; j < levelListBeans.get(i).getGradeList().size(); j++) {
                    if (gradeId.equals(levelListBeans.get(i).getGradeList().get(j).getId())) {
                        currentSelect = levelListBeans.get(i).getId();
                        CheckGradeDao.getInstance().addGuestChooseGrade(levelListBeans.get(i).getId(), levelListBeans.get(i).getLevelName(), levelListBeans.get(i).getGradeList().get(j).getId(), levelListBeans.get(i).getGradeList().get(j).getGradeName(), true);
                        refreshPage();
                        return;
                    }
                }
            }
            //如果是会员匹配不到学段的话
            ToastUtil.show(this, "当前会员用户的年级与数据库信息不相符，请练习管理员");
            CheckGradeDao.getInstance().addGuestChooseGrade(1, "小学", "P1", "一年级", false);
            refreshPage();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void widgetClick(View view) {


        switch (view.getId()) {
            case R.id.ivDownload:
                if (userInfo != null) {
                    requestPermission();
                } else {
                    showLoginTipsDialog();
                }
                break;
            case R.id.rl_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("subId", currentSubId);
                startActivity(intent);
                break;
            case R.id.rl_classify:
                showGuestGradeDialog();
                break;
            case R.id.ivMine:
                if (userInfo != null) {
                    Intent intentMine = new Intent(this, MineActivity.class);
                    startActivity(intentMine);
                } else {
                    showLoginTipsDialog();
                }

                break;
            case R.id.ivRecord:
                if (userInfo != null) {
                    JumpUtil.jump2Mine(mContext, MineActivity.class, 1);

                } else {
                    showLoginTipsDialog();
                }
                break;
            default:
                break;
        }
    }

    //点击展示游客登陆时展示的对话款
    private void showGuestGradeDialog() {
        TouristChooseDialog touristChooseDialog = (TouristChooseDialog) DialogUtil.getGuseDialog(this);
        touristChooseDialog.show();
    }

    /**
     * 显示需要登陆的对话框
     */
    private void showLoginTipsDialog() {
        DialogUtil.getInstance().getLoginTip(this, new GoToLoginDialogListener() {
            @Override
            public void onLogin() {
                checkLogin();
            }
        });
    }


    /**
     * 在登陆之前需要判断是否选择过学校，如果没有选择学校的话。是需要选择学校的
     */
    public void checkLogin() {
        CheckedSchool checkedSchool = CheckIdSchoolDao.getInstance().getCheckedSchool();
        //说明是之前选择过的学校的，不然的话。就需要重新选择学校
        if (checkedSchool != null) {
            showLoginDialog();
        } else {
            showChooseSchoolDialog();
        }
    }

    /**
     * 需要打开登陆对话框
     */
    public void showLoginDialog() {
        DialogUtil.getInstance().getToLogin(this, new OnLoginDialogListenr() {

            @Override
            public void onBeginRequest() {
                showLoading();
            }

            @Override
            public void onChooseSchool() {
                showChooseSchoolDialog();
            }

            @Override
            public void onLosePassword() {
                showLosePasswordDialog();
            }

            @Override
            public void onLoginSuccess(Dialog loginDialog) {
                hideLoading();
                mPresenter.getUserInfo();
                loginDialog.dismiss();
            }

            @Override
            public void onLoginError() {
                hideLoading();
                ToastUtil.show(BaseApplication.getContext(), "当前登录失败");
            }
        });
    }

    /**
     * 需要打开选择学校的对话框
     */
    public void showChooseSchoolDialog() {
        DialogUtil.getInstance().getToChooseSchoolDialog(this, new OnChooseSchoolDialogListener() {
            @Override
            public void onLoginDialogShow() {
                showLoginDialog();
            }

            @Override
            public void onNotFindSchool() {
                notFindSchool();
            }
        });
    }

    /**
     * 没有学校的对话框
     */
    public void notFindSchool() {
        DialogUtil.getInstance().getToNotFindSchool(this);
    }

    /**
     * 打开忘记密码的对框框
     */
    public void showLosePasswordDialog() {
        DialogUtil.getInstance().getToLosePassWordTips(this);
    }


    /**
     * 非会员的时候弹出登陆提示
     */
    private void showMemberPromptDialog() {
        DialogUtil.getInstance().getToMemberTip(this, new GetToMemberTipDialogListner() {
            @Override
            public void onGotologin() {
                showLoginDialog();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showClassifyDialog() {
        Dialog classifyDialog = com.wxjz.tenms_pad.util.DialogUtil.getClassifyDialog(this, currentSelect);
        classifyDialog.show();
    }


    @Override
    protected void onDestroy() {
        DialogUtil.getInstance().release();
        super.onDestroy();
    }

    /**
     * 修改学段
     *
     * @param classifyClickEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClassifyClickEvent(ClassifyClickEvent classifyClickEvent) {
        int clickButton = classifyClickEvent.getClickButton();
        tv_classify.setText(clickButton == 1 ? "小学" : "初中");
        ClassifyDao.getInstence().updateCheckId(clickButton);
        if (currentSelect != clickButton) {
            this.currentSelect = clickButton;
            refreshPage();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateUserInfo(UpdateUserInfoEvent event) {
        if (event.isUpdate()) {
            mPresenter.getUserInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginSuccessEvent event) {
        mPresenter.getUserInfo();
        initData();
    }

    /**
     * 游客模式下选择了学习阶段年级的对话框
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGusetChooseGradleDialog(GuestChooseGradeEvent event) {
        int leveId = event.getLeveld();
        String gradeName = event.getGradeName();
        tv_classify.setText(gradeName);
        this.currentSelect = leveId;
        mPresenter.getTopTabs(currentSelect);
    }

    /**
     * 退出登录后返回首页并刷新数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExit(ExitEvent event) {
        refreshPage();
    }

    /**
     * 刷新页面
     */
    private void refreshPage() {
        userInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        if (userInfo == null) {
            //如果用户信息为空了需要重新选择默认年级
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showGuestGradeDialog();
                }
            }, 500);
        } else {
            currentSelect = CheckGradeDao.getInstance().queryleveId();
            String gradename = CheckGradeDao.getInstance().queryGradeName();
            if (!TextUtils.isEmpty(gradename)) {
                tv_classify.setText(gradename);
            }
            mPresenter.getTopTabs(currentSelect);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                finish();
            } else {
                toast("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
