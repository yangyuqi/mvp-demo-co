package com.wxjz.tenms_pad.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_aliyun.aliyun.utils.ScreenUtils;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.CourseListItemBean;
import com.wxjz.module_base.bean.PlayAuthBean;
import com.wxjz.module_base.bean.SelectVideoBean;

import com.wxjz.module_base.db.bean.SubjectChapterBean;
import com.wxjz.module_base.db.bean.SubjectHomeBean;
import com.wxjz.module_base.db.bean.SubjectSectionBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.widgt.PopupWindowWithMask;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.CourseDetailActivity;
import com.wxjz.tenms_pad.adapter.AllCourseAdapter;
import com.wxjz.tenms_pad.adapter.ChapterVideoListAdapter;
import com.wxjz.tenms_pad.adapter.PickCourseLeftAdapter;
import com.wxjz.tenms_pad.adapter.PickCourseRightAdapter;
import com.wxjz.tenms_pad.adapter.SectionVideoListAdapter;
import com.wxjz.tenms_pad.adapter.SelectVideoAdapter;
import com.wxjz.tenms_pad.adapter.SubjectHomeAdapter;
import com.wxjz.tenms_pad.mvp.contract.CourseCommonContract;
import com.wxjz.tenms_pad.mvp.presenter.CourseCommonPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by a on 2019/9/5.
 * 单科
 */

@SuppressLint("ValidFragment")
public class CourseCommonFragment extends BaseMvpFragment<CourseCommonPresenter> implements CourseCommonContract.View, View.OnClickListener {
    private int subId;
    private TextView tvCourseCount;
    private ImageView ivShaixuan;
    private RecyclerView rvAllCourse;
    private RelativeLayout rlTop;
    private List<CourseListItemBean> mCourseList;
    private int levelId;
    private RelativeLayout rlEmpty;
    private LinearLayout llContent;
    private RelativeLayout rlEmptyShaiXuan;
    /**
     * 默认加载第一页
     */
    private int page = 1;
    private int courseId;
    private SelectVideoAdapter videoAdapter;
    private PopupWindowWithMask popupWindow;
    private String courseName;
    private String coverUrl;
    private SelectVideoBean.ListBean videoBean;

    private String gradeId;

    /**
     * 右侧空页面
     */
    private RelativeLayout rvRightEmpty;
    /**
     * 左侧二级收起的小菜单图片的样式
     */
    private ImageView ivCloseStatus;
    /**
     * 左侧二级展开的时候小图片的样式
     */
    private ImageView ivOpenStatus;

    /**
     * 侧边状态栏展开的状态
     */
    private LinearLayout llcolumnLayoutOut;
    /**
     * 侧边状态栏收起的状态
     */
    private LinearLayout llcolumnLinearLayoutClose;

    private RecyclerView ryVideoSelectList;

    private List<SubjectHomeBean.NavBean> navBeans = new ArrayList<>();

    private List<SubjectSectionBean> rightlistsectionBeans = new ArrayList<>();

    private List<SubjectSectionBean> sectionBeans = new ArrayList<>();

    private List<SubjectChapterBean> ChapterBeans = new ArrayList<>();
    /**
     * 点击章的时候获取的视频列表适配器
     */
    private ChapterVideoListAdapter chapterVideoListAdapter;

    /**
     * 点击节的时候获取节的视频列表
     */
    private SectionVideoListAdapter sectionVideoListAdapter;

    /**
     * 判断左侧列表是否是展示状态
     */
    private boolean isllcolumnshow = false;
    /**
     * 判断是否点击的章
     */
    private boolean Chapter = false;
    /**
     * 章在接口数据中的位置
     */
    private int ChapterIndex = -1;
    /**
     * 节在接口数据中的位置
     */
    private int SectionIndex = -1;

    //是否点击了章或者节
    private boolean isSelect;


    public static final int REQUEST_CODE = 1245;

    public int AdapterType;

