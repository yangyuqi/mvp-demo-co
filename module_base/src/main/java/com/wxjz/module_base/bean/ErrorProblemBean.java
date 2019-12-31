package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/9/18.
 */

public class ErrorProblemBean {

    /**
     * id : 305
     * videoDom : 200
     * domTitle : null
     * domContent : 如图所示装置进行实验（图中铁架台等仪器均已略去）．在I中加入试剂后，塞紧橡皮塞，立即打开止水夹，II中有气泡冒出；一段时间后关闭止水夹，II 中液面上升，溶液由无色变为浑浊．符合以上实验现象的I和II中应加入的试剂是
     * userAnswer : B
     * rightAnswer : A,B
     * isSelect : 0
     * domType : null
     * createTime : null
     * updateTime : null
     * videoId : 1
     * userId : 123
     * isAnswer : 0
     * tOwnDomOptions : [{"id":381,"option":"A","optionContent":"sdfjlksdjfsdjlfkjslkjsdfkldjflksd kjfkjsadkkldjf","createTime":1568608010000,"updateTime":1568608010000},{"id":382,"option":"B","optionContent":"了解公司就打开了几个了快递费了的空间够可怜的房间里快递费","createTime":1568608010000,"updateTime":1568608010000},{"id":383,"option":"C","optionContent":"打扫房间拉萨的副驾驶的水电费","createTime":1568608010000,"updateTime":1568608010000},{"id":384,"option":"D","optionContent":"adfasdfsdfksdfkljsdfj","createTime":1568608010000,"updateTime":1568608010000}]
     * tOwnDomOptionPictures : [{"id":75,"domId":305,"location":"project","optionTag":null,"url":"http://www.bestudy360.com/CSP/res/course-select/843f91c3-c4c6-48c8-980e-37b4f0f19c8f.jpg","createTime":1568608010000,"updateTime":1568608010000}]
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
    private String userAnswer;
    private String rightAnswer;
    private int isSelect;
    private Object domType;
    private Object createTime;
    private Object updateTime;
    private int videoId;
    private String userId;
    private int isAnswer;
    private String playAddress;
    private String videoTitle;
    private String videoDesc;
    private List<TOwnDomOptionsBean> tOwnDomOptions;
    private List<TOwnDomOptionPicturesBean> tOwnDomOptionPictures;
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

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
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

    public int getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(int isAnswer) {
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

    public List<TOwnDomOptionsBean> getTOwnDomOptions() {
        return tOwnDomOptions;
    }

    public void setTOwnDomOptions(List<TOwnDomOptionsBean> tOwnDomOptions) {
        this.tOwnDomOptions = tOwnDomOptions;
    }

    public List<TOwnDomOptionPicturesBean> getTOwnDomOptionPictures() {
        return tOwnDomOptionPictures;
    }

    public void setTOwnDomOptionPictures(List<TOwnDomOptionPicturesBean> tOwnDomOptionPictures) {
        this.tOwnDomOptionPictures = tOwnDomOptionPictures;
    }

    public static class TOwnDomOptionsBean {
        /**
         * id : 381
         * option : A
         * optionContent : sdfjlksdjfsdjlfkjslkjsdfkldjflksd kjfkjsadkkldjf
         * createTime : 1568608010000
         * updateTime : 1568608010000
         */

        private int id;
        private String option;
        private String optionContent;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getOptionContent() {
            return optionContent;
        }

        public void setOptionContent(String optionContent) {
            this.optionContent = optionContent;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class TOwnDomOptionPicturesBean {
        /**
         * id : 75
         * domId : 305
         * location : project
         * optionTag : null
         * url : http://www.bestudy360.com/CSP/res/course-select/843f91c3-c4c6-48c8-980e-37b4f0f19c8f.jpg
         * createTime : 1568608010000
         * updateTime : 1568608010000
         */

        private int id;
        private int domId;
        private String location;
        private String optionTag;
        private String url;
        private long createTime;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDomId() {
            return domId;
        }

        public void setDomId(int domId) {
            this.domId = domId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getOptionTag() {
            return optionTag;
        }

        public void setOptionTag(String optionTag) {
            this.optionTag = optionTag;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
