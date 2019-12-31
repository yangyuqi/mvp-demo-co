package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/9/18.
 */

public class LearnRecordBean {


    /**
     * todayTime : 0
     * list : [{"id":907,"coverUrl":"http://vod.k12c.com/36100fa8d5ea4ab0b22fcb81e3e12ffc/snapshots/2871d0a324a9475c8b44c596e8778645-00002.jpg","videoId":"36100fa8d5ea4ab0b22fcb81e3e12ffc","subId":1,"videoTitle":"12","videoDesc":null,"clickCount":73,"free":null,"subjectName":"语文","videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":null,"createTime":null,"videoSize":2107842,"progress":6,"sortId":null,"study":false},{"id":910,"coverUrl":"http://vod.k12c.com/a6982c98625c43779757bd8067337631/snapshots/74e4e278551c4cdd9387a58bbc9b7d0f-00005.jpg","videoId":"a6982c98625c43779757bd8067337631","subId":1,"videoTitle":"03-环境概述","videoDesc":null,"clickCount":173,"free":null,"subjectName":"语文","videoDuration":566,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":null,"createTime":null,"videoSize":73265065,"progress":8,"sortId":null,"study":false},{"id":908,"coverUrl":"http://vod.k12c.com/113cc5e954bc4592af5692f33ad63827/snapshots/740cb16c4065489194f18484d9fdd15d-00002.jpg","videoId":"113cc5e954bc4592af5692f33ad63827","subId":1,"videoTitle":"多多多","videoDesc":null,"clickCount":34,"free":null,"subjectName":"语文","videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":null,"createTime":null,"videoSize":2107842,"progress":10,"sortId":null,"study":false},{"id":909,"coverUrl":"http://vod.k12c.com/30c701d9c41c41ed8f4300f5c357775b/snapshots/f087e4a815ba4b99921f63b0e074bb34-00005.jpg","videoId":"30c701d9c41c41ed8f4300f5c357775b","subId":1,"videoTitle":"001-Ptyhon简介-01-Python的起源1","videoDesc":null,"clickCount":0,"free":null,"subjectName":"语文","videoDuration":321,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":null,"createTime":null,"videoSize":14190779,"progress":0,"sortId":null,"study":false},{"id":929,"coverUrl":"http://vod.k12c.com/9145863f314e42dd89763b23a756572e/snapshots/bf3d2f5653b34dbdaba648885dc5cfbc-00002.jpg","videoId":"9145863f314e42dd89763b23a756572e","subId":1,"videoTitle":"13","videoDesc":null,"clickCount":0,"free":null,"subjectName":"语文","videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":null,"createTime":null,"videoSize":2107842,"progress":0,"sortId":null,"study":false}]
     */

    private int todayTime;
    private List<ListBean> list;

    public int getTodayTime() {
        return todayTime;
    }

    public void setTodayTime(int todayTime) {
        this.todayTime = todayTime;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 907
         * coverUrl : http://vod.k12c.com/36100fa8d5ea4ab0b22fcb81e3e12ffc/snapshots/2871d0a324a9475c8b44c596e8778645-00002.jpg
         * videoId : 36100fa8d5ea4ab0b22fcb81e3e12ffc
         * subId : 1
         * videoTitle : 12
         * videoDesc : null
         * clickCount : 73
         * free : null
         * subjectName : 语文
         * videoDuration : 13
         * chapterId : 6
         * sectionId : 20
         * sectionName : null
         * tipsNum : null
         * termsNum : null
         * expriseNum : null
         * myProgress : 0.0
         * shelves : null
         * createTime : null
         * videoSize : 2107842
         * progress : 6
         * sortId : null
         * study : false
         */

        private int id;
        private String coverUrl;
        private String videoId;
        private int subId;
        private String videoTitle;
        private Object videoDesc;
        private int clickCount;
        private Object free;
        private String subjectName;
        private int videoDuration;
        private int chapterId;
        private int sectionId;
        private Object sectionName;
        private Object tipsNum;
        private Object termsNum;
        private Object expriseNum;
        private double myProgress;
        private Object shelves;
        private Object createTime;
        private int videoSize;
        private int progress;
        private Object sortId;
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

        public Object getVideoDesc() {
            return videoDesc;
        }

        public void setVideoDesc(Object videoDesc) {
            this.videoDesc = videoDesc;
        }

        public int getClickCount() {
            return clickCount;
        }

        public void setClickCount(int clickCount) {
            this.clickCount = clickCount;
        }

        public Object getFree() {
            return free;
        }

        public void setFree(Object free) {
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

        public double getMyProgress() {
            return myProgress;
        }

        public void setMyProgress(double myProgress) {
            this.myProgress = myProgress;
        }

        public Object getShelves() {
            return shelves;
        }

        public void setShelves(Object shelves) {
            this.shelves = shelves;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getVideoSize() {
            return videoSize;
        }

        public void setVideoSize(int videoSize) {
            this.videoSize = videoSize;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public Object getSortId() {
            return sortId;
        }

        public void setSortId(Object sortId) {
            this.sortId = sortId;
        }

        public boolean isStudy() {
            return study;
        }

        public void setStudy(boolean study) {
            this.study = study;
        }
    }
}
