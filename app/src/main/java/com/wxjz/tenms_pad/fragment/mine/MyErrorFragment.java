package com.wxjz.tenms_pad.fragment.mine;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.FilterErrorExerciseBean;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.event.ErrorItemChekEvent;
import com.wxjz.module_base.event.HaveErrorProblemEvent;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.adapter.FragmentInMyNoteAdapter;
import com.wxjz.tenms_pad.mvp.contract.MyErrorContract;
import com.wxjz.tenms_pad.mvp.presenter.MyErrorPresenter;
import com.wxjz.tenms_pad.popwindow.MyErrorPopWindow.MyErrorChapterAdapter;
import com.wxjz.tenms_pad.popwindow.MyErrorPopWindow.MyErrorGradeAdapter;
import com.wxjz.tenms_pad.popwindow.MyErrorPopWindow.MyErrorPopWindow;
import com.wxjz.tenms_pad.popwindow.MyErrorPopWindow.MyErrorSectionAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 我的错题
 */

public class MyErrorFragment extends BaseMvpFragment<MyErrorPresenter> implements MyErrorContract.View {

    private SlidingTabLayout sl_tablayout;
    private ViewPager viewPager;
    private LinearLayout llChoose;
    private LinearLayout llManger;
    private TextView tvManage;
    private List<CourseItemPage> mPageList;
    private TextView tvChooseAll;
    private TextView tvCancel;
    private TextView tvDelete;

    private List<FilterErrorExerciseBean> filterErrorExerciseBeans;
    private List<FilterErrorExerciseBean.chapterBean> filterErrorChapterBeans;
    private List<FilterErrorExerciseBean.sectionBean> filterErrorsectionBeans;

//    private RecyclerView gradeRecyclerView;
//    private RecyclerView chapterRecyclerView;
//    private RecyclerView sectionRecyclerView;
//    private MyErrorGradeAdapter gradeAdapter;
//    private MyErrorChapterAdapter chapterAdapter;
//    private MyErrorSectionAdapter sectionAdapter;

    public static MyErrorFragment getInstance() {
        return new MyErrorFragment();
    }

    @Override
    protected MyErrorPresenter createPresenter() {
        return new MyErrorPresenter(this);
    }

    @Override
    protected void initView(View view) {
        sl_tablayout = view.findViewById(R.id.sl_tablayout);
        viewPager = view.findViewById(R.id.view_pager);
        tvManage = view.findViewById(R.id.tv_manage);
        llChoose = view.findViewById(R.id.llChoose);
        tvChooseAll = view.findViewById(R.id.tv_choose_all);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvDelete = view.findViewById(R.id.tv_delete);
        llManger = view.findViewById(R.id.ll_manger_layout);
        setDeleteTextClickable(false);
        initListener();

    }

