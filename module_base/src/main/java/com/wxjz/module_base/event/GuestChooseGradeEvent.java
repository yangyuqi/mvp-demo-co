package com.wxjz.module_base.event;

/**
 * @ClassName EventGuestChooseGradeEvent
 * @Description TODO
 * @Author liufang
 * @Date 2019-10-30 14:07
 * @Version 1.0
 */
public class GuestChooseGradeEvent {

    private int leveld;
    private String leveName;
    private String gradeId;
    private String gradeName;

    public GuestChooseGradeEvent(int leveld, String leveName, String gradeId, String gradeName) {
        this.leveld = leveld;
        this.leveName = leveName;
        this.gradeId = gradeId;
        this.gradeName = gradeName;
    }

    public int getLeveld() {
        return leveld;
    }

    public void setLeveld(int leveld) {
        this.leveld = leveld;
    }

    public String getLeveName() {
        return leveName;
    }

    public void setLeveName(String leveName) {
        this.leveName = leveName;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
}
