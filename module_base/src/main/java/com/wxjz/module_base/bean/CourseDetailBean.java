package com.wxjz.module_base.bean;

import java.io.Serializable;

/**
 * Created by a on 2019/9/20.
 */

public class CourseDetailBean implements Serializable {


    /**
     * id : 99
     * courseName : 课程16
     * courseStatus : 0
     * knowledgeId : 53
     * coverUrl : http://www.bestudy360.com/CSP/res//mobilecompus/file72af8ec3-99df-4511-938e-9d2dfcb475e6.png
     * createTime : 1569375538000
     * updateTime : null
     * courseDesc : <p><img src="http://www.bestudy360.com/CSP/res//ueditor/jsp/upload/image/20190925/1569374756613050705.jpg50677009-9740-41ca-ba59-7a6a41e04cc4.jpg" title="50677009-9740-41ca-ba59-7a6a41e04cc4.jpg" alt="" width="500" height="73" style="width: 500px; height: 73px;"/>sdddddddsaaaaaaaaaaaaasddsadsadsadsaddsadasd</p>
     * subId : 1
     * levelId : 1
     * videoCount : 0
     * clickCount : 1
     * teacherDesc : 萨顶顶多多多多多多多多多多
     * teacherUrl : http://www.bestudy360.com/CSP/res//mobilecompus/fileb4174fee-cd5b-4a33-b79c-a52b72f0394c.jpg
     * isFree : 0
     * subjectName : null
     * progress : 0
     * idList : null
     * bgcColor : null
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
    private String courseDesc;
    private int subId;
    private int levelId;
    private int videoCount;
    private int clickCount;
    private String teacherDesc;
    private String teacherUrl;
    private int isFree;
    private Object subjectName;
    private int progress;
    private Object idList;
    private Object bgcColor;
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

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
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

    public String getTeacherDesc() {
        return teacherDesc;
    }

    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }

    public String getTeacherUrl() {
        return teacherUrl;
    }

    public void setTeacherUrl(String teacherUrl) {
        this.teacherUrl = teacherUrl;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public Object getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(Object subjectName) {
        this.subjectName = subjectName;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Object getIdList() {
        return idList;
    }

    public void setIdList(Object idList) {
        this.idList = idList;
    }

    public Object getBgcColor() {
        return bgcColor;
    }

    public void setBgcColor(Object bgcColor) {
        this.bgcColor = bgcColor;
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
