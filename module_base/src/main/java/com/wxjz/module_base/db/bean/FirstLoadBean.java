package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName FirstLoadBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-18 13:52
 * @Version 1.0
 */
public class FirstLoadBean extends LitePalSupport {

    private String FirstLoad;

    public String getFirstLoad() {
        return FirstLoad;
    }

    public void setFirstLoad(String firstLoad) {
        FirstLoad = firstLoad;
    }
}
