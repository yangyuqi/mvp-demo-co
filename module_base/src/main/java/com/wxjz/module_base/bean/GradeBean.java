package com.wxjz.module_base.bean;

/**
 * @ClassName GradeBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-10-28 15:02
 * @Version 1.0
 */
public class GradeBean {
    //年级
    private String grade;
    //是否是选择了
    private boolean isCheck;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
