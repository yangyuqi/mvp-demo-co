package com.wxjz.tenms_pad.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.wxjz.module_base.base.BaseFragment;
import com.wxjz.module_base.bean.CourseItemPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/8/30.
 * 主页课程adapter
 */

public class SearchTabAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private ArrayList<BaseFragment> mList;
    public SearchTabAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    private List<CourseItemPage> mPages ;
    public void setPages(List<CourseItemPage> pages, ArrayList<BaseFragment> fragmentList){
     this.mPages = pages;
    // notifyDataSetChanged();
        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment f : fm.getFragments()) {
            transaction.remove(f);
        }
        transaction.commitNow();
        this.mList = fragmentList;
        notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int i) {

        return mList.get(i);
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


    @Override
    public int getItemPosition(@NonNull Object object) {
        //表示页面不存在了，需要重新加载
        return  POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
