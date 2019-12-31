package com.wxjz.module_base.bean;

import java.util.List;

/**
 * @ClassName LevelListBean
 * @Description 游客模式下或者登陆模式下获取学习阶段和年级
 * @Author liufang
 * @Date 2019-10-29 16:02
 * @Version 1.0
 */
public class LevelListBean {

    /**
     * 学习阶段的主键ID
     */
    private int id;
    /**
     * 学习阶段返回的名称
     */
    private String levelName;


    private List<GradeBean> gradeList;

    public class GradeBean {
        /**
         * 年级的主键id
         */
        private String id;
        /**
         * 年级的名称
         */
        private String gradeName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public List<GradeBean> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<GradeBean> gradeList) {
        this.gradeList = gradeList;
    }
}
