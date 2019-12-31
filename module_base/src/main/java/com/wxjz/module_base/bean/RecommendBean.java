package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/11/5.
 */

public class RecommendBean {

    /**
     * list : [{"id":937,"coverUrl":"http://vod.k12c.com/cff0478c605e4318bddbbbbc22b15582/snapshots/0c5eb7884ac843b89b90687eb980d842-00005.jpg","videoId":"cff0478c605e4318bddbbbbc22b15582","subId":1,"videoTitle":"张学友 - 回头太难","videoDesc":null,"clickCount":0,"free":true,"subjectName":null,"videoDuration":315,"chapterId":10,"sectionId":30,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":null,"videoSize":33091551,"progress":null,"sortId":936,"idList":null,"study":false},{"id":936,"coverUrl":"http://vod.k12c.com/680a3bc3f2144b8fbc7ee2924abc18fb/snapshots/b256b4e80aa845cfa544286f22cf4877-00002.jpg","videoId":"680a3bc3f2144b8fbc7ee2924abc18fb","subId":1,"videoTitle":"asddsadasasdasdasdadsasddasdasadsdasdasdadasdasasdasddass","videoDesc":null,"clickCount":0,"free":true,"subjectName":null,"videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":null,"videoSize":2107842,"progress":null,"sortId":935,"idList":null,"study":false},{"id":933,"coverUrl":"http://vod.k12c.com/5d1f7488fab9422ab517400031313e67/snapshots/ceb1ebfa50064b6f99716a80c85f3473-00002.jpg","videoId":"5d1f7488fab9422ab517400031313e67","subId":1,"videoTitle":"13","videoDesc":null,"clickCount":2,"free":true,"subjectName":null,"videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":null,"videoSize":2107842,"progress":null,"sortId":932,"idList":null,"study":false},{"id":932,"coverUrl":"http://vod.k12c.com/6c64ae657a1e40beae717b86a59d6651/snapshots/c3b2612953dd4b9e922dfbeec8d6c4e7-00002.jpg","videoId":"6c64ae657a1e40beae717b86a59d6651","subId":1,"videoTitle":"12","videoDesc":null,"clickCount":1,"free":true,"subjectName":null,"videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":null,"videoSize":2107842,"progress":null,"sortId":933,"idList":null,"study":false},{"id":929,"coverUrl":"http://vod.k12c.com/9145863f314e42dd89763b23a756572e/snapshots/bf3d2f5653b34dbdaba648885dc5cfbc-00002.jpg","videoId":"9145863f314e42dd89763b23a756572e","subId":1,"videoTitle":"13萨达十大","videoDesc":"萨达十大","clickCount":0,"free":true,"subjectName":null,"videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":null,"videoSize":2107842,"progress":null,"sortId":930,"idList":null,"study":false},{"id":931,"coverUrl":"http://vod.k12c.com/2465358feb494caca28b933d26e839a0/snapshots/fba9b39d33b1482c887db2cfe590f83c-00002.jpg","videoId":"2465358feb494caca28b933d26e839a0","subId":1,"videoTitle":"13","videoDesc":null,"clickCount":1,"free":true,"subjectName":null,"videoDuration":13,"chapterId":6,"sectionId":20,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":null,"videoSize":2107842,"progress":null,"sortId":932,"idList":null,"study":false}]
     * title : 推荐课程
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
        public static final int HAS_LEARN = 1;
        public static final int NOT_LEARN = 2;
        /**
         * id : 937
         * coverUrl : http://vod.k12c.com/cff0478c605e4318bddbbbbc22b15582/snapshots/0c5eb7884ac843b89b90687eb980d842-00005.jpg
         * videoId : cff0478c605e4318bddbbbbc22b15582
         * subId : 1
         * videoTitle : 张学友 - 回头太难
         * videoDesc : null
         * clickCount : 0
         * free : true
         * subjectName : null
         * videoDuration : 315
         * chapterId : 10
         * sectionId : 30
         * sectionName : null
         * tipsNum : null
         * termsNum : null
         * expriseNum : null
         * myProgress : 0
         * shelves : 0
         * createTime : null
         * videoSize : 33091551
         * progress : null
         * sortId : 936
         * idList : null
         * study : false
         */

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
        private Object sectionName;
        private Object tipsNum;
        private Object termsNum;
        private Object expriseNum;
        private int myProgress;
        private int shelves;
        private Object createTime;
        private int videoSize;
        private Object progress;
        private int sortId;
        private Object idList;
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

        public int getShelves() {
            return shelves;
        }

        public void setShelves(int shelves) {
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

        public Object getProgress() {
            return progress;
        }

        public void setProgress(Object progress) {
            this.progress = progress;
        }

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        public Object getIdList() {
            return idList;
        }

        public void setIdList(Object idList) {
            this.idList = idList;
        }

        public boolean isStudy() {
            return study;
        }

        public void setStudy(boolean study) {
            this.study = study;
        }
    }
}
