package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/9/11.
 */

public class CourseListItemBean {

    @Override
    public String toString() {
        return "CourseListItemBean{" +
                "knowledge='" + knowledge + '\'' +
                ", courseList=" + courseList +
                '}';
    }

    /**
     * id : 1
     * knowledge : 集合神功运算
     * courseList : [{"id":2,"courseName":"集合解密视频","courseStatus":0,"knowledgeId":1,"coverUrl":"http://www.bestudy360.com/CSP/res//mobilecompus/file8a61ea76-983b-4d2b-adcf-19ea7b9e1a3e.jpeg","createTime":1566373665000,"updateTime":null,"courseDesc":"ffg","subId":7,"levelId":2,"videoCount":20,"clickCount":28,"teacherDesc":null,"teacherUrl":null,"isFree":0,"subjectName":"物理","progress":0,"idList":null,"bgcColor":null,"userVideoCount":null,"study":false},{"id":28,"courseName":"哈师大更换后撒代购","courseStatus":0,"knowledgeId":1,"coverUrl":"http://www.bestudy360.com/CSP/res//mobilecompus/file670edddb-9dea-452e-97cb-85d12ce50732.jpg","createTime":1568171865000,"updateTime":1568171888000,"courseDesc":"会撒娇搭嘎教师端噶几胡说八道好几遍几何","subId":5,"levelId":2,"videoCount":0,"clickCount":0,"teacherDesc":"股份的公司发生的是暂时的","teacherUrl":null,"isFree":1,"subjectName":"数学","progress":0,"idList":null,"bgcColor":null,"userVideoCount":null,"study":false}]
     */

    private int id;
    private String knowledge;
    private List<CourseListBean> courseList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public static class CourseListBean {
        @Override
        public String toString() {
            return "CourseListBean{" +
                    "courseName='" + courseName + '\'' +
                    '}';
        }

        public static final int HAS_LEARN = 1;
        public static final int NOT_LEARN = 2;
        /**
         * id : 2
         * courseName : 集合解密视频
         * courseStatus : 0
         * knowledgeId : 1
         * coverUrl : http://www.bestudy360.com/CSP/res//mobilecompus/file8a61ea76-983b-4d2b-adcf-19ea7b9e1a3e.jpeg
         * createTime : 1566373665000
         * updateTime : null
         * courseDesc : ffg
         * subId : 7
         * levelId : 2
         * videoCount : 20
         * clickCount : 28
         * teacherDesc : null
         * teacherUrl : null
         * isFree : 0
         * subjectName : 物理
         * progress : 0
         * idList : null
         * bgcColor : null
         * userVideoCount : null
         * study : false
         */

        private int id;
        private String courseName;
        private int courseStatus;
        private int knowledgeId;
        private String coverUrl;
        private long createTime;
        private Object updateTime;
        private String courseDesc;
        private int subId;
        private int levelId;
        private int videoCount;
        private int clickCount;
        private Object teacherDesc;
        private Object teacherUrl;
        private int isFree;
        private String subjectName;
        private double progress;
        private Object idList;
        private Object bgcColor;
        private Object userVideoCount;
        private boolean study;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getCourseStatus() {
            return courseStatus;
        }

        public void setCourseStatus(int courseStatus) {
            this.courseStatus = courseStatus;
        }

        public int getKnowledgeId() {
            return knowledgeId;
        }

        public void setKnowledgeId(int knowledgeId) {
            this.knowledgeId = knowledgeId;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public String getCourseDesc() {
            return courseDesc;
        }

        public void setCourseDesc(String courseDesc) {
            this.courseDesc = courseDesc;
        }

        public int getSubId() {
            return subId;
        }

        public void setSubId(int subId) {
            this.subId = subId;
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

        public int getClickCount() {
            return clickCount;
        }

        public void setClickCount(int clickCount) {
            this.clickCount = clickCount;
        }

        public Object getTeacherDesc() {
            return teacherDesc;
        }

        public void setTeacherDesc(Object teacherDesc) {
            this.teacherDesc = teacherDesc;
        }

        public Object getTeacherUrl() {
            return teacherUrl;
        }

        public void setTeacherUrl(Object teacherUrl) {
            this.teacherUrl = teacherUrl;
        }

        public int getIsFree() {
            return isFree;
        }

        public void setIsFree(int isFree) {
            this.isFree = isFree;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public double getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public Object getIdList() {
            return idList;
        }

        public void setIdList(Object idList) {
            this.idList = idList;
        }

        public Object getBgcColor() {
            return bgcColor;
        }

        public void setBgcColor(Object bgcColor) {
            this.bgcColor = bgcColor;
        }

        public Object getUserVideoCount() {
            return userVideoCount;
        }

        public void setUserVideoCount(Object userVideoCount) {
            this.userVideoCount = userVideoCount;
        }

        public boolean isStudy() {
            return study;
        }

        public void setStudy(boolean study) {
            this.study = study;
        }
    }
}
