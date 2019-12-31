package com.wxjz.tenms_pad.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.BrannerBean;
import com.wxjz.module_base.bean.FreeVideoListBean;
import com.wxjz.module_base.bean.PopularMutiItem;
import com.wxjz.module_base.bean.RecommendBean;
import com.wxjz.module_base.bean.RecommendCourseBean;
import com.wxjz.module_base.db.bean.GuestChooseGrade;
import com.wxjz.module_base.db.bean.UserInfoBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.util.JumpUtil;
import com.wxjz.tenms_pad.activity.CourseDetailActivity;
import com.wxjz.tenms_pad.activity.FreeCourseActivity;
import com.wxjz.tenms_pad.activity.MainActivity;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.activity.RichTextActivity;
import com.wxjz.tenms_pad.adapter.FreeCourseAdapter;
import com.wxjz.tenms_pad.adapter.PopularCourseAdapter;
import com.wxjz.tenms_pad.adapter.RecommendCourseAdapter;
import com.wxjz.tenms_pad.mvp.contract.HomePageContract;
import com.wxjz.tenms_pad.mvp.presenter.CourseHomePagePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/5.
 * 首页
 */

public class CourseHomePageFragment extends BaseMvpFragment<CourseHomePagePresenter> implements HomePageContract.View, View.OnClickListener {
    private int levelId = 1;
    private XBanner banner;
    private RecyclerView rv_popular;
    private RecyclerView rv_free;
    private RecyclerView rv_recommend;
    /**
     * 推荐课程页码数
     */
    private int recommendPage = 1;
    private RecommendCourseAdapter recommendCourseAdapter;
    private List<RecommendBean.ListBean> mRecommendCourseList = new ArrayList<>();
    private TextView tvPopular;
    private TextView tvFree;
    private TextView tvRecommend;
    private String gradeId = null;
    private RelativeLayout rl_popular_empty;
    private RelativeLayout rl_free_empty;
    private RelativeLayout rl_recommend_empty;

    public CourseHomePageFragment() {
    }


    @Override
    protected CourseHomePagePresenter createPresenter() {
        return new CourseHomePagePresenter((MainActivity) mContext);
    }

    @Override
    protected void initView(View view) {
        TextView tv_free_more = view.findViewById(R.id.tv_free_more);
        tv_free_more.setOnClickListener(this);
        tvPopular = view.findViewById(R.id.tv_popular);
        tvFree = view.findViewById(R.id.tv_free);
        tvRecommend = view.findViewById(R.id.tv_recommend);
        rv_popular = view.findViewById(R.id.rv_popular);
        rl_popular_empty = view.findViewById(R.id.rl_popular_empty);
        rl_free_empty = view.findViewById(R.id.rl_free_empty);
        rl_recommend_empty = view.findViewById(R.id.rl_recommend_empty);

        rv_free = view.findViewById(R.id.rv_free);
        rv_recommend = view.findViewById(R.id.rv_recommend);
        TextView tvRefresh = view.findViewById(R.id.tv_recommend_refresh);
        tvRefresh.setOnClickListener(this);
        banner = view.findViewById(R.id.banner);
        banner.setPageTransformer(Transformer.Default);
        banner.setAutoPlayAble(true);
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                //  Glide.with(CourseHomePageFragment.this).load().placeholder(R.drawable.banner).error(R.drawable.banner).into((ImageView) view);
                ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);

