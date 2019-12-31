package com.wxjz.tenms_pad.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wxjz.module_base.base.BaseActivity;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.viewPagerTransformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GuidePagesActivity
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-18 14:02
 * @Version 1.0
 */
public class GuidePagesActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private List<View> mViewList = new ArrayList<>();
    private ViewPagerAdapter adapter;
    private Button mBtn_splash;
    private LinearLayout mlayout;
    private ImageView mIvpoint;
    private ImageView[] mIvPointArray;
    private int[] Id;

    @Override
    protected boolean needEventBus() {
        return false;
    }

    @Override
    protected void initData() {
        //添加视图
        Id = new int[]{R.layout.guide_layout_1, R.layout.guide_layout_2,
                R.layout.guide_layout_3};
        for (int i = 0; i < Id.length; i++) {
            View view = LayoutInflater.from(this).inflate(Id[i], null);
            mViewList.add(view);
        }
        //编写指示器
        initPoint();
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        adapter = new ViewPagerAdapter(mViewList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
        mBtn_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuidePagesActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    private void initPoint() {
        mIvPointArray = new ImageView[mViewList.size()];
        for (int i = 0; i < mViewList.size(); i++) {
            mIvpoint = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            mIvpoint.setLayoutParams(params);
            params.setMargins(10, 0, 10, 0);
            mIvPointArray[i] = mIvpoint;
            if (i == 0) {
                mIvpoint.setImageResource(R.drawable.shape_circle_yellow);
            } else {
                mIvpoint.setImageResource(R.drawable.shape_circle_white);
            }

            mlayout.addView(mIvpoint);
        }
    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //实例化控件
        mViewPager = findViewById(R.id.viewpager_splash);
        mBtn_splash = findViewById(R.id.btn_splash);
        mlayout = findViewById(R.id.linear_layout_indicator);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_layout_guide_page;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        for (int j = 0; j < mIvPointArray.length; j++) {
            mIvPointArray[i].setImageResource(R.drawable.shape_circle_yellow);
            if (i != j) {
                mIvPointArray[j].setImageResource(R.drawable.shape_circle_white);
            }
        }

        if (i == mViewList.size() - 1) {
            mBtn_splash.setVisibility(View.VISIBLE);
        } else {
            mBtn_splash.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<View> lists;

        public ViewPagerAdapter(List<View> list) {
            this.lists = list;
        }

        @Override
        public int getCount() {
            if (lists != null) {
                return lists.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(lists.get(position));
            return lists.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,
                                @NonNull Object object) {
            container.removeView(lists.get(position));
        }
    }
}
