package com.wxjz.module_base.db.bean;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/8/10
 * Time: 9:37
 */
public class QuestionAnalysisBean extends BaseBean {
    private String title;

    private String content;//提示内容

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
