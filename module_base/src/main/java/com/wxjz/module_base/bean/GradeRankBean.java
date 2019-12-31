package com.wxjz.module_base.bean;

/**
 * Created by a on 2019/9/17.
 */

public class GradeRankBean {

    /**
     * courseCount : 11
     * learingRealTime : null
     * userId : null
     * userName : null
     * xqhid : 234
     * graid : 2231275
     * claid : 1600000000
     * gradeName : 高中三年级
     */
    private String className;
    private int courseCount;
    private int learingRealTime;
    private Object userId;
    private Object userName;
    private int xqhid;
    private int graid;
    private int claid;
    private String gradeName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public int getLearingRealTime() {
        return learingRealTime;
    }

    public void setLearingRealTime(int learingRealTime) {
        this.learingRealTime = learingRealTime;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public int getXqhid() {
        return xqhid;
    }

    public void setXqhid(int xqhid) {
        this.xqhid = xqhid;
    }

    public int getGraid() {
        return graid;
    }

    public void setGraid(int graid) {
        this.graid = graid;
    }

    public int getClaid() {
        return claid;
    }

    public void setClaid(int claid) {
        this.claid = claid;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
