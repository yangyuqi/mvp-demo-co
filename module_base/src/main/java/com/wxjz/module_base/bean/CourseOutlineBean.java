package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/9/20.
 */

public class CourseOutlineBean {


    /**
     * total : 2
     * list : [{"id":514,"videoTitle":"1569550372818","videoDesc":null,"playAddress":null,"createTime":"2019年09月27 10:28","updateTime":"2019年09月27 10:28","courseId":128,"coverUrl":"http://vod.k12c.com/d65d5b1c032749499e775809db452e74/snapshots/2d5656791dba4308aea57f355f8cd04e-00001.jpg","tipsNum":0,"downloadType":null,"termsNum":0,"expriseNum":0,"isDelete":0,"clickCount":0,"progress":null,"videoDuration":1,"sortID":520,"videoSize":228070,"isStudy":false,"videoId":"d65d5b1c032749499e775809db452e74"},{"id":515,"videoTitle":"大话西游之大圣娶亲","videoDesc":null,"playAddress":null,"createTime":"2019年09月27 10:39","updateTime":"2019年09月27 10:39","courseId":128,"coverUrl":"http://vod.k12c.com/c7fc3d723fb74a72a103080db62bc331/snapshots/0eb7f14b90e143f1aacb106dbbcaffd7-00005.jpg","tipsNum":0,"downloadType":null,"termsNum":0,"expriseNum":0,"isDelete":0,"clickCount":0,"progress":null,"videoDuration":null,"sortID":521,"videoSize":1614585375,"isStudy":false,"videoId":"c7fc3d723fb74a72a103080db62bc331"}]
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        @Override
        public String toString() {
            return "ListBean{" +
                    ", videoTitle='" + videoTitle + '\'' +

                    ", download_status=" + download_status +
                    '}';
        }

        /**
         * id : 514
         * videoTitle : 1569550372818
         * videoDesc : null
         * playAddress : null
         * createTime : 2019年09月27 10:28
         * updateTime : 2019年09月27 10:28
         * courseId : 128
         * coverUrl : http://vod.k12c.com/d65d5b1c032749499e775809db452e74/snapshots/2d5656791dba4308aea57f355f8cd04e-00001.jpg
         * tipsNum : 0
         * downloadType : null
         * termsNum : 0
         * expriseNum : 0
         * isDelete : 0
         * clickCount : 0
         * progress : null
         * videoDuration : 1
         * sortID : 520
         * videoSize : 228070
         * isStudy : false
         * videoId : d65d5b1c032749499e775809db452e74
         */

        private int id;
        private String videoTitle;
        private Object videoDesc;
        private Object playAddress;
        private String createTime;
        private String updateTime;
        private int courseId;
        private String coverUrl;
        private int tipsNum;
        private Object downloadType;
        private int termsNum;
        private int expriseNum;
        private int isDelete;
        private int clickCount;
        private double progress;
        private int videoDuration;
        private int sortID;
        private long videoSize;
        private boolean isStudy;
        private String videoId;
        /**
         * 0 未下载
         * 1下载中
         * 2已下载
         */
        private int download_status;

        public boolean isStudy() {
            return isStudy;
        }

        public void setStudy(boolean study) {
            isStudy = study;
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

        public Object getPlayAddress() {
            return playAddress;
        }

        public void setPlayAddress(Object playAddress) {
            this.playAddress = playAddress;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getTipsNum() {
            return tipsNum;
        }

        public void setTipsNum(int tipsNum) {
            this.tipsNum = tipsNum;
        }

        public Object getDownloadType() {
            return downloadType;
        }

        public void setDownloadType(Object downloadType) {
            this.downloadType = downloadType;
        }

        public int getTermsNum() {
            return termsNum;
        }

        public void setTermsNum(int termsNum) {
            this.termsNum = termsNum;
        }

        public int getExpriseNum() {
            return expriseNum;
        }

        public void setExpriseNum(int expriseNum) {
            this.expriseNum = expriseNum;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getClickCount() {
            return clickCount;
        }

        public void setClickCount(int clickCount) {
            this.clickCount = clickCount;
        }

        public double getProgress() {
            return progress;
        }

        public void setProgress(double progress) {
            this.progress = progress;
        }

        public int getVideoDuration() {
            return videoDuration;
        }

        public void setVideoDuration(int videoDuration) {
            this.videoDuration = videoDuration;
        }

        public int getSortID() {
            return sortID;
        }

        public void setSortID(int sortID) {
            this.sortID = sortID;
        }

        public long getVideoSize() {
            return videoSize;
        }

        public void setVideoSize(long videoSize) {
            this.videoSize = videoSize;
        }

        public boolean isIsStudy() {
            return isStudy;
        }

        public void setIsStudy(boolean isStudy) {
            this.isStudy = isStudy;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }
}
