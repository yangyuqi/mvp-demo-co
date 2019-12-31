package com.wxjz.module_base.db.dao;


import com.wxjz.module_base.db.bean.ClassifyBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by a on 2019/9/3.
 */

public class ClassifyDao {
    private static ClassifyDao classifyDao;

    public static ClassifyDao getInstence() {
        if (classifyDao == null) {
            synchronized (ClassifyDao.class) {
                if (classifyDao == null) {
                    classifyDao = new ClassifyDao();
                }
            }
        }
        return classifyDao;
    }

    /**
     * 更新／添加 数据  只保存一个值，有则删除
     *
     * @param checkId
     */
    public void updateCheckId(int checkId) {
        List<ClassifyBean> beanList = DataSupport.findAll(ClassifyBean.class);
        if (beanList.size() > 0) {
            for (ClassifyBean bean : beanList) {
                bean.delete();
            }
        }
        ClassifyBean classifyBean = new ClassifyBean();
        classifyBean.setChecked_id(String.valueOf(checkId));
        classifyBean.saveThrows();


    }

    public int queryCurrentCheckId() {
        List<ClassifyBean> list = DataSupport.findAll(ClassifyBean.class);
        if (list.size() > 0) {
            String checked_id = list.get(0).getChecked_id();
            return Integer.valueOf(checked_id);
        } else {
            return 1;
        }

    }

    public void clear(){
        DataSupport.deleteAll(ClassifyBean.class);
    }
}
