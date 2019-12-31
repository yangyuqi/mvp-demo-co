package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/11/1.
 */

public class VideoInPlayPageBean {

    /**
     * videoList : [{"id":907,"coverUrl":"http://vod.k12c.com/36100fa8d5ea4ab0b22fcb81e3e12ffc/snapshots/2871d0a324a9475c8b44c596e8778645-00002.jpg","videoId":"36100fa8d5ea4ab0b22fcb81e3e12ffc","subId":1,"videoTitle":"12","videoDesc":null,"clickCount":33,"free":true,"subjectName":"语文","videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":1572597884000,"study":false},{"id":908,"coverUrl":"http://vod.k12c.com/113cc5e954bc4592af5692f33ad63827/snapshots/740cb16c4065489194f18484d9fdd15d-00002.jpg","videoId":"113cc5e954bc4592af5692f33ad63827","subId":1,"videoTitle":"粉色的付多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多","videoDesc":null,"clickCount":0,"free":true,"subjectName":"语文","videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":1572601463000,"study":false},{"id":909,"coverUrl":"http://vod.k12c.com/30c701d9c41c41ed8f4300f5c357775b/snapshots/f087e4a815ba4b99921f63b0e074bb34-00005.jpg","videoId":"30c701d9c41c41ed8f4300f5c357775b","subId":1,"videoTitle":"001-Ptyhon简介-01-Python的起源1","videoDesc":null,"clickCount":0,"free":true,"subjectName":"语文","videoDuration":321,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":1572749249000,"study":false},{"id":910,"coverUrl":"http://vod.k12c.com/a6982c98625c43779757bd8067337631/snapshots/74e4e278551c4cdd9387a58bbc9b7d0f-00005.jpg","videoId":"a6982c98625c43779757bd8067337631","subId":1,"videoTitle":"03-环境概述","videoDesc":null,"clickCount":5,"free":true,"subjectName":"语文","videoDuration":566,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":1572747202000,"study":false},{"id":911,"coverUrl":"http://vod.k12c.com/b24a07739afa41f093e97be2eb1e4af4/snapshots/c72e27bde9184fdf819f3d7772d4745e-00002.jpg","videoId":"b24a07739afa41f093e97be2eb1e4af4","subId":1,"videoTitle":"12 - 副本","videoDesc":null,"clickCount":0,"free":true,"subjectName":"语文","videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":1572747204000,"study":false}]
     * title : 打
     */

    private String title;
    private List<VideoListBean> videoList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public static class VideoListBean {
        /**
         * id : 907
         * coverUrl : http://vod.k12c.com/36100fa8d5ea4ab0b22fcb81e3e12ffc/snapshots/2871d0a324a9475c8b44c596e8778645-00002.jpg
         * videoId : 36100fa8d5ea4ab0b22fcb81e3e12ffc
         * subId : 1
         * videoTitle : 12
         * videoDesc : null
         * clickCount : 33
         * free : true
         * subjectName : 语文
         * videoDuration : 13
         * chapterId : 6
         * sectionId : 20
         * sectionName : null
         * tipsNum : null
         * termsNum : null
         * expriseNum : null
         * myProgress : 0
         * shelves : 0
         * createTime : 1572597884000
         * study : false
         */
        /**
         * 0 未开始
         * 1 已开始
         * 2 已完成
         */
        private long videoSize;
        private int download_status;
        private int id;
        private String coverUrl;
        private String videoId;
        private int subId;
        private String videoTitle;
        private Object videoDesc;
        private int clickCount;
        private boolean free;
        private String subjectName;
        private int videoDuration;
        private int chapterId;
        private int sectionId;
        private String sectionName;
        private Object tipsNum;
        private Object termsNum;
        private Object expriseNum;
        private int myProgress;
        private int shelves;
        private long createTime;
        private boolean study;



        public long getVideoSize() {
            return videoSize;
        }

        public void setVideoSize(long videoSize) {
            this.videoSize = videoSize;
        }

        public int getDownload_status() {
            return download_status;
        }

        public void setDownload_status(int download_status) {
            this.download_status = download_status;
        }

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

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
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

        public int getShelves() {
            return shelves;
        }

        public void setShelves(int shelves) {
            this.shelves = shelves;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public boolean isStudy() {
            return study;
        }

        public void setStudy(boolean study) {
            this.study = study;
        }
    }
}
