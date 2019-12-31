package com.wxjz.module_base.db.bean;


/**
 * @ClassName SchoolItemBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-05 15:39
 * @Version 1.0
 */
public class SchoolItemBean extends BaseBean {
    private int schoolId;
    private String name;
    private String letters;//显示拼音的首字母
    private String iconUrl;//学校logo图片链
    private String lxdh;//显示联系电话

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }
}
