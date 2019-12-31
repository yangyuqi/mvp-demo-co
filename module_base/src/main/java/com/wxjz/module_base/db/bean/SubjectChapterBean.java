package com.wxjz.module_base.db.bean;

import java.util.List;

/**
 * @ClassName SubjectChapterBean
 * @Description 查询章的时候返回的视频
 * @Author liufang
 * @Date 2019-11-03 16:58
 * @Version 1.0
 */
public class SubjectChapterBean {
    /**
     * "id": 22,
     * "sectionName": "大大松",
     * "subId": 1,
     * "levelId": 1,
     * "chapterId": 6,
     * "gradeId": null,
     * "videoModelList": []
     */
    private Object id;
    private Object sectionName;
    private Object subId;
    private Object levelId;
    private Object chapterId;
    private Object gradeId;
    private List<SubjectSectionBean> videoModelList;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getSectionName() {
        return sectionName;
    }

    public void setSectionName(Object sectionName) {
        this.sectionName = sectionName;
    }

    public Object getSubId() {
        return subId;
    }

    public void setSubId(Object subId) {
        this.subId = subId;
    }

    public Object getLevelId() {
        return levelId;
    }

    public void setLevelId(Object levelId) {
        this.levelId = levelId;
    }

    public Object getChapterId() {
        return chapterId;
    }

    public void setChapterId(Object chapterId) {
        this.chapterId = chapterId;
    }

    public Object getGradeId() {
        return gradeId;
    }

    public void setGradeId(Object gradeId) {
        this.gradeId = gradeId;
    }

    public List<SubjectSectionBean> getVideoModelList() {
        return videoModelList;
    }

    public void setVideoModelList(List<SubjectSectionBean> videoModelList) {
        this.videoModelList = videoModelList;
    }
}
