package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName checkgradeBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-10-28 13:57
 * @Version 1.0
 */
public class CheckgradeBean extends LitePalSupport {

    //当前选择的学段
    private int educationType;

    //选择的年级所在的位置
    private int selectposition;

    //几年级
    private String grade;

    public int getSelectposition() {
        return selectposition;
    }

    public void setSelectposition(int selectposition) {
        this.selectposition = selectposition;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getEducationType() {
        return educationType;
    }

    public void setEducationType(int educationType) {
        this.educationType = educationType;
    }

}
