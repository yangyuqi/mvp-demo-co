package com.wxjz.tenms_pad.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.wxjz.module_base.bean.CourseItemPage;

import java.util.List;

/**
 * Created by a on 2019/8/30.
 * 主页课程adapter
 */

public class FragmentInMyNoteAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fragmentManager;
    public FragmentInMyNoteAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    private List<CourseItemPage> mPages ;
    public void setPages(List<CourseItemPage> pages){
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mPages.get(position).getTitle();
    }



}