    private HashMap<Integer, SelectLoaction> selectLoactionHashMap;


    public CourseCommonFragment() {
    }


    @Override
    protected CourseCommonPresenter createPresenter() {
        return new CourseCommonPresenter(this);
    }

    @Override
    protected void initView(View view) {
        tvCourseCount = view.findViewById(R.id.tv_course_count);

        rvAllCourse = view.findViewById(R.id.rv_all_course);
        rlEmpty = view.findViewById(R.id.rlEmpty);
        rlEmptyShaiXuan = view.findViewById(R.id.rlEmptyShaiXuan);
        llContent = view.findViewById(R.id.llContent);
        llcolumnLayoutOut = view.findViewById(R.id.ll_column_out);
        llcolumnLinearLayoutClose = view.findViewById(R.id.ll_column_close);

        ryVideoSelectList = view.findViewById(R.id.ry_video_select_list);
        rvRightEmpty = view.findViewById(R.id.rl_right_empty);
        ivCloseStatus = view.findViewById(R.id.iv_column_close_status);
        ivOpenStatus = view.findViewById(R.id.iv_column_out_status);
    }

    @Override
    protected void initData() {
        selectLoactionHashMap = new HashMap<>();
        subId = getArguments().getInt("id");
        levelId = CheckGradeDao.getInstance().queryleveId();
        gradeId = CheckGradeDao.getInstance().queryGradeId();
        /**
         * 获取单科下的所有学科
         */
        //  mPresenter.getAllCourseList(subId, levelId);
        mPresenter.getSubjectHome(levelId, subId, gradeId);
        llcolumnLayoutOut.setVisibility(View.GONE);
        llcolumnLinearLayoutClose.setVisibility(View.VISIBLE);
        llcolumnLayoutOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llcolumnLayoutOut.setVisibility(View.GONE);
                llcolumnLinearLayoutClose.setVisibility(View.VISIBLE);
//                if (isSelect) {
//                    ivCloseStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.column_show));
//                } else {
//                    ivCloseStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.column));
//                }
                onStatusChange();
            }
        });

        llcolumnLinearLayoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llcolumnLinearLayoutClose.setVisibility(View.GONE);
                llcolumnLayoutOut.setVisibility(View.VISIBLE);
                onStatusChange();
            }
        });


    }

    /**
     * 左侧列表是否是展开状态
     */
    private void onStatusChange() {
        if (llcolumnLayoutOut.isShown()) {
            isllcolumnshow = true;
            if (!Chapter && ChapterIndex == -1 && SectionIndex == -1) {
                //如果是第一进入就会走这个方法，因为加载的是RightVideoList数据
                GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 3);
                rvAllCourse.setLayoutManager(layoutManager1);
            } else if (Chapter && SectionIndex == -1) {
                //点击了章
                chapterVideoListAdapter.setGradelayoutitem(3);
            } else if (!Chapter && SectionIndex != -1) {
                //点击了节
                GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 3);
                rvAllCourse.setLayoutManager(layoutManager1);
            }
        } else {
            isllcolumnshow = false;
            if (!Chapter && ChapterIndex == -1 && SectionIndex == -1) {
                //如果是第一进入就会走这个方法，因为加载的是RightVideoList数据
                GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 4);
                rvAllCourse.setLayoutManager(layoutManager1);
            } else if (Chapter && SectionIndex == -1) {
                //点击了章
                chapterVideoListAdapter.setGradelayoutitem(4);
            } else if (!Chapter && SectionIndex != -1) {
                //点击了节
                GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 4);
                rvAllCourse.setLayoutManager(layoutManager1);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_course_common_fragment;
    }

    public static CourseCommonFragment getInstance(int id) {
        CourseCommonFragment courseCommonFragment = new CourseCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        courseCommonFragment.setArguments(bundle);
        return courseCommonFragment;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ll_shaixuan:
//                showPickDialog();
//                break;
//        }
//    }

    /**
     * 显示删选框
     */
    private void showPickDialog() {
        //先切换筛选按钮
        ivShaixuan.setBackground(getResources().getDrawable(R.drawable.shaixuan_2));
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_pick_pop, null, false);
        popupWindow = new PopupWindowWithMask(mContext, view, ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dp_200));
        popupWindow.showAsDropDown(rlTop, 0, (int) getResources().getDimension(R.dimen.dp_7));

        initPopView(view);
    }

    private int leftCheckedPosition = 0;

    private void initPopView(View view) {

        RecyclerView rvLeft = view.findViewById(R.id.rvLeft);
        final RecyclerView rvRight = view.findViewById(R.id.rvRight);
        LinearLayoutManager managerLeft = new LinearLayoutManager(mContext);
        rvLeft.setLayoutManager(managerLeft);
        final PickCourseLeftAdapter courseLeftAdapter = new PickCourseLeftAdapter(R.layout.layout_pick_course_left, mCourseList);
        rvLeft.setAdapter(courseLeftAdapter);
        //默认选中第一个
        courseLeftAdapter.setSelectPosition(0);
        selectShowRightCourse(courseLeftAdapter, rvRight, 0);
        courseLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectShowRightCourse(courseLeftAdapter, rvRight, position);
                leftCheckedPosition = position;
            }
        });
        TextView tvAll = view.findViewById(R.id.tv_all);
        TextView tv_course_all = view.findViewById(R.id.tv_course_all);
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //恢复成初始状态
                page = 1;
                mPresenter.getAllCourseList(subId, levelId);
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    rvAllCourse.setBackground(null);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rvAllCourse.getLayoutParams();
                    layoutParams.leftMargin = 0;
                    layoutParams.rightMargin = 0;
                    rvAllCourse.setLayoutParams(layoutParams);
                }
                showEmptyAfterShaiXuan(false);

            }
        });
        tv_course_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CourseListItemBean> singleCourse = new ArrayList<>();
                singleCourse.add(mCourseList.get(leftCheckedPosition));
                AllCourseAdapter courseAdapter = new AllCourseAdapter(R.layout.layout_shaixuan_course_item, singleCourse, CourseCommonFragment.this);
                rvAllCourse.setAdapter(courseAdapter);
                tvCourseCount.setText(Html.fromHtml("筛选出" + "<font color='#4E96FB'>" + singleCourse.size() + "</font>" + "个课程"));
                popupWindow.dismiss();
                showEmptyAfterShaiXuan(false);

            }
        });
    }

    private void selectShowRightCourse(PickCourseLeftAdapter leftAdapter, RecyclerView rvRight, final int positionLeft) {

        leftAdapter.setSelectPosition(positionLeft);
        LinearLayoutManager managerRight = new LinearLayoutManager(mContext);
        rvRight.setLayoutManager(managerRight);
        final PickCourseRightAdapter courseRightAdapter = new PickCourseRightAdapter(R.layout.layout_pick_course_right, mCourseList.get(positionLeft).getCourseList());
        rvRight.setAdapter(courseRightAdapter);
        //默认选中第一个
        // courseRightAdapter.setSelectPosition(0);
        courseRightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                popupWindow.dismiss();
                courseRightAdapter.setSelectPosition(position);
                /**
                 * 获取选中学科下的视频
                 */
                CourseListItemBean.CourseListBean courseListBean = mCourseList.get(positionLeft).getCourseList().get(position);
                courseId = courseListBean.getId();
                courseName = courseListBean.getCourseName();
                coverUrl = courseListBean.getCoverUrl();
                mPresenter.getSelectVideoList(courseId, page, 20);
            }
        });

    }

    @Override
    public void onAllCourseList(List<CourseListItemBean> courseListItemBeans) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvAllCourse.setLayoutManager(layoutManager);
        mCourseList = new ArrayList<>();
        int courseCount = 0;
        for (CourseListItemBean bean : courseListItemBeans) {
            int size = bean.getCourseList().size();
            courseCount += size;
            mCourseList.add(bean);

        }
        if (mCourseList == null || mCourseList.size() == 0) {
            showEmpty(true);
            return;
        } else {
            showEmpty(false);
        }

        tvCourseCount.setText(Html.fromHtml("共" + "<font color='#4E96FB'>" + courseCount + "</font>" + "个课程"));

        AllCourseAdapter courseAdapter = new AllCourseAdapter(R.layout.layout_shaixuan_course_item, mCourseList, CourseCommonFragment.this);
        rvAllCourse.setAdapter(courseAdapter);

    }

    /**
     * 单科内容的显示和隐藏
     *
     * @param showEmpty
     */
    private void showEmpty(boolean showEmpty) {
        rlEmpty.setVisibility(showEmpty ? View.VISIBLE : View.GONE);
        llContent.setVisibility(showEmpty ? View.GONE : View.VISIBLE);
    }


    private void showRightEmpty(boolean showEmpty) {
        rvAllCourse.setVisibility(showEmpty ? View.GONE : View.VISIBLE);
        rvRightEmpty.setVisibility(showEmpty ? View.VISIBLE : View.GONE);
    }

    /**
     * 增加点击量
     *
     * @param courseId
     */
    public void addCourseClickCount(int courseId) {
        mPresenter.addCourseClickCount(courseId);
    }

    /**
     * 筛选完后内容的显示和隐藏
     *
     * @param showEmpty
     */
    private void showEmptyAfterShaiXuan(boolean showEmpty) {
        rlEmptyShaiXuan.setVisibility(showEmpty ? View.VISIBLE : View.GONE);
        rvAllCourse.setVisibility(showEmpty ? View.GONE : View.VISIBLE);
    }

    private int currentVideoPosition;

    /**
     * 筛选出的视频
     *
     * @param selectVideoBeans
     */
    @Override
    public void onSelectVideoList(final SelectVideoBean selectVideoBeans) {
        page = 1;
        /**
         * 设置recycleview margin
         */
        tvCourseCount.setText(Html.fromHtml("筛选出" + "<font color='#4E96FB'>" + selectVideoBeans.getTotal() + "</font>" + "个视频"));
        if (selectVideoBeans.getList() == null || selectVideoBeans.getList().size() == 0) {
            showEmptyAfterShaiXuan(true);
            return;
        } else {

            showEmptyAfterShaiXuan(false);
            rvAllCourse.setBackground(getResources().getDrawable(R.drawable.normal_item_bac));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rvAllCourse.getLayoutParams();
            layoutParams.leftMargin = ScreenUtils.dip2px(mContext, 8);
            layoutParams.rightMargin = ScreenUtils.dip2px(mContext, 8);
            rvAllCourse.setLayoutParams(layoutParams);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 5);
        rvAllCourse.setLayoutManager(layoutManager);
        videoAdapter = new SelectVideoAdapter(R.layout.layout_select_video_item, selectVideoBeans.getList());
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentVideoPosition = position;
                videoBean = selectVideoBeans.getList().get(position);
                getVideoPlayAuth(videoBean.getVideoId(), videoBean.getId());
            }
        });
        rvAllCourse.setAdapter(videoAdapter);
        videoAdapter.setEnableLoadMore(true);
        videoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMoreVideoList(courseId, ++page, 10);
            }
        }, rvAllCourse);

    }

    /**
     * 先获取凭证再播放
     *
     * @param videoId
     * @param id
     */
    private void getVideoPlayAuth(String videoId, int id) {
        if (TextUtils.isEmpty(videoId)) {
            toast("无法播放该视频");
            return;
        }
        mPresenter.getPlayAuth(videoId, id);
    }

    @Override
    public void onLoadMoreVideoList(SelectVideoBean selectVideoBeans) {
        if (videoAdapter != null) {
            videoAdapter.addData(selectVideoBeans.getList());
            videoAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreComplete() {
        if (videoAdapter != null) {
            videoAdapter.loadMoreEnd();
        }

    }

    @Override
    public void onPlayAuth(PlayAuthBean playAuthBean, String videoId, int id) {
        String playAuth = playAuthBean.getPlayAuth();
        if (TextUtils.isEmpty(playAuth)) {
            toast("无法播放该视频");
            return;
        }
        if (videoBean != null) {
            videoBean.setClickCount(videoBean.getClickCount() + 1);
            videoAdapter.notifyItemChanged(currentVideoPosition);
        }
        mPresenter.addVideoClickCount(id, courseId);

    }

    /**
     * 第一次加载单科的视频
     *
     * @param subjectHomeBeans
     */
    @Override
    public void onSubjectHome(SubjectHomeBean subjectHomeBeans) {
        if (subjectHomeBeans != null && subjectHomeBeans.getNavList().size() > 0) {
            showRightEmpty(false);
            //回调的时候不为空才走这个方法
            navBeans = subjectHomeBeans.getNavList();
            rightlistsectionBeans = subjectHomeBeans.getRightVideoList();

            for (int i = 0; i < navBeans.size(); i++) {
                //默认所有的二级列表不展示，没有选中的位置
                navBeans.get(i).setExpand(false);
                navBeans.get(i).setChildSelect(-1);
                navBeans.get(i).setGroupSelect(-1);
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            ryVideoSelectList.setLayoutManager(layoutManager);
            final SubjectHomeAdapter subjectHomeAdapter = new SubjectHomeAdapter(getContext(), navBeans);
            ryVideoSelectList.setAdapter(subjectHomeAdapter);
            subjectHomeAdapter.setOnItemClickListener(new SubjectHomeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final int position) {
                    //如果是展开状态，那么就设置称收起状态，
                    //如果是收起状态，那么就设置成展开状态
                    //点击一级菜单时候，因为要做到互斥，所有的二级选择的position位置需要设置成为-1
                    navBeans.get(position).setExpand(navBeans.get(position).isExpand() ? false : true);
                    for (int i = 0; i < navBeans.size(); i++) {
                        if (i != position) {
                            navBeans.get(i).setGroupSelect(-1);
                        } else {
                            navBeans.get(i).setGroupSelect(position);
                        }
                        navBeans.get(i).setChildSelect(-1);
                    }
                    subjectHomeAdapter.notifyDataSetChanged();
                    Chapter = true;
                    ChapterIndex = position;
                    SectionIndex = -1;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            moveToPosition(ryVideoSelectList, position);
                        }
                    }, 500);
                    int chapterId = new Double((Double) navBeans.get(position).getId()).intValue();
                    mPresenter.getChapterVideoList(chapterId);
                }
            });
            subjectHomeAdapter.setListSelcetListener(new SubjectHomeAdapter.OnSecondListSelcetListener() {
                @Override
                public void onSecondIndex(int firstIndex, int secondIndex) {
                    for (int i = 0; i < navBeans.size(); i++) {
                        if (i != firstIndex) {
                            navBeans.get(i).setChildSelect(-1);
                            navBeans.get(i).setGroupSelect(-1);
                        } else {
                            navBeans.get(i).setGroupSelect(-1);
                            navBeans.get(i).setChildSelect(secondIndex);
                        }
                    }
                    subjectHomeAdapter.notifyDataSetChanged();
                    Chapter = false;
                    ChapterIndex = firstIndex;
                    SectionIndex = secondIndex;
                    int SectionId = new Double((Double) navBeans.get(firstIndex).getSectionList().get(secondIndex).getId()).intValue();
                    mPresenter.getSectionIdVideoList(SectionId);
                }
            });
            if (rightlistsectionBeans != null && rightlistsectionBeans.size() > 0) {
                Chapter = false;
                ChapterIndex = -1;
                SectionIndex = -1;
                sectionVideoListAdapter = new SectionVideoListAdapter(R.layout.layout_subject_item_video, rightlistsectionBeans);
                GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 4);
                rvAllCourse.setLayoutManager(layoutManager1);
                rvAllCourse.setAdapter(sectionVideoListAdapter);

                sectionVideoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        AdapterType = 1;
                        SelectLoaction selectLoaction = new SelectLoaction();
                        selectLoaction.setChapterIndex(0);
                        selectLoaction.setSectionIndex(position);
                        selectLoactionHashMap.put(1, selectLoaction);
                        int id = new Double((Double) rightlistsectionBeans.get(position).getId()).intValue();
                        int chapterId = new Double((Double) rightlistsectionBeans.get(position).getChapterId()).intValue();
                        Integer sectionId = 0;
                        if (rightlistsectionBeans.get(position).getSectionId() != null) {
                            sectionId = new Double((Double) rightlistsectionBeans.get(position).getSectionId()).intValue();
                        }
