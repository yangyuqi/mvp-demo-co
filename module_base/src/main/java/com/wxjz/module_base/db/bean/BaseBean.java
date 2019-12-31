package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 基础类
 * Created by liyutao on 2019/7/31.
 */

public class BaseBean extends LitePalSupport {

    private String viewId;  //视频id

    private String userId; //登录用户id
    /**
     * 创建时间
     */
    private long creatTime;

    /**
     * 内容的优先级，默认为0
     */
    private int viewType;
    /**
     * 数据类型
     */
    private int dataType;

    /**
     * 数据在视频的进度
     */
    private long viedoCurrent;
    /**
     * //转换后的进度
     */
    private String progress;

    /**
     * 进度占比
     */
    private double videoprogress;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public long getViedoCurrent() {
        return viedoCurrent;
    }

    public void setViedoCurrent(long viedoCurrent) {
        this.viedoCurrent = viedoCurrent;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public double getVideoprogress() {
        return videoprogress;
    }

    public void setVideoprogress(double videoprogress) {
        this.videoprogress = videoprogress;
    }
}
