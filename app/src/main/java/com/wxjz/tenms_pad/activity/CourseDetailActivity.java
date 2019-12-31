package com.wxjz.tenms_pad.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.wxjz.module_aliyun.activity.LandscapeVideoActivity;
import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.VideoDetailBean;
import com.wxjz.module_base.db.bean.CheckedSchool;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.CheckIdSchoolDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.dialog.MemberPromptDialog;
import com.wxjz.module_base.event.LoginSuccessEvent;
import com.wxjz.module_base.event.NotLoginEvent;
import com.wxjz.module_base.listener.GetToMemberTipDialogListner;
import com.wxjz.module_base.listener.GoToLoginDialogListener;
import com.wxjz.module_base.listener.OnChooseSchoolDialogListener;
import com.wxjz.module_base.listener.OnLoginDialogListenr;
import com.wxjz.module_base.login.LoginDialog;
import com.wxjz.module_base.login.LoginTipsDialog;
import com.wxjz.module_base.util.AppManager;
import com.wxjz.module_base.util.DialogUtil;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.module_base.util.SPCacheUtil;
import com.wxjz.module_base.util.ToastUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.adapter.AboutCourseAdapter;
import com.wxjz.tenms_pad.adapter.CourseDetailPageAdapter;
import com.wxjz.tenms_pad.fragment.CatalogueFragment;
import com.wxjz.tenms_pad.fragment.DetailFragment;
import com.wxjz.tenms_pad.mvp.contract.CourseDetailContract;
import com.wxjz.tenms_pad.mvp.presenter.CourseDetailPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CourseDetailActivity extends BaseMvpActivity<CourseDetailPresenter> implements CourseDetailContract.View, View.OnClickListener {
    private RecyclerView rvAbout;
    private AboutCourseAdapter mCourseAdapter;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager view_pager;
    private ImageView ivCourseCover;
    private TextView tvCourseName;
    private TextView tvLearnNum;
    private LoginTipsDialog tipsDialog;
    private LoginDialog loginDialog;
    private MemberPromptDialog memberPromptDialog;

    /**
     * 课程详情
     */
    private VideoDetailBean courseDetailBean;
    private boolean isFree;
    private int id, chapterId, sectionId;
    private String gradeId;
    private TextView tvTitle;
    private TextView tvAboutTitle;
    private int levelId;
    //是否是点击了后边列表播放
    private boolean isClickListPlay;
    private VideoDetailBean.AboutListBean aboutListBean;
    private UserInfoBean currentUserInfo;
    private int clickCount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        rvAbout = findViewById(R.id.rv_about);
        tvAboutTitle = findViewById(R.id.tv_about_title);
        slidingTabLayout = findViewById(R.id.sl_tablayout);
        tvTitle = findViewById(R.id.tv_title_name);
        view_pager = findViewById(R.id.view_pager);
        ivCourseCover = findViewById(R.id.iv_course_cover);
        tvCourseName = findViewById(R.id.tv_course_name);
        tvLearnNum = findViewById(R.id.tv_learn_num);
        ImageView iv_play = findViewById(R.id.iv_play);
        iv_play.setOnClickListener(this);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null) {
            id = getIntent().getIntExtra("id", 0);
            chapterId = getIntent().getIntExtra("chapterId", 0);
            sectionId = getIntent().getIntExtra("sectionId", 0);
            gradeId = getIntent().getStringExtra("gradeId");

            mPresenter.getVideoDetail(id, chapterId == 0 ? null : chapterId, sectionId == 0 ? null : sectionId, gradeId, levelId);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        currentUserInfo = UserInfoDao.getInstence().getCurrentUserInfo();
        id = getIntent().getIntExtra("id", -1);
        levelId = CheckGradeDao.getInstance().queryleveId();
        chapterId = getIntent().getIntExtra("chapterId", 0);
        sectionId = getIntent().getIntExtra("sectionId", 0);
        gradeId = getIntent().getStringExtra("gradeId");

        mPresenter.getVideoDetail(id, chapterId == 0 ? null : chapterId, sectionId == 0 ? null : sectionId, gradeId, levelId);

    }

    /**
     * 初始化详情和目录
     *
     * @param courseDetailBean
     */
    private void createDetailAndCataloguePage(VideoDetailBean courseDetailBean) {
        CatalogueFragment catalogueFragment = new CatalogueFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putParcelableArrayList("kejian", (ArrayList<? extends Parcelable>) courseDetailBean.getDetail().getCoursewareList());
        catalogueFragment.setArguments(bundle2);

        DetailFragment detailFragment = DetailFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", courseDetailBean.getDetail());
        detailFragment.setArguments(bundle);
        List<CourseItemPage> mPages = new ArrayList<>();
        CourseItemPage detailPage = new CourseItemPage();
        CourseItemPage cataloguePage = new CourseItemPage();
        detailPage.setTitle("简介");
        detailPage.setId(1);
        detailPage.setFragment(detailFragment);
        cataloguePage.setTitle("课件");
        cataloguePage.setId(2);
        cataloguePage.setFragment(catalogueFragment);
        mPages.add(detailPage);
        mPages.add(cataloguePage);

        CourseDetailPageAdapter pageAdapter = new CourseDetailPageAdapter(getSupportFragmentManager());
        pageAdapter.setPages(mPages);
        view_pager.setAdapter(pageAdapter);
        pageAdapter.notifyDataSetChanged();
        slidingTabLayout.setViewPager(view_pager);
        slidingTabLayout.setCurrentTab(0);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_detail;
    }

    @Override
    protected CourseDetailPresenter createPresenter() {
        return new CourseDetailPresenter(this);
    }

    @Override
    public void onAboutCourse(final List<VideoDetailBean.AboutListBean> aboutList) {
        if (aboutList.size() == 0) {
            return;
        }
        rvAbout.setLayoutManager(new LinearLayoutManager(mContext));
        mCourseAdapter = new AboutCourseAdapter(R.layout.layout_about_course_in_course_item, aboutList, id);
        rvAbout.setAdapter(mCourseAdapter);
        mCourseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDetailBean.AboutListBean aboutListBean = aboutList.get(position);
                if (aboutListBean.isFree()) {
                    JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, aboutListBean.getId(), aboutListBean.getChapterId(), aboutListBean.getSectionId(), gradeId);

                }
                if (!aboutListBean.isFree() && currentUserInfo.getIsMember() == 1) {
                    JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, aboutListBean.getId(), aboutListBean.getChapterId(), aboutListBean.getSectionId(), gradeId);

                }
                if (!aboutListBean.isFree() && currentUserInfo.getIsMember() != 1) {
                    showMemberPromptDialog();
                }

            }
        });
    }

    @Override
    public void onUserInfo(com.wxjz.module_base.bean.UserInfoBean userInfoBean) {
        hideLoading();
        UserInfoDao.getInstence().saveUserInfo(userInfoBean);
        com.wxjz.module_base.bean.UserInfoBean.UserBean user = userInfoBean.getUser();
        int isMember = user.getIsMember();
        if (isMember == 1) {
            //会员 返回首页刷新数据
            EventBus.getDefault().post(new LoginSuccessEvent());
            AppManager.getAppManager().finishActivity();
        } else {
            //登录成功刷新主页
            initData();
            EventBus.getDefault().post(new LoginSuccessEvent());
        }
    }


    /**
     * 获取课程详情
     *
     * @param videoDetailBean1
     */
    @Override
    public void onVideoDetail(VideoDetailBean videoDetailBean1) {
        createDetailAndCataloguePage(videoDetailBean1);
        this.courseDetailBean = videoDetailBean1;
        VideoDetailBean.DetailBean detail = videoDetailBean1.getDetail();
        clickCount = detail.getClickCount();
        if (detail == null) {
            return;
        }
        isFree = detail.isFree();
        List<VideoDetailBean.AboutListBean> aboutList = videoDetailBean1.getAboutList();
        onAboutCourse(aboutList);
        tvTitle.setText(detail.getVideoTitle());
        try {
            tvAboutTitle.setText(videoDetailBean1.getTitle() + "  全部课程");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(this).load(detail.getCoverUrl()).error(R.drawable.banner1).into(ivCourseCover);
        tvCourseName.setText(detail.getVideoTitle());
        tvLearnNum.setText("共" + detail.getClickCount() + "人学过");
    }

    @Override
    public void onPlayAuth(PlayAuthBean playAuthBean) {
        //播放
        String playAuth = playAuthBean.getPlayAuth();
        if (TextUtils.isEmpty(playAuth)) {
            toast("获取播放凭证失败");
            return;
        }

        if (isClickListPlay) {
            JumpUtil.jump2VideoActivity(CourseDetailActivity.this, LandscapeVideoActivity.class, playAuth, aboutListBean.getVideoId(),
                    aboutListBean.getId(), aboutListBean.getSubId(), String.valueOf(aboutListBean.getChapterId()), String.valueOf(aboutListBean.getSectionId()), gradeId
                    , aboutListBean.getVideoDuration()

            );
        } else {
            VideoDetailBean.DetailBean detail = courseDetailBean.getDetail();
            JumpUtil.jump2VideoActivity(CourseDetailActivity.this, LandscapeVideoActivity.class, playAuth, detail.getVideoId(),
                    detail.getId(), detail.getSubId(), String.valueOf(detail.getChapterId()), String.valueOf(detail.getSectionId()), gradeId
                    , detail.getVideoDuration()

            );
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (clickCount != -1) {
                    Intent intent = new Intent();
                    intent.putExtra("clickCount", clickCount);
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
            case R.id.iv_play:
                if (isFree) {
                    //免费课程
                    isClickListPlay = false;
                    startPlay();
                } else {
                    if (currentUserInfo != null) {
                        if (currentUserInfo.getIsMember() == 1) {
                            //会员
                            startPlay();
                        } else {
                            showMemberPromptDialog();
                        }
                    } else {
                        showLoginTipsDialog();
                    }

                }
                break;
        }
    }



    //播放第一个视频
    private void startPlay() {

        //获取视频播放凭证
        if (courseDetailBean.getDetail() != null) {
            mPresenter.getPlayAuthByVid(courseDetailBean.getDetail().getVideoId());
        }


    }

    @Override
    protected boolean needEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotLoginEvent(NotLoginEvent event) {
        showLoginTipsDialog();
    }

    /**
     * 登录提示框
     */
    public void showLoginTipsDialog() {
        DialogUtil.getInstance().getLoginTip(this, new GoToLoginDialogListener() {
            @Override
            public void onLogin() {
                CheckLogin();
            }
        });
    }

    /**
     * 在登陆之前需要判断是否选择过学校，如果没有选择学校的话。是需要选择学校的
     */
    public void CheckLogin() {
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

    @Override
    protected void onDestroy() {
        DialogUtil.getInstance().release();
        super.onDestroy();
    }
}
