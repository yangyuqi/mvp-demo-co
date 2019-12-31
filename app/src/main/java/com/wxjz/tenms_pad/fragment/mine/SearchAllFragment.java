package com.wxjz.tenms_pad.fragment.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_base.base.BaseFragment;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.SearchTabContentBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.CourseDetailActivity;
import com.wxjz.tenms_pad.adapter.SearchContentAdapter;
import com.wxjz.tenms_pad.mvp.contract.SearchCommonContract;
import com.wxjz.tenms_pad.mvp.presenter.SearchCommonPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/5.
 */

@SuppressLint("ValidFragment")
public class SearchAllFragment extends BaseFragment {
    private RecyclerView rvSearch;
    private RelativeLayout rlEmpty;
    private String gradeId;
   // private ArrayList<SearchTabContentBean.AllBean> allBeans = new ArrayList<>();

    public static SearchAllFragment getInstance() {
        SearchAllFragment fragment = new SearchAllFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("searchList", allBeans);
//        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initView(View view) {
        rvSearch = view.findViewById(R.id.rv_search);
        rlEmpty = view.findViewById(R.id.rlEmpty);
    }

    @Override
    protected void initData() {
       // ArrayList<SearchTabContentBean.AllBean> mAllSearchlist = getArguments().getParcelableArrayList("searchList");
        gradeId = CheckGradeDao.getInstance().getGuestChooseGrade().getGradeId();
        onSearchData(BasisConstants.SEARCH_LIST);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_search_common_fragment;
    }

    @Override
    public boolean needEventBus() {
        return false;
    }


    private void onSearchData(final List<SearchTabContentBean.AllBean> searchTabContent) {
        if (searchTabContent.size() > 0) {
            rlEmpty.setVisibility(View.GONE);
            rvSearch.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
            rvSearch.setLayoutManager(gridLayoutManager);
            SearchContentAdapter searchContentAdapter = new SearchContentAdapter();
            searchContentAdapter.addData(searchTabContent);
            rvSearch.setAdapter(searchContentAdapter);
            searchContentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    //     mPresenter.addCourseClickCount(searchTabContent.get(position).getId());
                    SearchTabContentBean.AllBean bean = searchTabContent.get(position);
                    JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class,
                            bean.getId(), bean.getChapterId(), bean.getSectionId(), gradeId);
                }
            });

        } else {
            rlEmpty.setVisibility(View.VISIBLE);
            rvSearch.setVisibility(View.GONE);
        }
    }


}