//                        isSelect = false;
                        startActivityForResult(id, chapterId, sectionId, gradeId);
//                        JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, id, chapterId, sectionId, gradeId);
                    }
                });
            }
        } else {
            showRightEmpty(true);
        }
    }

    /**
     * 获取章视频的ID
     *
     * @param subjectChapterBeans
     */
    @Override
    public void onChapterVideoList(final List<SubjectChapterBean> subjectChapterBeans) {
        ChapterBeans = subjectChapterBeans;
        if (ChapterBeans != null && ChapterBeans.size() > 0) {
            showRightEmpty(false);
            chapterVideoListAdapter = new ChapterVideoListAdapter(getContext(), ChapterBeans, 3);
            if (isllcolumnshow) {
                //列表是展开状态
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rvAllCourse.setLayoutManager(layoutManager);
                rvAllCourse.setAdapter(chapterVideoListAdapter);
            } else {
                //列表是展开状态
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rvAllCourse.setLayoutManager(layoutManager);
                rvAllCourse.setAdapter(chapterVideoListAdapter);
            }
            chapterVideoListAdapter.setChildItemClickListener(new ChapterVideoListAdapter.OnChildItemClickListener() {
                @Override
                public void OnChildItemOnClickListener(int layoutPosition, int position) {
                    AdapterType = 2;
                    SelectLoaction selectLoaction = new SelectLoaction();
                    selectLoaction.setChapterIndex(layoutPosition);
                    selectLoaction.setSectionIndex(position);
                    selectLoactionHashMap.put(2, selectLoaction);
                    double id = (double) ChapterBeans.get(layoutPosition).getVideoModelList().get(position).getId();
                    int Id = new Double(id).intValue();
                    int chapterId = new Double((double) ChapterBeans.get(layoutPosition).getVideoModelList().get(position).getChapterId()).intValue();
                    Integer sectionId = 0;
                    if (ChapterBeans.get(layoutPosition).getVideoModelList().get(position).getSectionId() != null) {
                        sectionId = new Double((double) ChapterBeans.get(layoutPosition).getVideoModelList().get(position).getSectionId()).intValue();
                    }
//                    isSelect = false;
                    startActivityForResult(Id, chapterId, sectionId, gradeId);
//                    JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, Id, chapterId, sectionId, gradeId);
                }
            });
        } else {
            showRightEmpty(true);
        }
    }

    /**
     * 获取节视频的ID
     *
     * @param subjectSectionBeans
     */
    @Override
    public void onSectionIdVideoList(final List<SubjectSectionBean> subjectSectionBeans) {
        sectionBeans = subjectSectionBeans;
        if (sectionBeans != null && sectionBeans.size() > 0) {
            showRightEmpty(false);
            sectionVideoListAdapter = new SectionVideoListAdapter(R.layout.layout_subject_item_video, sectionBeans);
            if (isllcolumnshow) {
                GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 3);
                rvAllCourse.setLayoutManager(layoutManager1);
                rvAllCourse.setAdapter(sectionVideoListAdapter);
            } else {
                GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 4);
                rvAllCourse.setLayoutManager(layoutManager1);
                rvAllCourse.setAdapter(sectionVideoListAdapter);
            }

            sectionVideoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    AdapterType = 3;
                    SelectLoaction selectLoaction = new SelectLoaction();
                    selectLoaction.setChapterIndex(0);
                    selectLoaction.setSectionIndex(position);
                    selectLoactionHashMap.put(3, selectLoaction);
                    int id = new Double((Double) sectionBeans.get(position).getId()).intValue();
                    int chapterId = new Double((Double) sectionBeans.get(position).getChapterId()).intValue();
                    Integer sectionId = 0;
                    if (sectionBeans.get(position).getSectionId() != null) {
                        sectionId = new Double((Double) sectionBeans.get(position).getSectionId()).intValue();
                    }
