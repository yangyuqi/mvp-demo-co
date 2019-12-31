package com.wxjz.tenms_pad.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.SearchBean;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.SearchTabContentBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.CourseDetailActivity;
import com.wxjz.tenms_pad.adapter.SearchContentAdapter;
import com.wxjz.tenms_pad.adapter.SearchCourseAdapter;
import com.wxjz.tenms_pad.mvp.contract.SearchCommonContract;
import com.wxjz.tenms_pad.mvp.presenter.SearchCommonPresenter;

import java.util.List;

/**
 * Created by a on 2019/9/5.
 */

public class SearchCommonFragment extends BaseMvpFragment<SearchCommonPresenter> implements SearchCommonContract.View {
    private int id;
    private RecyclerView rvSearch;
    private RelativeLayout rlEmpty;
    private String searchContent;
    private String gradeId;


    @Override
    protected SearchCommonPresenter createPresenter() {
        return new SearchCommonPresenter();
    }

    @Override
    protected void initView(View view) {
        rvSearch = view.findViewById(R.id.rv_search);
        rlEmpty = view.findViewById(R.id.rlEmpty);

    }

    @Override
    protected void initData() {
        id = getArguments().getInt("id");
        searchContent = getArguments().getString("content");
        int levelId = CheckGradeDao.getInstance().queryleveId();
        gradeId = CheckGradeDao.getInstance().getGuestChooseGrade().getGradeId();
        Log.e("===","id:"+id+"searchContent:"+BasisConstants.SEARCH_CONTENT+"levelId:"+levelId+"gradeId:"+gradeId);
        mPresenter.getSearchData(BasisConstants.SEARCH_CONTENT,levelId, this.id, gradeId);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_search_common_fragment;
    }

    public static SearchCommonFragment getInstance(int id, String content) {
        SearchCommonFragment searchCommonFragment = new SearchCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("content", content);
        searchCommonFragment.setArguments(bundle);
        BasisConstants.SEARCH_CONTENT = content;
        return searchCommonFragment;
    }

    @Override
    public void onSearchData(final List<SearchBean> searchTabContent) {
        if (searchTabContent.size() > 0) {
            rlEmpty.setVisibility(View.GONE);
            rvSearch.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
            rvSearch.setLayoutManager(gridLayoutManager);
            SearchCourseAdapter searchContentAdapter = new SearchCourseAdapter();
            searchContentAdapter.addData(searchTabContent);
            rvSearch.setAdapter(searchContentAdapter);
            searchContentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    SearchBean searchBean = searchTabContent.get(position);
                    JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, searchBean.getId()
                            , searchBean.getChapterId(), searchBean.getSectionId(), gradeId);
                }
            });

        } else {
            rlEmpty.setVisibility(View.VISIBLE);
            rvSearch.setVisibility(View.GONE);
        }
    }
}
