package com.wxjz.module_base.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.List;

/**
 * Created by a on 2019/9/16.
 */

public class BrannerBean {


    private List<BannerListBean> bannerList;

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public static class BannerListBean extends SimpleBannerInfo {
        /**
         * subject_id : 1
         * module_id_s : null
         * course_id : 997
         * sch_id : null
         * create_time : 1570677180000
         * banner_id : ad7d251c7a6647bb81a18717a721cde8
         * banner_name : 已已i
         * sort_code : 4
         * title : null
         * school_section : 3
         * update_time : 1570677180000
         * level_name : 全部
         * section_id : 53
         * banner_type : 3
         * is_link : null
         * rich_text : <p>adadfa</p>
         * link_url :
         * state : 0
         * chapter_id : 45
         * pic_url : http://www.bestudy360.com/CSP/res//mobilecompus/file2ee76c70-bdc2-4373-9f2e-d60934f77770.png
         * module_id_p : null
         * knowledge_id : null
         */

        private String subject_id;
        private Object module_id_s;
        private String course_id;
        private Object sch_id;
        private long create_time;
        private String banner_id;
        private String banner_name;
        private int sort_code;
        private String title;
        private String school_section;
        private long update_time;
        private String level_name;
        private String section_id;
        private int banner_type;
        private Object is_link;
        private String rich_text;
        private String link_url;
        private int state;
        private String chapter_id;
        private String pic_url;
        private Object module_id_p;
        private Object knowledge_id;

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public Object getModule_id_s() {
            return module_id_s;
        }

        public void setModule_id_s(Object module_id_s) {
            this.module_id_s = module_id_s;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public Object getSch_id() {
            return sch_id;
        }

        public void setSch_id(Object sch_id) {
            this.sch_id = sch_id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getBanner_id() {
            return banner_id;
        }

        public void setBanner_id(String banner_id) {
            this.banner_id = banner_id;
        }

        public String getBanner_name() {
            return banner_name;
        }

        public void setBanner_name(String banner_name) {
            this.banner_name = banner_name;
        }

        public int getSort_code() {
            return sort_code;
        }

        public void setSort_code(int sort_code) {
            this.sort_code = sort_code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSchool_section() {
            return school_section;
        }

        public void setSchool_section(String school_section) {
            this.school_section = school_section;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getSection_id() {
            return section_id;
        }

        public void setSection_id(String section_id) {
            this.section_id = section_id;
        }

        public int getBanner_type() {
            return banner_type;
        }

        public void setBanner_type(int banner_type) {
            this.banner_type = banner_type;
        }

        public Object getIs_link() {
            return is_link;
        }

        public void setIs_link(Object is_link) {
            this.is_link = is_link;
        }

        public String getRich_text() {
            return rich_text;
        }

        public void setRich_text(String rich_text) {
            this.rich_text = rich_text;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public Object getModule_id_p() {
            return module_id_p;
        }

        public void setModule_id_p(Object module_id_p) {
            this.module_id_p = module_id_p;
        }

        public Object getKnowledge_id() {
            return knowledge_id;
        }

        public void setKnowledge_id(Object knowledge_id) {
            this.knowledge_id = knowledge_id;
        }

        @Override
        public Object getXBannerUrl() {
            return getPic_url();
        }
    }
}
