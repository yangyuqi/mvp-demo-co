package com.wxjz.module_base.bean;

/**
 * Created by a on 2019/9/3.
 */

public class TopTabBean {


    /**
     * id : 4
     * subjectName : 语文
     * levelId : 2
     * videoCount : null
     */

    private int id;
    private String subjectName;
    private int levelId;
    private Object videoCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public Object getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Object videoCount) {
        this.videoCount = videoCount;
    }
}
