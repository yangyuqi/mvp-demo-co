package com.wxjz.module_base.db.bean;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by a on 2019/9/10.
 */

public class SearchTabContentBean {


    private List<AllBean> all;
    private List<TableBean> table;

    public List<AllBean> getAll() {
        return all;
    }

    public void setAll(List<AllBean> all) {
        this.all = all;
    }

    public List<TableBean> getTable() {
        return table;
    }

    public void setTable(List<TableBean> table) {
        this.table = table;
    }

    public static class AllBean implements Parcelable {
        public static final int HAS_LEARN = 1;
        public static final int NOT_LEARN = 2;
        /**
         * id : 889
         * coverUrl : http://vod.k12c.com/7201595a5ec4403390e081b7925c42ca/snapshots/5d61f7a2fd984e479a8b1fb4b8e4bd34-00005.jpg
         * videoId : 5tg678hy89899817h7h890wqd90d
         * subId : 1
         * videoTitle : 测试视频
         * videoDesc : 测试描述
         * clickCount : 19
         * free : true
         * subjectName : 语文
         * videoDuration : 1111
         * chapterId : 1
         * sectionId : 1
         * sectionName : null
         * tipsNum : null
         * termsNum : null
         * expriseNum : null
         * myProgress : 0
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
        private boolean study;

        protected AllBean(Parcel in) {
            id = in.readInt();
            coverUrl = in.readString();
            videoId = in.readString();
            subId = in.readInt();
            videoTitle = in.readString();
            videoDesc = in.readString();
            clickCount = in.readInt();
            free = in.readByte() != 0;
            subjectName = in.readString();
            videoDuration = in.readInt();
            chapterId = in.readInt();
            sectionId = in.readInt();
            myProgress = in.readInt();
            study = in.readByte() != 0;
        }

        public static final Creator<AllBean> CREATOR = new Creator<AllBean>() {
            @Override
            public AllBean createFromParcel(Parcel in) {

                return new AllBean(in);
            }

            @Override
            public AllBean[] newArray(int size) {
                return new AllBean[size];
            }
        };

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

        public boolean isStudy() {
            return study;
        }

        public void setStudy(boolean study) {
            this.study = study;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(coverUrl);
            dest.writeString(videoId);
            dest.writeInt(subId);
            dest.writeString(videoTitle);
            dest.writeString(videoDesc);
            dest.writeInt(clickCount);
            dest.writeByte((byte) (free ? 1 : 0));
            dest.writeString(subjectName);
            dest.writeInt(videoDuration);
            dest.writeInt(chapterId);
            dest.writeInt(sectionId);
            dest.writeInt(myProgress);
            dest.writeByte((byte) (study ? 1 : 0));
        }

    }

    public static class TableBean {
        /**
         * id : 1
         * subjectName : 语文
         * levelId : 1
         * videoCount : 2
         */

        private int id;
        private String subjectName;
        private int levelId;
        private int videoCount;

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

        public int getVideoCount() {
            return videoCount;
        }

        public void setVideoCount(int videoCount) {
            this.videoCount = videoCount;
        }
    }
}
