package com.wxjz.module_base.db.dao.temp;


import com.wxjz.module_base.db.bean.HistoryBean;
import com.wxjz.module_base.db.bean.VideoPlayHistory;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2019/9/2.
 */

public class VideoPlayHistoryDBDao {
    private static VideoPlayHistoryDBDao historyDBDao;

    public static VideoPlayHistoryDBDao getInstence() {
        if (historyDBDao == null) {
            synchronized (VideoPlayHistoryDBDao.class) {
                if (historyDBDao == null) {
                    historyDBDao = new VideoPlayHistoryDBDao();
                }
            }
        }
        return historyDBDao;
    }

    /**
     * 添加一观看记录
     *
     * @param
     */
    public void addPlayHistory(String vid, long position) {
        //如果有重复的查询 则更新
        List<VideoPlayHistory> historyBeans = DataSupport.where("vid=?", vid).find(VideoPlayHistory.class);

        if (historyBeans.size() > 0) {
            for (VideoPlayHistory historyBean : historyBeans) {
                if (position==0){
                    historyBean.delete();
                }else{
                    historyBean.setPosition(position);
                    historyBean.updateAll("vid=?", vid);
                }

            }
        } else {
            VideoPlayHistory historyBean = new VideoPlayHistory();
            historyBean.setVid(vid);
            historyBean.setPosition(position);
            historyBean.saveThrows();
        }

    }

    /**
     * 删除所有历史记录
     */
    public boolean clearPlayHistory() {
        List<VideoPlayHistory> historyBeans = DataSupport.findAll(VideoPlayHistory.class);
        if (historyBeans == null || historyBeans.size() == 0) {
            return false;
        }
        for (VideoPlayHistory historyBean : historyBeans) {
            historyBean.delete();
        }
        return true;
    }

    /**
     * 根据vid 查观看记录
     *
     * @return
     */
    public VideoPlayHistory querySearchHistory(String vid) {
        //按时间顺序降序
        List<VideoPlayHistory> historyBeans = DataSupport.where("vid=?", vid).find(VideoPlayHistory.class);
        if (historyBeans.size()>0){
            return historyBeans.get(0);

        }else{
            return new VideoPlayHistory();
        }
    }
}
