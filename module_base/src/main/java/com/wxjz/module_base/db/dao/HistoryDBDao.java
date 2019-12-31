package com.wxjz.module_base.db.dao;


import com.wxjz.module_base.db.bean.HistoryBean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/2.
 */

public class HistoryDBDao {
    private static HistoryDBDao historyDBDao;

    public static HistoryDBDao getInstence() {
        if (historyDBDao == null) {
            synchronized (HistoryDBDao.class) {
                if (historyDBDao == null) {
                    historyDBDao = new HistoryDBDao();
                }
            }
        }
        return historyDBDao;
    }

    /**
     * 添加一条历史记录
     *
     * @param searchHistory
     */
    public void addSearchHistory(String searchHistory) {
        //如果有重复的查询 则更新
        List<HistoryBean> historyBeans = DataSupport.where("history=?", searchHistory).find(HistoryBean.class);

        if (historyBeans.size() > 0) {
            for (HistoryBean historyBean : historyBeans) {
                historyBean.setTime_stamp(System.currentTimeMillis());
                historyBean.setHistory(searchHistory);
                historyBean.saveThrows();
            }
        } else {
            HistoryBean historyBean = new HistoryBean();
            historyBean.setHistory(searchHistory);
            historyBean.setTime_stamp(System.currentTimeMillis());
            historyBean.saveThrows();
        }

    }

    /**
     * 删除所有历史记录
     */
    public boolean clearSearchHistory() {
        List<HistoryBean> historyBeans = DataSupport.findAll(HistoryBean.class);
        if (historyBeans == null || historyBeans.size() == 0) {
            return false;
        }
        for (HistoryBean historyBean : historyBeans) {
            historyBean.delete();
        }
        return true;
    }

    /**
     * 查找所有的历史记录,最多查询5条
     *
     * @return
     */
    public List<HistoryBean> querySearchHistory() {
        //按时间顺序降序
        List<HistoryBean> historyBeans = DataSupport.order("time_stamp desc").find(HistoryBean.class);
        //List<HistoryBean> historyBeans = DataSupport.limit(5).find(HistoryBean.class);
        if (historyBeans != null) {
            return historyBeans;
        }
        return new ArrayList<HistoryBean>();
    }
}
