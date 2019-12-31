package com.wxjz.module_base.db.bean;

/**
 * Created by liyutao on 2019/8/9.
 */

public class ChoiceQuestion
{
    private String tag;//选项

    private String option;//内容

    public ChoiceQuestion(String tag, String option) {
        this.tag = tag;
        this.option = option;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
