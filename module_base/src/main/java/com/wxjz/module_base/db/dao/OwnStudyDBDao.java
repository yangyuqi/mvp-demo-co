package com.wxjz.module_base.db.dao;

import com.google.gson.Gson;
import com.wxjz.module_base.db.bean.AnalysisBean;
import com.wxjz.module_base.db.bean.ChoiceQuestion;
import com.wxjz.module_base.db.bean.ExerciseBean;
import com.wxjz.module_base.db.bean.ExerciseDBBean;
import com.wxjz.module_base.db.bean.NotesBean;
import com.wxjz.module_base.db.bean.SchoolItemBean;
import com.wxjz.module_base.db.bean.TipsBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liyutao on 2019/8/1.
 */

public class OwnStudyDBDao {

    private volatile static OwnStudyDBDao singleton;

    private OwnStudyDBDao() {
    }

    public static OwnStudyDBDao getInstance() {
        if (singleton == null) {
            synchronized (OwnStudyDBDao.class) {
                if (singleton == null) {
                    singleton = new OwnStudyDBDao();
                }
            }
        }
        return singleton;
    }

    public boolean saveNotes(NotesBean bean) {
        return bean.save();
    }

    public boolean saveExercise(ExerciseBean bean) {
        return bean.save();
    }

    /**
     * 根据播放进度查找
     *
     * @param progress
     * @return
     */
    public List<NotesBean> findNotesByProgress(String progress) {
        List<NotesBean> list = LitePal.where("progress = ? ", progress).find(NotesBean.class);
        return list;
    }

    /**
     * 根据播放进度查找
     *
     * @param progress
     * @return
     */
    public List<ExerciseBean> findExerciseByProgress(String progress) {
        List<ExerciseBean> list = LitePal.where("progress = ? ", progress).find(ExerciseBean.class);
        return list;
    }

    /**
     * 查找所有笔记
     *
     * @return
     */
    public List<NotesBean> findAllNotes() {
        List<NotesBean> list = LitePal.findAll(NotesBean.class);
        return list;
    }

    /***
     * 查找笔记按照降序排列
     */
    public List<NotesBean> findAllNotesDesc() {
        List<NotesBean> list = LitePal.order("creatTime DESC").find(NotesBean.class);
        return list;
    }

    /**
     * 查找所有提示
     */
    public List<TipsBean> findAllTips() {
        List<TipsBean> list = LitePal.findAll(TipsBean.class);
        return list;
    }

    /**
     * 查找所有知识点解析
     */
    public List<AnalysisBean> findAllAnalysis() {
        List<AnalysisBean> list = LitePal.findAll(AnalysisBean.class);
        return list;
    }

    /**
     * 查找所有练习
     *
     * @return
     */
    public List<ExerciseDBBean> findAllExercise() {
        List<ExerciseBean> list = LitePal.findAll(ExerciseBean.class);
        List<ExerciseDBBean> exerciseDBBeanList = new ArrayList<>();
        if (exerciseDBBeanList != null) {
            for (ExerciseBean bean : list) {
                ExerciseDBBean exerciseDBBean = new ExerciseDBBean();
                exerciseDBBean.setViewId(bean.getViewId());
                exerciseDBBean.setTitle(bean.getTitle());
                exerciseDBBean.setUrl(bean.getUrl());
                exerciseDBBean.setViedoCurrent(bean.getViedoCurrent());
                exerciseDBBean.setProgress(bean.getProgress());
                exerciseDBBean.setViewType(bean.getViewType());
                ChoiceQuestion[] choice = new Gson().fromJson(bean.getChoice(), ChoiceQuestion[].class);
                exerciseDBBean.setChoice(Arrays.asList(choice));
                exerciseDBBean.setAnswer(new Gson().fromJson(bean.getAnswer(), ChoiceQuestion.class));
                exerciseDBBean.setDataType(bean.getDataType());
                exerciseDBBean.setVideoprogress(bean.getVideoprogress());
                exerciseDBBeanList.add(exerciseDBBean);
            }
        }
        return exerciseDBBeanList;
    }

    public List<SchoolItemBean> findAllSchool() {
        List<SchoolItemBean> list = LitePal.findAll(SchoolItemBean.class);
        return list;
    }

    public void deleteAllSchool() {
        LitePal.deleteAll(SchoolItemBean.class);
    }

}