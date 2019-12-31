package com.wxjz.module_base.db.bean;

/**
 * 术语
 * Created by liyutao on 2019/7/31.
 */

public class AnalysisBean extends BaseBean {

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
