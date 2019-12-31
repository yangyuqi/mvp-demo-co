package com.wxjz.tenms_pad.fragment.mine;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.event.HaveNoteEvent;
import com.wxjz.module_base.event.NoteItemChekEvent;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.adapter.FragmentInMyNoteAdapter;
import com.wxjz.tenms_pad.adapter.HomePageCourseAdapter;
import com.wxjz.tenms_pad.mvp.contract.LearnRecordContract;
import com.wxjz.tenms_pad.mvp.contract.MyNoteContract;
import com.wxjz.tenms_pad.mvp.presenter.LearnRecordPresenter;
import com.wxjz.tenms_pad.mvp.presenter.MyNotePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 * 我的笔记
 */

public class MyNoteFragment extends BaseMvpFragment<MyNotePresenter> implements MyNoteContract.View {

    private SlidingTabLayout sl_tablayout;
    private ViewPager viewPager;
    private LinearLayout llChoose;
    private TextView tvManage;
    private List<CourseItemPage> mPageList;
    private TextView tvChooseAll;
    private TextView tvCancel;
    private TextView tvDelete;

    private RelativeLayout rlContent;

    public static MyNoteFragment getInstance() {
        return new MyNoteFragment();
    }

    @Override
    protected MyNotePresenter createPresenter() {
        return new MyNotePresenter(this);
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
        rlContent = view.findViewById(R.id.rl_content);
        setDeleteTextClickable(false);
        initListener();

    }

    private void initListener() {
        tvManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setManageButtonVisible(false);
                FragmentInMyNote fragmentInMyNote = (FragmentInMyNote) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                fragmentInMyNote.showCheckBox(true);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentInMyNote fragmentInMyNote = (FragmentInMyNote) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                fragmentInMyNote.showCheckBox(false);
                setManageButtonVisible(true);

            }
        });
        tvChooseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentInMyNote fragmentInMyNote = (FragmentInMyNote) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                fragmentInMyNote.chooseAll(true);
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentInMyNote fragmentInMyNote = (FragmentInMyNote) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
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
        int   levelId = CheckGradeDao.getInstance().queryleveId();
        mPresenter.getTopTabs(levelId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_note;
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
                FragmentInMyNote fragmentInMyNote = (FragmentInMyNote) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                fragmentInMyNote.showCheckBox(false);
                setManageButtonVisible(true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (tvManage.getVisibility() == View.GONE) {
                    FragmentInMyNote fragmentInMyNote = (FragmentInMyNote) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
                    fragmentInMyNote.showCheckBox(false);
                    setManageButtonVisible(true);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 切换右上角 管理按钮和选择按钮的显示和隐藏
     *
     * @param visible
     */
    public void setManageButtonVisible(boolean visible) {
        tvManage.setVisibility(visible ? View.VISIBLE : View.GONE);
        llChoose.setVisibility(visible ? View.GONE : View.VISIBLE);
    }


    /**
     * 是否有checkbox选中的通知
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNoteCheckEvent(NoteItemChekEvent event) {
        if (event.isCheck()) {
            FragmentInMyNote fragmentInMyNote = (FragmentInMyNote) mPageList.get(sl_tablayout.getCurrentTab()).getFragment();
            boolean hasRadioCheck = fragmentInMyNote.getRadioCheckStatus();
            setDeleteTextClickable(hasRadioCheck);
        }
    }

    /**
     * 全部页面是否有笔记，没有则显示空白页
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHaveNote(HaveNoteEvent event) {
//        if (!event.isHaveNote()) {
//            rlEmpty.setVisibility(View.VISIBLE);
//            rlContent.setVisibility(View.GONE);
//        } else {
//            rlEmpty.setVisibility(View.GONE);
//            rlContent.setVisibility(View.VISIBLE);
//        }
    }

    private void setDeleteTextClickable(boolean clickable) {
        tvDelete.setClickable(clickable);
        tvDelete.setTextColor(clickable ? getResources().getColor(R.color.black) : getResources().getColor(R.color.gray909399));
    }

    @Override
    public boolean needEventBus() {
        return true;
    }
}
