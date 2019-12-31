package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/9/12.
 */

public class SelectVideoBean {


    /**
     * total : 171
     * list : [{"id":3,"videoTitle":"充钱看！大阿萨大大","videoDesc":"超大尺寸","playAddress":null,"createTime":1566974486000,"updateTime":1567992601000,"courseId":4,"coverUrl":"http://www.bestudy360.com/CSP/res//mobilecompus/file8a61ea76-983b-4d2b-adcf-19ea7b9e1a3e.jpeg","tipsNum":1,"termsNum":1,"expriseNum":1,"isDelete":0}]
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
        public int getClickCount() {
            return clickCount;
        }

        public void setClickCount(int clickCount) {
            this.clickCount = clickCount;
        }

        /**
         * id : 3
         * videoTitle : 充钱看！大阿萨大大
         * videoDesc : 超大尺寸
         * playAddress : null
         * createTime : 1566974486000
         * updateTime : 1567992601000
         * courseId : 4
         * coverUrl : http://www.bestudy360.com/CSP/res//mobilecompus/file8a61ea76-983b-4d2b-adcf-19ea7b9e1a3e.jpeg
         * tipsNum : 1
         * termsNum : 1
         * expriseNum : 1
         * isDelete : 0
         */
        private String videoId;
        private int clickCount;
        private int id;
        private String videoTitle;
        private String videoDesc;
        private Object playAddress;
        private int courseId;
        private String coverUrl;
        private int tipsNum;
        private int termsNum;
        private int expriseNum;
        private int isDelete;

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
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

        public String getVideoDesc() {
            return videoDesc;
        }

        public void setVideoDesc(String videoDesc) {
            this.videoDesc = videoDesc;
        }

        public Object getPlayAddress() {
            return playAddress;
        }

        public void setPlayAddress(Object playAddress) {
            this.playAddress = playAddress;
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
    }
}
