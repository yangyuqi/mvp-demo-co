package com.wxjz.module_base.db.bean;

/**
 * 课堂练习
 * Created by liyutao on 2019/7/31.
 */

public class ExerciseBean extends BaseBean {
    private String title;//题目

    private String choice;

    private String answer;

    private String url;

    private boolean isAnswer;

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


    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(boolean answer) {
        isAnswer = answer;
    }
}