    private void setDeleteTextClickable(boolean clickable) {
        tvDelete.setClickable(clickable);
        tvDelete.setTextColor(clickable ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray909399));

    }

    private void initListener() {
        tvManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setManageButtonVisible(false);
                FragmentInMyError fragmentInMyNote = (FragmentInMyError) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                fragmentInMyNote.showCheckBox(true);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentInMyError fragmentInMyNote = (FragmentInMyError) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                fragmentInMyNote.showCheckBox(false);
                setManageButtonVisible(true);

            }
        });
        tvChooseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentInMyError fragmentInMyNote = (FragmentInMyError) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                fragmentInMyNote.chooseAll(true);
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentInMyError fragmentInMyNote = (FragmentInMyError) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                fragmentInMyNote.deleteCheckedItem();
                initData();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        int levelId = CheckGradeDao.getInstance().queryleveId();
        mPresenter.getTopTabs(levelId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_error;
    }

    @Override
    public void onTopTabData(List<CourseItemPage> itemPageList) {
        this.mPageList = itemPageList;
        FragmentInMyNoteAdapter fragmentInMyNoteAdapter = new FragmentInMyNoteAdapter(getChildFragmentManager());
        fragmentInMyNoteAdapter.setPages(itemPageList);
        viewPager.setAdapter(fragmentInMyNoteAdapter);
        fragmentInMyNoteAdapter.notifyDataSetChanged();
        sl_tablayout.setViewPager(viewPager);
        sl_tablayout.setCurrentTab(0);
        sl_tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                FragmentInMyError fragmentInMyError = (FragmentInMyError) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
//                //  fragmentInMyError.showCheckBox(false);
//                setManageButtonVisible(true);

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
//                if (tvManage.getVisibility() == View.GONE) {
//                    FragmentInMyError fragmentInMyError = (FragmentInMyError) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
//                    //fragmentInMyNote.showCheckBox(false);
//                    setManageButtonVisible(true);
//                }
            }

            @Override
            public void onPageSelected(int i) {
                setManageButtonVisible(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 筛选结果的回调
     *
     * @param filterErrorExerciseBean
     */
    @Override
    public void onFilte(List<FilterErrorExerciseBean> filterErrorExerciseBean) {

    }


    /**
     * 展示筛选还是隐藏
     *
     * @param filterErrorExerciseBean
     */
    private void showFilter(List<FilterErrorExerciseBean> filterErrorExerciseBean) {
//        if (filterErrorExerciseBean != null && filterErrorExerciseBean.size() > 0) {
//            //设置一级recyclerview
//            setgradeRecyclerView(filterErrorExerciseBean);
//        }
    }


//    /**
//     * 设置一级recyclerview
//     *
//     * @param filterErrorExerciseBeans
//     */
//    private void setgradeRecyclerView(final List<FilterErrorExerciseBean> filterErrorExerciseBeans) {
//        for (int i = 0; i < filterErrorExerciseBeans.size(); i++) {
//            filterErrorExerciseBeans.get(i).setSelect(false);
//        }
//
//        gradeAdapter = new MyErrorGradeAdapter(mContext, filterErrorExerciseBeans, new MyErrorGradeAdapter.ItemViewOnClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                //判断自身是否是选中状态
//                if (!filterErrorExerciseBeans.get(position).getSelect()) {
//                    for (int i = 0; i < filterErrorExerciseBeans.size(); i++) {
//                        if (i == position) {
//                            filterErrorExerciseBeans.get(i).setSelect(true);
//                        } else {
//                            filterErrorExerciseBeans.get(i).setSelect(false);
//                        }
//                    }
//                    gradeAdapter.notifyDataSetChanged();
//                    setChapterRecyclerView(position);
//                }
//            }
//        });
//        gradeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        gradeRecyclerView.setAdapter(gradeAdapter);
//        setChapterRecyclerView(0);
//
//    }
//
//    /**
//     * 设置二级的recyclerview
//     *
//     * @param fatherPosition 父级选项的选择位置
//     */
//    private void setChapterRecyclerView(final int fatherPosition) {
//        if (null != filterErrorExerciseBeans.get(fatherPosition)) {
//            filterErrorChapterBeans = filterErrorExerciseBeans.get(fatherPosition).getChapterId();
//            for (int i = 0; i < filterErrorChapterBeans.size(); i++) {
//                filterErrorChapterBeans.get(i).setSelect(false);
//            }
//            chapterAdapter = new MyErrorChapterAdapter(mContext, filterErrorChapterBeans, new MyErrorChapterAdapter.ItemViewOnClickListener() {
//                @Override
//                public void onItemClick(int position) {
//                    if (!filterErrorChapterBeans.get(position).isSelect()) {
//                        //判断父级是否是选中状态，//如果没有选中，就需要刷新
//                        if (!filterErrorExerciseBeans.get(fatherPosition).getSelect()) {
//                            filterErrorExerciseBeans.get(fatherPosition).setSelect(true);
//                            gradeAdapter.notifyDataSetChanged();
//                        }
//                        //自身不是选中状态，需要刷新
//                        for (int i = 0; i < filterErrorChapterBeans.size(); i++) {
//                            if (i == position) {
//                                filterErrorChapterBeans.get(i).setSelect(true);
//                            } else {
//                                filterErrorChapterBeans.get(i).setSelect(false);
//                            }
//                        }
//                        chapterAdapter.notifyDataSetChanged();
//                        setSectionRecyclerView(fatherPosition, position);
//                    }
//                }
//            });
//
//            chapterRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//            chapterRecyclerView.setAdapter(chapterAdapter);
//            setSectionRecyclerView(fatherPosition, 0);
//        }
//
//    }
//
//
//    /**
//     * 设置三级的recyclerview
//     *
//     * @param firstposition  一级选项选择的位置
//     * @param secondposition 二级选项选择的位置
//     */
//    private void setSectionRecyclerView(final int firstposition, final int secondposition) {
//        if (null != filterErrorExerciseBeans.get(firstposition).getChapterId().get(secondposition).getSectionId()) {
//            filterErrorsectionBeans = filterErrorExerciseBeans.get(firstposition).getChapterId().get(secondposition).getSectionId();
//            for (int i = 0; i < filterErrorsectionBeans.size(); i++) {
//                filterErrorsectionBeans.get(i).setSelect(false);
//            }
//            sectionAdapter = new MyErrorSectionAdapter(mContext, filterErrorsectionBeans, new MyErrorSectionAdapter.ItemViewOnClickListener() {
//                @Override
//                public void onItemClick(int position) {
//                    if (!filterErrorsectionBeans.get(position).isSelect()) {
//                        //判断一级列表是否是选择状态
//                        if (!filterErrorExerciseBeans.get(firstposition).getSelect()) {
//                            filterErrorExerciseBeans.get(firstposition).setSelect(true);
//                            gradeAdapter.notifyDataSetChanged();
//                        }
//                        //判断二级列表是否是选中状态
//                        if (!filterErrorChapterBeans.get(secondposition).isSelect()) {
//                            filterErrorChapterBeans.get(secondposition).setSelect(true);
//                            chapterAdapter.notifyDataSetChanged();
//                        }
//
//                        for (int i = 0; i < filterErrorsectionBeans.size(); i++) {
//                            if (i == position) {
//                                filterErrorsectionBeans.get(i).setSelect(true);
//                            } else {
//                                filterErrorsectionBeans.get(i).setSelect(false);
//                            }
//                            sectionAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            });
//
//            sectionRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//            sectionRecyclerView.setAdapter(sectionAdapter);
//        }
//    }


    /**
     * 切换右上角 管理按钮和选择按钮的显示和隐藏
     *
     * @param visible
     */
    public void setManageButtonVisible(boolean visible) {
        llManger.setVisibility(visible ? View.VISIBLE : View.GONE);
        llChoose.setVisibility(visible ? View.GONE : View.VISIBLE);
    }


    @Override
    public boolean needEventBus() {
        return true;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            //说明没有隐藏，这个时候我们根据状态去刷新界面

        }
    }

    /**
     * 是否有错题的监听
     *
     * @param errorProblemEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHaveErrorProblemEvent(HaveErrorProblemEvent errorProblemEvent) {
//        if (errorProblemEvent.isHaveError()) {
//            rlContent.setVisibility(View.VISIBLE);
//            rlEmpty.setVisibility(View.GONE);
//        } else {
//            rlContent.setVisibility(View.GONE);
//            rlEmpty.setVisibility(View.VISIBLE);
//        }
    }

    /**
     * 是否有checkbox选中的通知
     *
     * @param errorProblemEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorItemCheck(ErrorItemChekEvent errorProblemEvent) {
        if (errorProblemEvent.isCheck()) {
            FragmentInMyError fragmentInMyError = (FragmentInMyError) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
            boolean radioCheckStatus = fragmentInMyError.getRadioCheckStatus();
            setDeleteTextClickable(radioCheckStatus);
        }

    }

}
