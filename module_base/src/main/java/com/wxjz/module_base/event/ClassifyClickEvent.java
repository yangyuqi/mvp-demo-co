package com.wxjz.module_base.event;

/**
 * Created by a on 2019/9/3.
 */

public class ClassifyClickEvent {
    public ClassifyClickEvent(int clickButton){
        this.clickButton = clickButton;
    }

    /**
     * 1小学 2初中
     */
    private int clickButton;

    public int getClickButton() {
        return clickButton;
    }

    public void setClickButton(int clickButton) {
        this.clickButton = clickButton;
    }
}
