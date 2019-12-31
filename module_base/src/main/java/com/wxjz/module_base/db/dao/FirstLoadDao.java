package com.wxjz.module_base.db.dao;

import com.wxjz.module_base.db.bean.FirstLoadBean;

import org.litepal.LitePal;

/**
 * @ClassName FirstLoadDao
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-18 13:55
 * @Version 1.0
 */
public class FirstLoadDao {
    private static FirstLoadDao findFirst;

    public static FirstLoadDao getInstance() {
        if (findFirst == null) {
            synchronized (FirstLoadDao.class) {
                if (findFirst == null) {
                    findFirst = new FirstLoadDao();
                }
            }
        }
        return findFirst;
    }

    public void addFirstLoad(String text) {
        FirstLoadBean firstLoadBean = LitePal.findFirst(FirstLoadBean.class);
        if (firstLoadBean != null) {
            firstLoadBean.delete();
        }
        FirstLoadBean dialogShow1 = new FirstLoadBean();
        dialogShow1.setFirstLoad(text);
        dialogShow1.save();
    }


    public String querrIsFirstload() {
        String load = "";
        FirstLoadBean show = LitePal.findFirst(FirstLoadBean.class);
        if (show != null) {
            load = show.getFirstLoad();
        }
        return load;
    }
}
