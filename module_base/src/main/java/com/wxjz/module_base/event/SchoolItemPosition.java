package com.wxjz.module_base.event;

/**
 * @ClassName SchoolItemPosition
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-12 11:01
 * @Version 1.0
 */
public class SchoolItemPosition {
    private int schoolId;

    private String schoolName;

    public SchoolItemPosition(int position, String schoolName) {
        this.schoolId = position;
        this.schoolName = schoolName;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
