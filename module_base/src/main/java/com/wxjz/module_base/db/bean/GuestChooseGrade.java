package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName guestChooseGrade
 * @Description 游客模式情况下，选择的年级和班级
 * @Author liufang
 * @Date 2019-10-29 15:50
 * @Version 1.0
 */
public class GuestChooseGrade extends LitePalSupport {


    /**
     * 选择的学习阶段对应的主键ID
     */
    private int levelid;
    /**
     * 选择的学习阶段
     */
    private String levelName;
    /**
     * 选择的年级对应的主键ID
     */
    private String gradeId;
    /**
     * 选择的年级
     */
    private String gradeName;

    /**
     * 年级是否匹配上了
     */
    private Boolean gradeNameCanMatch;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }

    public Boolean getGradeNameCanMatch() {
        return gradeNameCanMatch;
    }

    public void setGradeNameCanMatch(Boolean gradeNameCanMatch) {
        this.gradeNameCanMatch = gradeNameCanMatch;
    }
}
