package com.wxjz.module_base.bean;

import android.support.v4.app.Fragment;

/**
 * Created by a on 2019/8/30.
 */

public class CourseItemPage {
    private String title;
    private Fragment fragment;


    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
