package com.wxjz.module_base.bean;

/**
 * Created by a on 2019/9/27.
 */

public class PlayAuthBean {

    /**
     * playAuth : eeeee
     * videoMeta : {"coverURL":"http://vod.k12c.com/ea31c7e76b45423a845e6f148cdf48e1/snapshots/7d19647290694da3a046072d0f97b422-00002.jpg","duration":13.504,"status":"Normal","title":"12 - 副本","videoId":"ea31c7e76b45423a845e6f148cdf48e1"}
     */

    private String playAuth;
    private VideoMetaBean videoMeta;

    public String getPlayAuth() {
        return playAuth;
    }

    public void setPlayAuth(String playAuth) {
        this.playAuth = playAuth;
    }

    public VideoMetaBean getVideoMeta() {
        return videoMeta;
    }

    public void setVideoMeta(VideoMetaBean videoMeta) {
        this.videoMeta = videoMeta;
    }

    public static class VideoMetaBean {
        /**
         * coverURL : http://vod.k12c.com/ea31c7e76b45423a845e6f148cdf48e1/snapshots/7d19647290694da3a046072d0f97b422-00002.jpg
         * duration : 13.504
         * status : Normal
         * title : 12 - 副本
         * videoId : ea31c7e76b45423a845e6f148cdf48e1
         */

        private String coverURL;
        private double duration;
        private String status;
        private String title;
        private String videoId;

        public String getCoverURL() {
            return coverURL;
        }

        public void setCoverURL(String coverURL) {
            this.coverURL = coverURL;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }
}
