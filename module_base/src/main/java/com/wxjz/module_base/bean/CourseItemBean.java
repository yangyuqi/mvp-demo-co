package com.wxjz.module_base.bean;

/**
 * Created by a on 2019/9/3.
 */

public class CourseItemBean {

    /**
     * id : 1
     * courseName : 二元一次方程解法大全
     * courseStatus : 1
     * knowledgeId : 2
     * coverUrl : http://www.bestudy360.com/CSP/res//mobilecompus/file15335eef-3383-4f05-9566-32661d588b3b.png
     * createTime : 1566373583000
     * updateTime : null
     * courseDesc : null
     * subId : 5
     * levelId : 2
     * videoCount : 0
     * clickCount : 1
     * subjectName : null
     * progress : 0
     * userVideoCount : null
     * study : false
     */

    private int id;
    private String courseName;
    private int courseStatus;
    private int knowledgeId;
    private String coverUrl;
    private long createTime;
    private Object updateTime;
    private Object courseDesc;
    private int subId;
    private int levelId;
    private int videoCount;
    private int clickCount;
    private String subjectName;
    private int progress;
    private Object userVideoCount;
    private boolean study;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(int courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(int knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(Object courseDesc) {
        this.courseDesc = courseDesc;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Object getUserVideoCount() {
        return userVideoCount;
    }

    public void setUserVideoCount(Object userVideoCount) {
        this.userVideoCount = userVideoCount;
    }

    public boolean isStudy() {
        return study;
    }

    public void setStudy(boolean study) {
        this.study = study;
    }
}
