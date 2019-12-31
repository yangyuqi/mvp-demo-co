package com.wxjz.module_base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by a on 2019/10/30.
 */

public class VideoDetailBean {

    /**
     * aboutList : [{"id":999,"coverUrl":"http://vod.k12c.com/a0944c0d37e841308859ce0700d51426/snapshots/62006ade55724dc09797926c9af33ad7-00005.jpg","videoId":"a0944c0d37e841308859ce0700d51426","subId":1,"videoTitle":"单韵母（2） i、u、ü","videoDesc":"<p>一、学习指南<\/p><p>1.课题名称：<\/p><p>拼音：单韵母（2）i u ü<\/p><p>2.达成目标：<\/p><p>通过观看教学视频和完成《微课学习任务单》规定的任务后：<\/p><p>（1）通过观看教学视频，能初步学会复韵母i u ü的读音、字形以及四声的朗读。<\/p><p>（2）通过观看教学视频，培养学生学习拼音的兴趣，享受学习过程的愉悦。<\/p><p>3.学习方法建议：边观看边朗读。<\/p><p>4.课堂学习形式预告：<\/p><p>基于课前微课学习，课堂上将：<\/p><p>(1)出示复韵母，学习读音。<\/p><p>(2)介绍字形。<\/p><p>(3)视频带读四声，学生跟读。<\/p><p>二、学习任务<\/p><p>通过观看教学录像自学，完成下列学习任务：<\/p><p>1.能初步学会复韵母i u ü的读音、字形以及四声的朗读。<\/p><p>2. 培养学生学习拼音的兴趣，享受学习过程的愉悦。<\/p>","clickCount":77,"free":false,"subjectName":"语文","videoDuration":423,"chapterId":45,"sectionId":54,"sectionName":null,"tipsNum":null,"termsNum":null,"expriseNum":null,"myProgress":0,"shelves":0,"createTime":1573093194000,"videoSize":186160028,"progress":null,"sortId":999,"idList":null,"teacherDesc":"教师介绍暂无","gradeId":"P1","coursewareList":null,"study":false}]
     * detail : {"id":999,"videoTitle":"单韵母（2） i、u、ü","videoDesc":"<p>一、学习指南<\/p><p>1.课题名称：<\/p><p>拼音：单韵母（2）i u ü<\/p><p>2.达成目标：<\/p><p>通过观看教学视频和完成《微课学习任务单》规定的任务后：<\/p><p>（1）通过观看教学视频，能初步学会复韵母i u ü的读音、字形以及四声的朗读。<\/p><p>（2）通过观看教学视频，培养学生学习拼音的兴趣，享受学习过程的愉悦。<\/p><p>3.学习方法建议：边观看边朗读。<\/p><p>4.课堂学习形式预告：<\/p><p>基于课前微课学习，课堂上将：<\/p><p>(1)出示复韵母，学习读音。<\/p><p>(2)介绍字形。<\/p><p>(3)视频带读四声，学生跟读。<\/p><p>二、学习任务<\/p><p>通过观看教学录像自学，完成下列学习任务：<\/p><p>1.能初步学会复韵母i u ü的读音、字形以及四声的朗读。<\/p><p>2. 培养学生学习拼音的兴趣，享受学习过程的愉悦。<\/p>","createTime":null,"updateTime":null,"chapterId":45,"coverUrl":"http://vod.k12c.com/a0944c0d37e841308859ce0700d51426/snapshots/62006ade55724dc09797926c9af33ad7-00005.jpg","isDelete":null,"clickCount":77,"videoDuration":423,"videoSize":186160028,"sortId":999,"videoType":3,"videoId":"a0944c0d37e841308859ce0700d51426","sectionId":54,"subId":1,"teacherDesc":"教师介绍暂无","teacherImgUrl":null,"free":false,"shelves":0,"levelId":1,"subjectName":null,"gradeId":"P1","coursewareList":[{"id":1,"coursewareName":"测试课件pdf","vidoId":999,"showAddress":"http://www.bestudy360.com/CSP/res//mobilecompus/filea800a18f-b3d7-4da8-afba-37a4badbf5a5.pdf","createTime":1574149026000},{"id":2,"coursewareName":"测试课件PPT","vidoId":999,"showAddress":"http://www.bestudy360.com/CSP/res//mobilecompus/filecb0c4e7c-7088-43ed-b355-4d2af9e96f0f.ppt","createTime":1574150312000}]}
     * title : iuü
     */

