package com.wxjz.module_base.bean;

/**
 * Created by a on 2019/10/31.
 */

public class SearchBean {

    public static final int HAS_LEARN = 1;
    public static final int NOT_LEARN = 2;
    /**
     * id : 891
     * coverUrl : http://vod.k12c.com/82219610b0404a99ba7f6df4dd3f438d/snapshots/5a5d13c9c669425b804cf1d78672802f-00005.jpg
     * videoId : wqeqwq342d3dwqewq2e2
     * subId : 2
     * videoTitle : 测试3
     * videoDesc : 测试描述3
     * clickCount : 3
     * free : false
     * subjectName : null
     * videoDuration : 111
     * chapterId : 2
     * sectionId : 2
     * sectionName : null
     * tipsNum : null
     * termsNum : null
     * expriseNum : null
     * myProgress : 0
     * watchTime : null
     * study : false
     */

    private int id;
    private String coverUrl;
    private String videoId;
    private int subId;
    private String videoTitle;
    private String videoDesc;
    private int clickCount;
    private boolean free;
    private String subjectName;
    private int videoDuration;
    private int chapterId;
    private int sectionId;
    private Object sectionName;
    private Object tipsNum;
    private Object termsNum;
    private Object expriseNum;
    private int myProgress;
    private Object watchTime;
    private boolean study;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public Object getSectionName() {
        return sectionName;
    }

    public void setSectionName(Object sectionName) {
        this.sectionName = sectionName;
    }

    public Object getTipsNum() {
        return tipsNum;
    }

    public void setTipsNum(Object tipsNum) {
        this.tipsNum = tipsNum;
    }

    public Object getTermsNum() {
        return termsNum;
    }

    public void setTermsNum(Object termsNum) {
        this.termsNum = termsNum;
    }

    public Object getExpriseNum() {
        return expriseNum;
    }

    public void setExpriseNum(Object expriseNum) {
        this.expriseNum = expriseNum;
    }

    public int getMyProgress() {
        return myProgress;
    }

    public void setMyProgress(int myProgress) {
        this.myProgress = myProgress;
    }

    public Object getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(Object watchTime) {
        this.watchTime = watchTime;
    }

    public boolean isStudy() {
        return study;
    }

    public void setStudy(boolean study) {
        this.study = study;
    }
}