                ((ImageView) view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
            }
        });
        initListener();

    }

    private void initListener() {
        //banner条目点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                BrannerBean.BannerListBean bannerListBean = (BrannerBean.BannerListBean) model;
                String course_id = bannerListBean.getCourse_id();
                String chapter_id = bannerListBean.getChapter_id();
                String section_id = bannerListBean.getSection_id();
                /**
                 * 1 链接型
                 * 2 详情型
                 * 3 推荐课程型
                 *
                 */
                int banner_type = bannerListBean.getBanner_type();
                if (banner_type == 2) {
                    Intent intent = new Intent(mContext, RichTextActivity.class);
                    intent.putExtra("title", bannerListBean.getBanner_name());
                    intent.putExtra("rich_text", bannerListBean.getRich_text());
                    intent.putExtra("type", bannerListBean.getBanner_type());
                    startActivity(intent);
                } else if (banner_type == 3) {
                    if (TextUtils.isEmpty(course_id) || course_id.equals("undefined")) {
                        toast("打开视频失败");
                        return;
                    }
                    JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, Integer.valueOf(course_id), Integer.valueOf(chapter_id), Integer.valueOf(section_id), gradeId);

                } else if (banner_type == 1) {
                    Intent intent = new Intent(mContext, RichTextActivity.class);
                    intent.putExtra("web_url", bannerListBean.getLink_url());
                    intent.putExtra("type", bannerListBean.getBanner_type());
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void initData() {
        levelId = getArguments().getInt("stuLevelId");
        gradeId = CheckGradeDao.getInstance().queryGradeId();
        levelId = CheckGradeDao.getInstance().queryleveId();
        mPresenter.getBannerData(levelId + "", gradeId);
        mPresenter.getPopularCourse(levelId, this.gradeId, 1, 4);
        mPresenter.getRecommendCourse(levelId, gradeId, recommendPage);
        mPresenter.getFreeCourse(levelId, this.gradeId, 1, 4);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.course_home_page_fragment;
    }

    public static CourseHomePageFragment getInstance(int stuLevelId) {
        CourseHomePageFragment courseHomePageFragment = new CourseHomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("stuLevelId", stuLevelId);
        courseHomePageFragment.setArguments(bundle);
        return courseHomePageFragment;
    }

    @Override
    public void onBanner(List<BrannerBean.BannerListBean> bannerList) {
        if (bannerList != null && bannerList.size() > 0) {
            banner.setVisibility(View.VISIBLE);
            banner.setBannerData(bannerList);
            banner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(CourseHomePageFragment.this).load(((BrannerBean.BannerListBean) model).getXBannerUrl())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                            .error(R.drawable.banner1).into((ImageView) view);
                }
            });
        } else {
            banner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPopularCourse(PopularMutiItem popularMutiItems) {
        tvPopular.setText(popularMutiItems.getTitle());
        final List<PopularMutiItem.ListBean> list = popularMutiItems.getList();
        if (list == null || list.size() == 0) {
            rv_popular.setVisibility(View.GONE);
            rl_popular_empty.setVisibility(View.VISIBLE);
            return;
        } else {
            rv_popular.setVisibility(View.VISIBLE);
            rl_popular_empty.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv_popular.setLayoutManager(layoutManager);

        final PopularCourseAdapter popularCourseAdapter = new PopularCourseAdapter();
        popularCourseAdapter.addData(list);
        rv_popular.setAdapter(popularCourseAdapter);
        popularCourseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PopularMutiItem.ListBean listBean = list.get(position);
                //  mPresenter.addCourseClickCount(listBean.getId());
                listBean.setClickCount(listBean.getClickCount() + 1);
                popularCourseAdapter.notifyItemChanged(position);
                JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, listBean.getId(), listBean.getChapterId(), listBean.getSectionId(), gradeId);

            }
        });

    }

    @Override
    public void onRecommendCourse(RecommendBean recommendCourseList, int recommendPage) {
        tvRecommend.setText(recommendCourseList.getTitle());
        final List<RecommendBean.ListBean> list = recommendCourseList.getList();
        if (list == null || list.size() == 0) {
            rv_recommend.setVisibility(View.GONE);
            rl_recommend_empty.setVisibility(View.VISIBLE);

            return;
        } else {
            rv_recommend.setVisibility(View.VISIBLE);
            rl_recommend_empty.setVisibility(View.GONE);
        }
        if (list == null) {
            return;
        }
        if (list.size() == 0) {
            toast("没有更多推荐课程");
            return;
        }

        if (recommendPage == 1) {
            mRecommendCourseList.clear();
            mRecommendCourseList.addAll(list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            rv_recommend.setLayoutManager(layoutManager);
            recommendCourseAdapter = new RecommendCourseAdapter(R.layout.layout_popular_course_unlearn_item, mRecommendCourseList);
            rv_recommend.setAdapter(recommendCourseAdapter);
        } else {
            mRecommendCourseList.clear();
            mRecommendCourseList.addAll(list);
            recommendCourseAdapter.notifyDataSetChanged();
        }
        recommendCourseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecommendBean.ListBean listBean = list.get(position);
//                mPresenter.addCourseClickCount(listBean.getId());
                listBean.setClickCount(listBean.getClickCount() + 1);
                recommendCourseAdapter.notifyItemChanged(position);
                JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, listBean.getId(), listBean.getChapterId(), listBean.getSectionId(), gradeId);

            }
        });
    }

    @Override
    public void onFreeCourse(FreeVideoListBean freeVideoListBean) {
        tvFree.setText(freeVideoListBean.getTitle());
        final List<FreeVideoListBean.ListBean> list = freeVideoListBean.getList();

        if (list == null || list.size() == 0) {
            rv_free.setVisibility(View.GONE);
            rl_free_empty.setVisibility(View.VISIBLE);

            return;
        } else {
            rv_free.setVisibility(View.VISIBLE);
            rl_free_empty.setVisibility(View.GONE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv_free.setLayoutManager(linearLayoutManager);
        final FreeCourseAdapter freeCourseAdapter = new FreeCourseAdapter();
        freeCourseAdapter.addData(list);
        rv_free.setAdapter(freeCourseAdapter);
        freeCourseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FreeVideoListBean.ListBean listBean = list.get(position);
                //   mPresenter.addCourseClickCount(listBean.getId());
                listBean.setClickCount(listBean.getClickCount() + 1);
                freeCourseAdapter.notifyItemChanged(position);
                JumpUtil.jump2CourseDetailActivity(mContext, CourseDetailActivity.class, listBean.getId(), listBean.getChapterId(), listBean.getSectionId(), gradeId);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
        mPresenter.getPopularCourse(levelId, this.gradeId, 1, 4);
        // mPresenter.getRecommendCourse(levelId, gradeId, recommendPage);
        mPresenter.getFreeCourse(levelId, this.gradeId, 1, 4);
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recommend_refresh:
                refreshRecommendCourse();
                break;
            case R.id.tv_free_more:
                gotoFreeCourseActivity();
                break;
        }
    }

    private void gotoFreeCourseActivity() {
        Intent intent = new Intent(mContext, FreeCourseActivity.class);
        startActivity(intent);
    }

    /**
     * 刷新推荐课程
     */
    private void refreshRecommendCourse() {
        recommendPage = recommendPage + 1;
        int i = recommendPage % 3;
        mPresenter.getRecommendCourse(levelId, gradeId, i == 0 ? 3 : i);
//        if (recommendPage == 3) {
//            recommendPage = 0;
//        }
    }
}
