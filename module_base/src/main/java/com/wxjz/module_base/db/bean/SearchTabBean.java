package com.wxjz.module_base.db.bean;

/**
 * Created by a on 2019/9/10.
 */

public class SearchTabBean {

    /**
     * id : 4
     * subjectName : 语文
     * stuLevelId : 2
     * bgcColor : FDCF00
     */

    private int id;
    private String subjectName;
    private int stuLevelId;
    private String bgcColor;

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

    public int getStuLevelId() {
        return stuLevelId;
    }

    public void setStuLevelId(int stuLevelId) {
        this.stuLevelId = stuLevelId;
    }

    public String getBgcColor() {
        return bgcColor;
    }

    public void setBgcColor(String bgcColor) {
        this.bgcColor = bgcColor;
    }
}
