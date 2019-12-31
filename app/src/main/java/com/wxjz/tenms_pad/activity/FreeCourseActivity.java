package com.wxjz.tenms_pad.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.bean.FreeVideoListBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.module_base.db.bean.GuestChooseGrade;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.adapter.FreeCourseActivityAdapter;
import com.wxjz.tenms_pad.mvp.contract.FreeCourseContract;
import com.wxjz.tenms_pad.mvp.presenter.FreeCoursePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 免费课程
 */
public class FreeCourseActivity extends BaseMvpActivity<FreeCoursePresenter> implements FreeCourseContract.View {

    private RecyclerView rvFree;
    private int PAGE = 1;
    private int ROWS = 20;
    private List<FreeVideoListBean.ListBean> mFreeList = new ArrayList<>();
    private FreeCourseActivityAdapter mAdapter;
    private int levelId;
    private String gradeId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_free_course;

    }

    @Override
    protected FreeCoursePresenter createPresenter() {
        return new FreeCoursePresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ImageView ivBack = findViewById(R.id.iv_back);
        rvFree = findViewById(R.id.rv_free);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvFree.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new FreeCourseActivityAdapter(R.layout.layout_popular_course_unlearn_item, mFreeList);
        rvFree.setAdapter(mAdapter);
        mAdapter.setEnableLoadMore(true);
        mAdapter.disableLoadMoreIfNotFullPage(rvFree);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getMoreFreCourse(levelId, gradeId, ++PAGE, ROWS);
            }
        }, rvFree);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FreeVideoListBean.ListBean listBean = mFreeList.get(position);
                JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, listBean.getId(), listBean.getChapterId(), listBean.getSectionId(), gradeId);
                //JumpUtil.jump2CourseDetailActivity(FreeCourseActivity.this, CourseDetailActivity.class, mFreeList.get(position).getVideoTitle(), courseId, mFreeList.get(position).getSubId(), mFreeList.get(position).isFree());
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        GuestChooseGrade guestChooseGrade = CheckGradeDao.getInstance().getGuestChooseGrade();
        gradeId = guestChooseGrade.getGradeId();
        levelId = guestChooseGrade.getLevelid();
        mPresenter.getFreeCourse(levelId, gradeId, PAGE, ROWS);
    }

    @Override
    public void onFreeCourse(FreeVideoListBean freeCourse) {
        List<FreeVideoListBean.ListBean> list = freeCourse.getList();
        if (list.size() == 0) {

            return;
        }
        int startPosition = mFreeList.size() - 1;
        mFreeList.addAll(list);
        mAdapter.notifyItemRangeInserted(startPosition, list.size());


    }

    @Override
    public void loadMoreFreeCourse(FreeVideoListBean freeCourse) {
        if (freeCourse != null) {
            if (null != freeCourse.getList()) {
                List<FreeVideoListBean.ListBean> list = freeCourse.getList();
                if (list.size() == 0) {
                    mAdapter.loadMoreEnd();
                    return;
                }
                int startPosition = mFreeList.size() - 1;
                mFreeList.addAll(list);
                mAdapter.notifyItemRangeInserted(startPosition, list.size());
                mAdapter.loadMoreComplete();
            } else {
                mAdapter.loadMoreEnd();
            }
        }
    }
}