    private DetailBean detail;
    private String title;
    private List<AboutListBean> aboutList;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AboutListBean> getAboutList() {
        return aboutList;
    }

    public void setAboutList(List<AboutListBean> aboutList) {
        this.aboutList = aboutList;
    }

    public static class DetailBean implements Parcelable {
        /**
         * id : 999
         * videoTitle : 单韵母（2） i、u、ü
         * videoDesc : <p>一、学习指南</p><p>1.课题名称：</p><p>拼音：单韵母（2）i u ü</p><p>2.达成目标：</p><p>通过观看教学视频和完成《微课学习任务单》规定的任务后：</p><p>（1）通过观看教学视频，能初步学会复韵母i u ü的读音、字形以及四声的朗读。</p><p>（2）通过观看教学视频，培养学生学习拼音的兴趣，享受学习过程的愉悦。</p><p>3.学习方法建议：边观看边朗读。</p><p>4.课堂学习形式预告：</p><p>基于课前微课学习，课堂上将：</p><p>(1)出示复韵母，学习读音。</p><p>(2)介绍字形。</p><p>(3)视频带读四声，学生跟读。</p><p>二、学习任务</p><p>通过观看教学录像自学，完成下列学习任务：</p><p>1.能初步学会复韵母i u ü的读音、字形以及四声的朗读。</p><p>2. 培养学生学习拼音的兴趣，享受学习过程的愉悦。</p>
         * createTime : null
         * updateTime : null
         * chapterId : 45
         * coverUrl : http://vod.k12c.com/a0944c0d37e841308859ce0700d51426/snapshots/62006ade55724dc09797926c9af33ad7-00005.jpg
         * isDelete : null
         * clickCount : 77
         * videoDuration : 423
         * videoSize : 186160028
         * sortId : 999
         * videoType : 3
         * videoId : a0944c0d37e841308859ce0700d51426
         * sectionId : 54
         * subId : 1
         * teacherDesc : 教师介绍暂无
         * teacherImgUrl : null
         * free : false
         * shelves : 0
         * levelId : 1
         * subjectName : null
         * gradeId : P1
         * coursewareList : [{"id":1,"coursewareName":"测试课件pdf","vidoId":999,"showAddress":"http://www.bestudy360.com/CSP/res//mobilecompus/filea800a18f-b3d7-4da8-afba-37a4badbf5a5.pdf","createTime":1574149026000},{"id":2,"coursewareName":"测试课件PPT","vidoId":999,"showAddress":"http://www.bestudy360.com/CSP/res//mobilecompus/filecb0c4e7c-7088-43ed-b355-4d2af9e96f0f.ppt","createTime":1574150312000}]
         */

        private int id;
        private String videoTitle;
        private String videoDesc;
        private Object createTime;
        private Object updateTime;
        private int chapterId;
        private String coverUrl;
        private Object isDelete;
        private int clickCount;
        private int videoDuration;
        private int videoSize;
        private int sortId;
        private int videoType;
        private String videoId;
        private int sectionId;
        private int subId;
        private String teacherDesc;
        private String teacherImgUrl;
        private boolean free;
        private int shelves;
        private int levelId;
        private Object subjectName;
        private String gradeId;
        private List<CoursewareListBean> coursewareList;

        protected DetailBean(Parcel in) {
            id = in.readInt();
            videoTitle = in.readString();
            videoDesc = in.readString();
            chapterId = in.readInt();
            coverUrl = in.readString();
            clickCount = in.readInt();
            videoDuration = in.readInt();
            videoSize = in.readInt();
            sortId = in.readInt();
            videoType = in.readInt();
            videoId = in.readString();
            sectionId = in.readInt();
            subId = in.readInt();
            teacherDesc = in.readString();
            teacherImgUrl = in.readString();
            free = in.readByte() != 0;
            shelves = in.readInt();
            levelId = in.readInt();
            gradeId = in.readString();
            coursewareList = in.createTypedArrayList(CoursewareListBean.CREATOR);
        }

