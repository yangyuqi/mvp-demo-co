package com.wxjz.module_base.db.bean;

import java.util.List;

/**
 * 课堂练习
 * Created by liyutao on 2019/7/31.
 */

public class ExerciseDBBean extends BaseBean
{
    private String title;//题目

    private List<ChoiceQuestion> choice;

    private ChoiceQuestion answer;

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ChoiceQuestion> getChoice() {
        return choice;
    }

    public void setChoice(List<ChoiceQuestion> choice) {
        this.choice = choice;
    }

    public ChoiceQuestion getAnswer() {
        return answer;
    }

    public void setAnswer(ChoiceQuestion answer) {
        this.answer = answer;
    }
}
