package com.wxjz.tenms_pad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wxjz.module_base.bean.CourseItemPage;

import java.util.List;

/**
 * Created by a on 2019/9/20.
 */

public class CourseDetailPageAdapter extends FragmentPagerAdapter {
    public CourseDetailPageAdapter(FragmentManager fm) {
        super(fm);
    }

    private List<CourseItemPage> mPages;

    public void setPages(List<CourseItemPage> pages) {
        this.mPages = pages;
        // notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return mPages.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPages.get(position).getTitle();
    }

}
