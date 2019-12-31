package com.wxjz.module_base.db.bean;

import java.util.List;
import java.util.Observable;

/**
 * @ClassName SubjectHomeBean
 * @Description 单科首页的bean
 * @Author liufang
 * @Date 2019-11-01 08:25
 * @Version 1.0
 */
public class SubjectHomeBean {


    private List<SubjectSectionBean> rightVideoList;

    private List<NavBean> navList;


    public List<SubjectSectionBean> getRightVideoList() {
        return rightVideoList;
    }

    public void setRightVideoList(List<SubjectSectionBean> rightVideoList) {
        this.rightVideoList = rightVideoList;
    }

    public List<NavBean> getNavList() {
        return navList;
    }

    public void setNavList(List<NavBean> navList) {
        this.navList = navList;
    }

    /**
     * "id": 6,
     * "chapterName": "打",
     * "subId": 1,
     * "levelId": 1,
     * "gradeId": null,
     * "sectionList": []
     */
    public class NavBean {
        private Object id;
        private Object chapterName;
        private Object subId;
        private Object levelId;
        private Object gradeId;
        /**
         * 是否是展开
         */
        private boolean expand;
        /**
         * 二级控件是否有被选择的，选择的位置
         */
        private int ChildSelect;
        /**
         * 一级控件是否有被选中，选中的位置
         */
        private int GroupSelect;


        private List<SectionBean> sectionList;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getChapterName() {
            return chapterName;
        }

        public void setChapterName(Object chapterName) {
            this.chapterName = chapterName;
        }

        public Object getSubId() {
            return subId;
        }

        public void setSubId(Object subId) {
            this.subId = subId;
        }

        public Object getLevelId() {
            return levelId;
        }

        public void setLevelId(Object levelId) {
            this.levelId = levelId;
        }

        public Object getGradeId() {
            return gradeId;
        }

        public void setGradeId(Object gradeId) {
            this.gradeId = gradeId;
        }

        public List<SectionBean> getSectionList() {
            return sectionList;
        }

        public void setSectionList(List<SectionBean> sectionList) {
            this.sectionList = sectionList;
        }

        public boolean isExpand() {
            return expand;
        }

        public void setExpand(boolean expand) {
            this.expand = expand;
        }

        public int getChildSelect() {
            return ChildSelect;
        }

        public void setChildSelect(int childSelect) {
            ChildSelect = childSelect;
        }

        public int getGroupSelect() {
            return GroupSelect;
        }

        public void setGroupSelect(int groupSelect) {
            GroupSelect = groupSelect;
        }
    }


    /**
     * "id": 20,
     * "sectionName": "信息",
     * "subId": null,
     * "levelId": null,
     * "chapterId": null,
     * "gradeId": null,
     * "videoModelList": null
     */
    public class SectionBean {

        private Object id;
        private Object sectionName;
        private Object subId;
        private Object levelId;
        private Object chapterId;
        private Object gradeId;
        private Object videoModelList;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getSectionName() {
            return sectionName;
        }

        public void setSectionName(Object sectionName) {
            this.sectionName = sectionName;
        }

        public Object getSubId() {
            return subId;
        }

        public void setSubId(Object subId) {
            this.subId = subId;
        }

        public Object getLevelId() {
            return levelId;
        }

        public void setLevelId(Object levelId) {
            this.levelId = levelId;
        }

        public Object getChapterId() {
            return chapterId;
        }

        public void setChapterId(Object chapterId) {
            this.chapterId = chapterId;
        }

        public Object getGradeId() {
            return gradeId;
        }

        public void setGradeId(Object gradeId) {
            this.gradeId = gradeId;
        }

        public Object getVideoModelList() {
            return videoModelList;
        }

        public void setVideoModelList(Object videoModelList) {
            this.videoModelList = videoModelList;
        }
    }
}
