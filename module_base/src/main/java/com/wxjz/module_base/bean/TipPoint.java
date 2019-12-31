package com.wxjz.module_base.bean;

/**
 * Created by a on 2019/8/12.
 */

public class TipPoint {
    /**
     * 点所在seekbar的位置
     */
    private double progress;
    /**
     * 点的类型
     */
    private int type;

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
