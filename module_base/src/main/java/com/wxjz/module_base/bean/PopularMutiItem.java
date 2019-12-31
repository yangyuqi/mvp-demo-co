package com.wxjz.module_base.bean;


import java.util.List;

/**
 * Created by a on 2019/9/4.
 */

public class PopularMutiItem {


    /**
     * list : [{"id":890,"coverUrl":"http://vod.k12c.com/7201595a5ec4403390e081b7925c42ca/snapshots/5d61f7a2fd984e479a8b1fb4b8e4bd34-00005.jpg","videoId":"hyjys0898ud8audsd8asd","subId":129,"videoTitle":"测试视频2","videoDesc":"测试描述2","clickCount":34,"free":true,"subjectName":"测试修改","videoDuration":111,"chapterId":1,"sectionId":1,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null},{"id":891,"coverUrl":"http://vod.k12c.com/82219610b0404a99ba7f6df4dd3f438d/snapshots/5a5d13c9c669425b804cf1d78672802f-00005.jpg","videoId":"wqeqwq342d3dwqewq2e2","subId":2,"videoTitle":"测试3","videoDesc":"测试描述3","clickCount":1,"free":false,"subjectName":"数学","videoDuration":111,"chapterId":2,"sectionId":2,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null}]
     * title : 最受欢迎课程
     */

    private String title;
    private List<ListBean> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        public static final int NOT_LEARN = 1;
        /**
         * id : 890
         * coverUrl : http://vod.k12c.com/7201595a5ec4403390e081b7925c42ca/snapshots/5d61f7a2fd984e479a8b1fb4b8e4bd34-00005.jpg
         * videoId : hyjys0898ud8audsd8asd
         * subId : 129
         * videoTitle : 测试视频2
         * videoDesc : 测试描述2
         * clickCount : 34
         * free : true
         * subjectName : 测试修改
         * videoDuration : 111
         * chapterId : 1
         * sectionId : 1
         * sectionName : null
         * tipsNum : null
         * termsNum : null
         * expriseNum : null
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
    }
}