        public static final Creator<DetailBean> CREATOR = new Creator<DetailBean>() {
            @Override
            public DetailBean createFromParcel(Parcel in) {
                return new DetailBean(in);
            }

            @Override
            public DetailBean[] newArray(int size) {
                return new DetailBean[size];
            }
        };

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

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public Object getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(Object isDelete) {
            this.isDelete = isDelete;
        }

        public int getClickCount() {
            return clickCount;
        }

        public void setClickCount(int clickCount) {
            this.clickCount = clickCount;
        }

        public int getVideoDuration() {
            return videoDuration;
        }

        public void setVideoDuration(int videoDuration) {
            this.videoDuration = videoDuration;
        }

        public int getVideoSize() {
            return videoSize;
        }

        public void setVideoSize(int videoSize) {
            this.videoSize = videoSize;
        }

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        public int getVideoType() {
            return videoType;
        }

        public void setVideoType(int videoType) {
            this.videoType = videoType;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public int getSectionId() {
            return sectionId;
        }

        public void setSectionId(int sectionId) {
            this.sectionId = sectionId;
        }

        public int getSubId() {
            return subId;
        }

        public void setSubId(int subId) {
            this.subId = subId;
        }

        public String getTeacherDesc() {
            return teacherDesc;
        }

        public void setTeacherDesc(String teacherDesc) {
            this.teacherDesc = teacherDesc;
        }

        public String getTeacherImgUrl() {
            return teacherImgUrl;
        }

        public void setTeacherImgUrl(String teacherImgUrl) {
            this.teacherImgUrl = teacherImgUrl;
        }

        public boolean isFree() {
            return free;
        }

        public void setFree(boolean free) {
            this.free = free;
        }

        public int getShelves() {
            return shelves;
        }

        public void setShelves(int shelves) {
            this.shelves = shelves;
        }

        public int getLevelId() {
            return levelId;
        }

        public void setLevelId(int levelId) {
            this.levelId = levelId;
        }

        public Object getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(Object subjectName) {
            this.subjectName = subjectName;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public List<CoursewareListBean> getCoursewareList() {
            return coursewareList;
        }

        public void setCoursewareList(List<CoursewareListBean> coursewareList) {
            this.coursewareList = coursewareList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(videoTitle);
            dest.writeString(videoDesc);
            dest.writeInt(chapterId);
            dest.writeString(coverUrl);
            dest.writeInt(clickCount);
            dest.writeInt(videoDuration);
            dest.writeInt(videoSize);
            dest.writeInt(sortId);
            dest.writeInt(videoType);
            dest.writeString(videoId);
            dest.writeInt(sectionId);
            dest.writeInt(subId);
            dest.writeString(teacherDesc);
            dest.writeString(teacherImgUrl);
            dest.writeByte((byte) (free ? 1 : 0));
            dest.writeInt(shelves);
            dest.writeInt(levelId);
            dest.writeString(gradeId);
            dest.writeTypedList(coursewareList);
        }

        public static class CoursewareListBean implements Parcelable {
            /**
             * id : 1
             * coursewareName : 测试课件pdf
             * vidoId : 999
             * showAddress : http://www.bestudy360.com/CSP/res//mobilecompus/filea800a18f-b3d7-4da8-afba-37a4badbf5a5.pdf
             * createTime : 1574149026000
             */
            private long fileSize;
            private String fileType;
            private int id;
            private String coursewareName;
            private int vidoId;
            private String showAddress;
            private long createTime;

            public long getFileSize() {
                return fileSize;
            }

            public void setFileSize(long fileSize) {
                this.fileSize = fileSize;
            }

            public String getFileType() {
                return fileType;
            }

            public void setFileType(String fileType) {
                this.fileType = fileType;
            }

            protected CoursewareListBean(Parcel in) {
                id = in.readInt();
                coursewareName = in.readString();
                vidoId = in.readInt();
                showAddress = in.readString();
                createTime = in.readLong();
            }

            public static final Creator<CoursewareListBean> CREATOR = new Creator<CoursewareListBean>() {
                @Override
                public CoursewareListBean createFromParcel(Parcel in) {
                    return new CoursewareListBean(in);
                }

                @Override
                public CoursewareListBean[] newArray(int size) {
                    return new CoursewareListBean[size];
                }
            };

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCoursewareName() {
                return coursewareName;
            }

            public void setCoursewareName(String coursewareName) {
                this.coursewareName = coursewareName;
            }

            public int getVidoId() {
                return vidoId;
            }

            public void setVidoId(int vidoId) {
                this.vidoId = vidoId;
            }

            public String getShowAddress() {
                return showAddress;
            }

            public void setShowAddress(String showAddress) {
                this.showAddress = showAddress;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

                dest.writeInt(id);
                dest.writeString(coursewareName);
                dest.writeInt(vidoId);
                dest.writeString(showAddress);
                dest.writeLong(createTime);
            }
        }
    }

    public static class AboutListBean {
        /**
         * id : 999
         * coverUrl : http://vod.k12c.com/a0944c0d37e841308859ce0700d51426/snapshots/62006ade55724dc09797926c9af33ad7-00005.jpg
         * videoId : a0944c0d37e841308859ce0700d51426
         * subId : 1
         * videoTitle : 单韵母（2） i、u、ü
         * videoDesc : <p>一、学习指南</p><p>1.课题名称：</p><p>拼音：单韵母（2）i u ü</p><p>2.达成目标：</p><p>通过观看教学视频和完成《微课学习任务单》规定的任务后：</p><p>（1）通过观看教学视频，能初步学会复韵母i u ü的读音、字形以及四声的朗读。</p><p>（2）通过观看教学视频，培养学生学习拼音的兴趣，享受学习过程的愉悦。</p><p>3.学习方法建议：边观看边朗读。</p><p>4.课堂学习形式预告：</p><p>基于课前微课学习，课堂上将：</p><p>(1)出示复韵母，学习读音。</p><p>(2)介绍字形。</p><p>(3)视频带读四声，学生跟读。</p><p>二、学习任务</p><p>通过观看教学录像自学，完成下列学习任务：</p><p>1.能初步学会复韵母i u ü的读音、字形以及四声的朗读。</p><p>2. 培养学生学习拼音的兴趣，享受学习过程的愉悦。</p>
         * clickCount : 77
         * free : false
         * subjectName : 语文
         * videoDuration : 423
         * chapterId : 45
         * sectionId : 54
         * sectionName : null
         * tipsNum : null
         * termsNum : null
         * expriseNum : null
         * myProgress : 0
         * shelves : 0
         * createTime : 1573093194000
         * videoSize : 186160028
         * progress : null
         * sortId : 999
         * idList : null
         * teacherDesc : 教师介绍暂无
         * gradeId : P1
         * coursewareList : null
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
        private Object myProgress;
        private int shelves;
        private long createTime;
        private int videoSize;
        private Object progress;
        private int sortId;
        private Object idList;
        private String teacherDesc;
        private String gradeId;
        private Object coursewareList;
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

        public Object getMyProgress() {
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

        public String getTeacherDesc() {
            return teacherDesc;
        }

        public void setTeacherDesc(String teacherDesc) {
            this.teacherDesc = teacherDesc;
        }

        public String getGradeId() {
            return gradeId;
        }

        public void setGradeId(String gradeId) {
            this.gradeId = gradeId;
        }

        public Object getCoursewareList() {
            return coursewareList;
        }

        public void setCoursewareList(Object coursewareList) {
            this.coursewareList = coursewareList;
        }

        public boolean isStudy() {
            return study;
        }

        public void setStudy(boolean study) {
            this.study = study;
        }
    }
}
