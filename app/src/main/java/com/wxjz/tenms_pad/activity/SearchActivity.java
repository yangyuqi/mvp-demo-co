package com.wxjz.tenms_pad.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.wxjz.module_base.base.BaseFragment;
import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.bean.CourseItemPage;
import com.wxjz.module_base.bean.RecommendBean;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.HistoryBean;
import com.wxjz.module_base.db.bean.SearchTabContentBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.HistoryDBDao;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.module_base.widgt.FlowLayout;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.adapter.RecommendInSearchCourseAdapter;
import com.wxjz.tenms_pad.adapter.SearchTabAdapter;
import com.wxjz.tenms_pad.fragment.SearchCommonFragment;
import com.wxjz.tenms_pad.fragment.mine.SearchAllFragment;
import com.wxjz.tenms_pad.mvp.contract.SearchContract;
import com.wxjz.tenms_pad.mvp.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements SearchContract.View, View.OnClickListener {

    private EditText edSearch;
    //private RecyclerView rvHistroy;
    private RecyclerView rvAboutCourse;
    private SlidingTabLayout slTablayout;
    private ViewPager viewPager;
    private LinearLayout llHistory;
    private LinearLayout llSearch;
    private RelativeLayout rlEmpty;
    private int levelId = -1;
    private FlowLayout flowLayout;
    private String gradeId = null;
    private TextView tvAboutVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear_history:
                boolean history = HistoryDBDao.getInstence().clearSearchHistory();
                if (!history) {
                    toast(R.string.delete_error_msg);
                } else {
                    onSearchHistory(new ArrayList<HistoryBean>());
                }
                break;

            case R.id.tv_cancel:
                resumeSearch();
                break;
            case R.id.ivDeleteText:
                resumeSearch();
                break;

            case R.id.iv_back:
                finish();
                break;
        }

    }

    private void resumeSearch() {
        changeVisible(true, null);
        setSearchEmptyVisible(false);
        edSearch.setText("");
        edSearch.setHint(getResources().getString(R.string.search_hint));
    }

    @Override
    protected void initView() {
        super.initView();
        ImageView ivDeleteText = findViewById(R.id.ivDeleteText);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        ImageView ivBack = findViewById(R.id.iv_back);
        tvAboutVideo = findViewById(R.id.tv_about_video);
        llHistory = findViewById(R.id.llHistory);
        llSearch = findViewById(R.id.llSearch);
        rlEmpty = findViewById(R.id.rlEmpty);
        ImageView ivClearHistory = findViewById(R.id.iv_clear_history);
        slTablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.view_pager);
        //  rvHistroy = findViewById(R.id.rv_search_history);
        flowLayout = findViewById(R.id.flowLayout);
        rvAboutCourse = findViewById(R.id.rv_about_course);
        edSearch = findViewById(R.id.ed_search);
        ivClearHistory.setOnClickListener(this);
        ivDeleteText.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        initListener();
    }

    private void initListener() {
        InputFilter inputFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    //                    Toast.makeText(MainActivity.this,"不支持输入表情", 0).show();
                    toast("不支持输入表情");
                    return "";
                }
                return null;
            }
        };
        edSearch.setFilters(new InputFilter[]{inputFilter});
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String edittextContent = getEdittextContent();
                    if (!TextUtils.isEmpty(edittextContent)) {
                        HistoryDBDao.getInstence().addSearchHistory(edittextContent);
                        changeVisible(false, null);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private String getEdittextContent() {
        return edSearch.getText().toString().trim();
    }

    @Override
    protected void initData() {
        super.initData();
        /**
         * 当前学段
         */
        gradeId = CheckGradeDao.getInstance().queryGradeId();
        levelId = CheckGradeDao.getInstance().queryleveId();
        /**
         * 获取搜索历史数据
         */
        mPresenter.getSearchHistory();
        /**
         * 获取相关课程数据
         */
        mPresenter.getRecommendCourse(levelId, gradeId, 1);

    }

    @Override
    public void onSearchHistory(final List<HistoryBean> historyDatas) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }
        for (final HistoryBean historyBean : historyDatas) {
            TextView textView = new TextView(SearchActivity.this);
            textView.setMaxEms(35);
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setBackground(getResources().getDrawable(R.drawable.search_history_item_bac));
            textView.setTextColor(getResources().getColor(R.color.dark_gray));
            textView.setText(historyBean.getHistory());
            textView.setLayoutParams(layoutParams);
            flowLayout.addView(textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String history = historyBean.getHistory();
                    HistoryDBDao.getInstence().addSearchHistory(history);
                    changeVisible(false, history);
                    //  mPresenter.queryAllSearchData(history, gradeId, levelId);
                    edSearch.setText(history);
                }
            });
        }

    }

    @Override
    public void onRecommendCourse(RecommendBean recommendBean) {
        final List<RecommendBean.ListBean> list = recommendBean.getList();
        if (list.size() == 0) {
            return;
        }
        if (!TextUtils.isEmpty(recommendBean.getTitle())) {
            tvAboutVideo.setText(recommendBean.getTitle());
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvAboutCourse.setLayoutManager(manager);
        RecommendInSearchCourseAdapter courseAdapter = new RecommendInSearchCourseAdapter(R.layout.layout_about_course_in_course_item, list);
        rvAboutCourse.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // mPresenter.addCourseClickCount(list.get(position).getId());
                RecommendBean.ListBean listBean = list.get(position);
                JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, listBean.getId(), listBean.getChapterId(), listBean.getSectionId(), gradeId);
            }
        });
    }


    private void onSearchTab(List<SearchTabContentBean.TableBean> tabBeans, ArrayList<SearchTabContentBean.AllBean> allBeans) {
        BasisConstants.SEARCH_LIST = allBeans;
        ArrayList<CourseItemPage> courseItemPages = new ArrayList<>();
        ArrayList<BaseFragment> fragmentList = new ArrayList<>();

        CourseItemPage courseItemPageAll = new CourseItemPage();
        SearchAllFragment instance = SearchAllFragment.getInstance();
       // courseItemPageAll.setFragment(instance);
        fragmentList.add(instance);
        courseItemPageAll.setId(0);
        courseItemPageAll.setTitle(getResources().getString(R.string.all));
        courseItemPages.add(courseItemPageAll);
        for (SearchTabContentBean.TableBean searchTabBean : tabBeans) {
            CourseItemPage courseItemPage = new CourseItemPage();
            courseItemPage.setTitle(searchTabBean.getSubjectName()+ "( " + searchTabBean.getVideoCount() + " )");
            courseItemPage.setId(searchTabBean.getId());
           // courseItemPage.setFragment(SearchCommonFragment.getInstance(searchTabBean.getId(), getEdittextContent()));
            fragmentList.add(SearchCommonFragment.getInstance(searchTabBean.getId(), getEdittextContent()));
            courseItemPages.add(courseItemPage);
        }

        SearchTabAdapter searchTabAdapter = new SearchTabAdapter(getSupportFragmentManager());
        searchTabAdapter.setPages(courseItemPages,fragmentList);
        viewPager.setAdapter(searchTabAdapter);
        //  searchTabAdapter.notifyDataSetChanged();
        slTablayout.setViewPager(viewPager);
        slTablayout.setCurrentTab(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BasisConstants.SEARCH_LIST = null;
    }

    @Override
    public void onAllSearchData(SearchTabContentBean searchTabContentBeans) {

        if (searchTabContentBeans.getAll().size() == 0) {
            setSearchEmptyVisible(true);
        } else {
            /**
             * 获取搜索tab
             */
            List<SearchTabContentBean.TableBean> table = searchTabContentBeans.getTable();
            ArrayList<SearchTabContentBean.AllBean> all = (ArrayList<SearchTabContentBean.AllBean>) searchTabContentBeans.getAll();
            onSearchTab(table, all);
            // mPresenter.getSearchTab(levelId);
        }
    }

    private void changeVisible(boolean historyVisible, String history) {
        if (historyVisible) {
            mPresenter.getSearchHistory();
        } else {
            if (TextUtils.isEmpty(history)) {
                /**
                 * 先查询所有的内容，如果没有数据显示空白页，有的话再查tab
                 */
                mPresenter.queryAllSearchData(getEdittextContent(), gradeId, levelId);
            } else {
                mPresenter.queryAllSearchData(history, gradeId, levelId);
            }


        }
        llSearch.setVisibility(historyVisible ? View.GONE : View.VISIBLE);
        llHistory.setVisibility(historyVisible ? View.VISIBLE : View.GONE);
    }

    private void setSearchEmptyVisible(boolean visible) {
        rlEmpty.setVisibility(visible ? View.VISIBLE : View.GONE);
        llSearch.setVisibility(View.GONE);
        llHistory.setVisibility(visible ? View.GONE : View.VISIBLE);
    }
}
