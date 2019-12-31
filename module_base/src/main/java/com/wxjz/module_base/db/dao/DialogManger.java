package com.wxjz.module_base.db.dao;


import android.content.Context;
import android.util.Log;

import com.wxjz.module_base.db.bean.AnalysisBean;
import com.wxjz.module_base.db.bean.BaseBean;
import com.wxjz.module_base.db.bean.ExerciseDBBean;
import com.wxjz.module_base.db.bean.NotesBean;
import com.wxjz.module_base.db.bean.TipsBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName DialogManger
 * @Description TODO
 * @Author liufang
 * @Date 2019-08-30 09:31
 * @Version 1.0
 */
public class DialogManger {

    private volatile static DialogManger singleton;

    private List<BaseBean> list = new ArrayList<>();

    private DialogManger() {

    }

    public static DialogManger getInstance(Context context) {
        if (singleton == null) {
            synchronized (DialogManger.class) {
                if (singleton == null) {
                    singleton = new DialogManger();
                }
            }
        }
        return singleton;
    }

    /**
     * 获取数据库里面所有的笔记，术语，题目，提示，答题的所有内容
     */
    public List<BaseBean> getAllData() {
        if (list != null) {
            list.clear();
            getNotes();
            getTips();
            getAnswer();
            getterminology();
            sortdatalist();
            Log.d("LF123", "" + list);
            return list;
        }
        return null;
    }


    /**
     * 获取笔记
     */
    public List<NotesBean> getNotes() {
        List<NotesBean> datalist = OwnStudyDBDao.getInstance().findAllNotes();
        for (NotesBean b : datalist) {
            list.add(b);
        }
        return datalist;
    }

    /**
     * 获取提示
     */
    public List<TipsBean> getTips() {
        List<TipsBean> tipsBeanList = OwnStudyDBDao.getInstance().findAllTips();
        for (TipsBean b : tipsBeanList) {
            list.add(b);
        }
        return tipsBeanList;
    }

    /**
     * 获取题目
     */
    public List<ExerciseDBBean> getAnswer() {
        List<ExerciseDBBean> exerciseBeanList = OwnStudyDBDao.getInstance().findAllExercise();
        for (ExerciseDBBean b : exerciseBeanList) {
            list.add(b);
        }
        return exerciseBeanList;
    }

    /**
     * 获取术语
     */
    public List<AnalysisBean> getterminology() {
        List<AnalysisBean> analysisBeans = OwnStudyDBDao.getInstance().findAllAnalysis();
        for (AnalysisBean b : analysisBeans) {
            list.add(b);
        }
        return analysisBeans;
    }

    /**
     * 对获取的数据进行排序
     */
    private void sortdatalist() {
        Collections.sort(list, new Comparator<BaseBean>() {
            @Override
            public int compare(BaseBean baseBean, BaseBean t1) {
                int progress = (int) (baseBean.getViedoCurrent() - t1.getViedoCurrent());
                if (progress == 0) {
                    return baseBean.getViewType() - t1.getViewType();
                }
                return progress;
            }
        });
    }

}
