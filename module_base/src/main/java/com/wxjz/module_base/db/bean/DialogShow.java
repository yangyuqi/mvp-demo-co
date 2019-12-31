package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName DialogShow
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-30 16:54
 * @Version 1.0
 */
public class DialogShow extends LitePalSupport {
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
