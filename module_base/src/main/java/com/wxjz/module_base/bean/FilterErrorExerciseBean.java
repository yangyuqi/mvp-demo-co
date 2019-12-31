package com.wxjz.module_base.bean;

import java.util.List;

/**
 * @ClassName FilterErrorExerciseBean
 * @Description 错题筛选的Bean
 * @Author liufang
 * @Date 2019-11-26 17:39
 * @Version 1.0
 */
public class FilterErrorExerciseBean {
    /**
     * {
     * "code": 1,
     * "datas": [
     * {
     * "gradeName": "一年级",
     * "chapterId": [
     * {
     * "chapterName": "汉语拼音",
     * "id": 45,
     * "sectionId": [
     * {
     * "sectionName": "aoe",
     * "id": 53
     * },
     * {
     * "sectionName": "dtnl",
     * "id": 56
     * }
     * ]
     * }
     * ],
     * "id": "P1"
     * },
     * {
     * "gradeName": "二年级",
     * "chapterId": [
     * {
     * "chapterName": "识字1",
     * "id": 65,
     * "sectionId": [
     * {
     * "sectionName": "古诗两首",
     * "id": 96
     * }
     * ]
     * }
     * ],
     * "id": "P2"
     * }
     * ],
     * "message": "筛选查询成功!"
     * }
     */

    private List<chapterBean> chapterId;
    private String gradeName;
    private String id;
    private Boolean isSelect;

    public List<chapterBean> getChapterId() {
        return chapterId;
    }

    public void setChapterId(List<chapterBean> chapterId) {
        this.chapterId = chapterId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public class chapterBean {
        private String chapterName;
        private int id;
        private List<sectionBean> sectionId;
        private boolean isSelect;
        private int fatherPosition;

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<sectionBean> getSectionId() {
            return sectionId;
        }

        public void setSectionId(List<sectionBean> sectionId) {
            this.sectionId = sectionId;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getFatherPosition() {
            return fatherPosition;
        }

        public void setFatherPosition(int fatherPosition) {
            this.fatherPosition = fatherPosition;
        }
    }

    public class sectionBean {
        private int id;
        private String sectionName;
        private int firstFatherPosition;
        private int secondFatherPosition;
        private boolean isSelect;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public int getFirstFatherPosition() {
            return firstFatherPosition;
        }

        public void setFirstFatherPosition(int firstFatherPosition) {
            this.firstFatherPosition = firstFatherPosition;
        }

        public int getSecondFatherPosition() {
            return secondFatherPosition;
        }

        public void setSecondFatherPosition(int secondFatherPosition) {
            this.secondFatherPosition = secondFatherPosition;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