//                    isSelect = false;
                    startActivityForResult(id, chapterId, sectionId, gradeId);
//                    JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, id, chapterId, sectionId, gradeId);
                }
            });
        } else {
            showRightEmpty(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void moveToPosition(RecyclerView recyclerView, int position) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //因为只有LinearLayoutManager 才有获得可见位置的方法
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
            int lastItem = linearLayoutManager.findLastVisibleItemPosition();
            if (position < firstItem || position > lastItem) {
                recyclerView.smoothScrollToPosition(position);
            } else {
                int movePosition = position - firstItem;
                int top = recyclerView.getChildAt(movePosition).getTop();
                recyclerView.smoothScrollBy(0, top);
            }
        }
    }

    private void startActivityForResult(int id, int chapterId, int sectionId, String gradeId) {
        Intent intent = new Intent(mContext, CourseDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("chapterId", chapterId);
        intent.putExtra("sectionId", sectionId);
        intent.putExtra("gradeId", gradeId);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                int clickCount = data.getIntExtra("clickCount", -1);
                if (clickCount != -1) {
                    SelectLoaction selectLoaction = selectLoactionHashMap.get(AdapterType);
                    if (selectLoaction != null) {
                        //如果是-1就不更新
                        switch (AdapterType) {
                            case 1:
                                rightlistsectionBeans.get(selectLoaction.getSectionIndex()).setClickCount(new Double(clickCount));
                                sectionVideoListAdapter.notifyItemChanged(selectLoaction.getSectionIndex());
                                break;
                            case 2:
                                ChapterBeans.get(selectLoaction.getChapterIndex()).getVideoModelList().get(selectLoaction.getSectionIndex()).setClickCount(new Double(clickCount));
                                chapterVideoListAdapter.notifyItemChanged(selectLoaction.getChapterIndex());
                                break;
                            case 3:
                                sectionBeans.get(selectLoaction.getSectionIndex()).setClickCount(new Double(clickCount));
                                sectionVideoListAdapter.notifyItemChanged(selectLoaction.getSectionIndex());
                                break;
                            default:
                                break;
                        }
                    }
                }
//            isSelect = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (ivCloseStatus.isShown()) {
//                        ivCloseStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.column_show));
//                    } else if (ivOpenStatus.isShown()) {
//                        ivOpenStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.column_show));
//                    }
//                }
//            }, 500);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 记录选择的章节在数据中的位置
     */
    public class SelectLoaction {
        private int ChapterIndex;
        private int SectionIndex;

        public int getChapterIndex() {
            return ChapterIndex;
        }

        public void setChapterIndex(int chapterIndex) {
            ChapterIndex = chapterIndex;
        }

        public int getSectionIndex() {
            return SectionIndex;
        }

        public void setSectionIndex(int sectionIndex) {
            SectionIndex = sectionIndex;
        }
    }
}
