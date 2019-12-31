package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/9/17.
 */

public class NoteBean {


    /**
     * id : 310
     * videoDom : 123
     * domTitle : null
     * domContent : fadsfasfasdfasd
     * userAnswer : null
     * rightAnswer : null
     * isSelect : null
     * domType : null
     * createTime : null
     * updateTime : null
     * videoId : 1
     * userId : 123
     * isAnswer : null
     * tOwnDomOptions : []
     * tOwnDomOptionPictures : []
     * playAddress : http://www.bestudy360.com/CSP/res//mobilecompus/file335ed651-ded3-41db-82bc-a9939740c38f.mp4
     * videoTitle : 超级实用威风威风威风
     * videoDesc : 超级实用
     */
    private int chapterId;
    private int sectionId;
    private int id;
    private int videoDom;
    private Object domTitle;
    private String domContent;
    private Object userAnswer;
    private Object rightAnswer;
    private Object isSelect;
    private Object domType;
    private Object createTime;
    private Object updateTime;
    private int videoId;
    private String userId;
    private Object isAnswer;
    private String playAddress;
    private String videoTitle;
    private String videoDesc;
    private List<?> tOwnDomOptions;
    private List<?> tOwnDomOptionPictures;
    private String videAlipayVideoId;
    private int courseId;
    private long videoDuration;
    private String courseName;
    private String courseCover;

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

    public String getCourseCover() {
        return courseCover;
    }

    public void setCourseCover(String courseCover) {
        this.courseCover = courseCover;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public long getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(long videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideAlipayVideoId() {
        return videAlipayVideoId;
    }

    public void setVideAlipayVideoId(String videAlipayVideoId) {
        this.videAlipayVideoId = videAlipayVideoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVideoDom() {
        return videoDom;
    }

    public void setVideoDom(int videoDom) {
        this.videoDom = videoDom;
    }

    public Object getDomTitle() {
        return domTitle;
    }

    public void setDomTitle(Object domTitle) {
        this.domTitle = domTitle;
    }

    public String getDomContent() {
        return domContent;
    }

    public void setDomContent(String domContent) {
        this.domContent = domContent;
    }

    public Object getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Object userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Object getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Object rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Object getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Object isSelect) {
        this.isSelect = isSelect;
    }

    public Object getDomType() {
        return domType;
    }

    public void setDomType(Object domType) {
        this.domType = domType;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Object isAnswer) {
        this.isAnswer = isAnswer;
    }

    public String getPlayAddress() {
        return playAddress;
    }

    public void setPlayAddress(String playAddress) {
        this.playAddress = playAddress;
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

    public List<?> getTOwnDomOptions() {
        return tOwnDomOptions;
    }

    public void setTOwnDomOptions(List<?> tOwnDomOptions) {
        this.tOwnDomOptions = tOwnDomOptions;
    }

    public List<?> getTOwnDomOptionPictures() {
        return tOwnDomOptionPictures;
    }

    public void setTOwnDomOptionPictures(List<?> tOwnDomOptionPictures) {
        this.tOwnDomOptionPictures = tOwnDomOptionPictures;
    }
}
