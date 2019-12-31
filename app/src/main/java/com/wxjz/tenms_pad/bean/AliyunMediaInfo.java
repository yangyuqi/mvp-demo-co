package com.wxjz.tenms_pad.bean;

import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.wxjz.module_aliyun.aliyun.utils.download.AliyunDownloadMediaInfo;

import java.util.List;

/**
 * Created by a on 2019/10/1.
 */

public class AliyunMediaInfo {


   private String sectionName;

    private List<DownloadVideoMidiainfo> downloadVideoMidiainfos;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<DownloadVideoMidiainfo> getDownloadVideoMidiainfos() {
        return downloadVideoMidiainfos;
    }

    @Override
    public String toString() {
        return "AliyunMediaInfo{" +
                ", courseCover='" + sectionName + '\'' +
                ", downloadVideoMidiainfos=" + downloadVideoMidiainfos +
                '}';
    }

    public void setDownloadVideoMidiainfos(List<DownloadVideoMidiainfo> downloadVideoMidiainfos) {
        this.downloadVideoMidiainfos = downloadVideoMidiainfos;
    }

    public static class DownloadVideoMidiainfo {
        private String mVid;
        private String mQuality;
        private int mProgress = 0;
        private String mSavePath = null;
        private String mTitle;
        private long mDuration;
        private AliyunDownloadMediaInfo.Status mStatus;
        private long mSize;
        private String mFormat;
        private int mDownloadIndex = 0;
        private int isEncripted = 0;
        private TrackInfo mTrackInfo;
        private VidSts mVidSts;
        private ErrorCode errorCode;
        private String errorMsg;
        private int mFileHandleProgress = 0;
        private int mQualityIndex;
        /**
         * 播放凭证
         */
        private VidAuth mVidAuth;
        private String videoCover;
        private String videoId;

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getVideoCover() {
            return videoCover;
        }

        public void setVideoCover(String videoCover) {
            this.videoCover = videoCover;
        }

        public String getmVid() {
            return mVid;
        }

        public void setmVid(String mVid) {
            this.mVid = mVid;
        }

        public String getmQuality() {
            return mQuality;
        }

        public void setmQuality(String mQuality) {
            this.mQuality = mQuality;
        }

        public int getmProgress() {
            return mProgress;
        }

        public void setmProgress(int mProgress) {
            this.mProgress = mProgress;
        }

        public String getmSavePath() {
            return mSavePath;
        }

        public void setmSavePath(String mSavePath) {
            this.mSavePath = mSavePath;
        }

        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }



        public long getmDuration() {
            return mDuration;
        }

        public void setmDuration(long mDuration) {
            this.mDuration = mDuration;
        }

        public AliyunDownloadMediaInfo.Status getmStatus() {
            return mStatus;
        }

        public void setmStatus(AliyunDownloadMediaInfo.Status mStatus) {
            this.mStatus = mStatus;
        }

        public long getmSize() {
            return mSize;
        }

        public void setmSize(long mSize) {
            this.mSize = mSize;
        }

        public String getmFormat() {
            return mFormat;
        }

        public void setmFormat(String mFormat) {
            this.mFormat = mFormat;
        }

        public int getmDownloadIndex() {
            return mDownloadIndex;
        }

        public void setmDownloadIndex(int mDownloadIndex) {
            this.mDownloadIndex = mDownloadIndex;
        }

        public int getIsEncripted() {
            return isEncripted;
        }

        public void setIsEncripted(int isEncripted) {
            this.isEncripted = isEncripted;
        }

        public TrackInfo getmTrackInfo() {
            return mTrackInfo;
        }

        public void setmTrackInfo(TrackInfo mTrackInfo) {
            this.mTrackInfo = mTrackInfo;
        }

        public VidSts getmVidSts() {
            return mVidSts;
        }

        public void setmVidSts(VidSts mVidSts) {
            this.mVidSts = mVidSts;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public int getmFileHandleProgress() {
            return mFileHandleProgress;
        }

        public void setmFileHandleProgress(int mFileHandleProgress) {
            this.mFileHandleProgress = mFileHandleProgress;
        }

        public int getmQualityIndex() {
            return mQualityIndex;
        }

        public void setmQualityIndex(int mQualityIndex) {
            this.mQualityIndex = mQualityIndex;
        }

        public VidAuth getmVidAuth() {
            return mVidAuth;
        }

        public void setmVidAuth(VidAuth mVidAuth) {
            this.mVidAuth = mVidAuth;
        }


        @Override
        public String toString() {
            return "DownloadVideoMidiainfo{" +
                    "mVid='" + mVid + '\'' +
                    ", mQuality='" + mQuality + '\'' +
                    ", mProgress=" + mProgress +
                    ", mSavePath='" + mSavePath + '\'' +
                    ", mTitle='" + mTitle + '\'' +
                    ", mDuration=" + mDuration +
                    ", mStatus=" + mStatus +
                    ", mSize=" + mSize +
                    ", mFormat='" + mFormat + '\'' +
                    ", mDownloadIndex=" + mDownloadIndex +
                    ", isEncripted=" + isEncripted +
                    ", mTrackInfo=" + mTrackInfo +
                    ", mVidSts=" + mVidSts +
                    ", errorCode=" + errorCode +
                    ", errorMsg='" + errorMsg + '\'' +
                    ", mFileHandleProgress=" + mFileHandleProgress +
                    ", mQualityIndex=" + mQualityIndex +
                    ", mVidAuth=" + mVidAuth +
                    '}';
        }
    }
}
