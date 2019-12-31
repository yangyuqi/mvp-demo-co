package com.wxjz.module_base.db.dao.temp;


import com.google.gson.Gson;
import com.wxjz.module_base.db.bean.AnalysisBean;
import com.wxjz.module_base.db.bean.ChoiceQuestion;
import com.wxjz.module_base.db.bean.ExerciseBean;
import com.wxjz.module_base.db.bean.NotesBean;
import com.wxjz.module_base.db.bean.TipsBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 生成数据
 * Created by liyutao on 2019/8/1.
 */

public class DataGenerator {
    /**
     * 数据的等级
     */
    public static final int DIALOG_LEVEL_NORMAL = -1;
    //优先级
    public static final int DIALOG_LEVEL_ONE = 1;
    public static final int DIALOG_LEVEL_TWO = 2;
    public static final int DIALOG_LEVEL_THREE = 3;
    //笔记
    public static final int DIALOG_TYPE_NOTES = 3;
    //提示
    public static final int DIALOG_TYPE_TIPS = 0;
    //题目
    public static final int DIALOG_TYPE_EXERCISE = 2;
    //术语提示
    public static final int DIALOG_TYPE_TERMINOLOGY = 1;


    private volatile static DataGenerator singleton;

    private DataGenerator() {
    }

    public static DataGenerator getInstance() {
        if (singleton == null) {
            synchronized (DataGenerator.class) {
                if (singleton == null) {
                    singleton = new DataGenerator();
                }
            }
        }
        return singleton;
    }

    /**
     * 生成提示
     */
    public void generatorTips() {
        Date date = new Date();
        TipsBean tipsBean = new TipsBean();
        tipsBean.setContent("这是提示内容，F=ax+b,这是必考点，请注意");
        tipsBean.setProgress("02:11");
        tipsBean.setViedoCurrent(131000);
        tipsBean.setCreatTime(date.getTime());
        tipsBean.setViewType(DIALOG_LEVEL_TWO);
        tipsBean.setDataType(DIALOG_TYPE_TIPS);
        tipsBean.setVideoprogress(0.5);
        tipsBean.save();
    }

    /**
     * 生成知识点解析
     */
    public void generatorAnalysis() {
        Date date = new Date();
        AnalysisBean analysisBean = new AnalysisBean();
        analysisBean.setTitle("这是提示内容，F=ax+b,这是必考点，请注意");
        analysisBean.setContent("这是提示内容，F=ax+b,这是必考点，请注意");
        analysisBean.setProgress("01:45");
        analysisBean.setViewId("1");
        analysisBean.setViedoCurrent(104800);
        analysisBean.setCreatTime(date.getTime());
        analysisBean.setViewType(DIALOG_LEVEL_TWO);
        analysisBean.setDataType(DIALOG_TYPE_TERMINOLOGY);
        analysisBean.setVideoprogress(0.4);
        analysisBean.save();
    }

    /**
     * 生成题目
     */
    public void generatorExercise() {
        Date date = new Date();
        ExerciseBean exerciseBean = new ExerciseBean();
        exerciseBean.setTitle("问题1，以下说法正确的是？");
        List<ChoiceQuestion> listexerciseBean = new ArrayList<>();
        listexerciseBean.add(new ChoiceQuestion("A", "这是提示内容，F=ax+b,这是必考点，请注意"));
        listexerciseBean.add(new ChoiceQuestion("B", "这是提示内容，F=ax+b,这是必考点，请注意"));
        listexerciseBean.add(new ChoiceQuestion("C", "这是提示内容，F=ax+b,这是必考点，请注意"));
        listexerciseBean.add(new ChoiceQuestion("D", "这是提示内容，F=ax+b,这是必考点，请注意"));
        exerciseBean.setChoice(new Gson().toJson(listexerciseBean));
        exerciseBean.setUrl("https://dpic.tiankong.com/bl/sm/QJ6308594262.jpg?x-oss-process=style/shows");
        exerciseBean.setAnswer(new Gson().toJson(new ChoiceQuestion("A", "这是提示内容，F=ax+b,这是必考点，请注意")));
        exerciseBean.setViewId("1");
        exerciseBean.setProgress("00:52");
        exerciseBean.setViedoCurrent(52400);
        exerciseBean.setIsAnswer(true);
        exerciseBean.setCreatTime(date.getTime());
        exerciseBean.setViewType(DIALOG_LEVEL_THREE);
        exerciseBean.setDataType(DIALOG_TYPE_EXERCISE);
        exerciseBean.setVideoprogress(0.2);
        exerciseBean.save();

        ExerciseBean exerciseBean2 = new ExerciseBean();
        exerciseBean2.setTitle("问题2，以下说法正确的是？");
        List<ChoiceQuestion> listexerciseBean2 = new ArrayList<>();
        listexerciseBean2.add(new ChoiceQuestion("A", "这是提示内容，F=ax+b,这是必考点，请注意"));
        listexerciseBean2.add(new ChoiceQuestion("B", "这是提示内容，F=ax+b,这是必考点，请注意"));
        listexerciseBean2.add(new ChoiceQuestion("C", "这是提示内容，F=ax+b,这是必考点，请注意"));
        listexerciseBean2.add(new ChoiceQuestion("D", "这是提示内容，F=ax+b,这是必考点，请注意"));
        exerciseBean2.setChoice(new Gson().toJson(listexerciseBean2));
        exerciseBean2.setAnswer(new Gson().toJson(new ChoiceQuestion("A", "这是提示内容，F=ax+b,这是必考点，请注意")));
        exerciseBean2.setViewId("1");
        exerciseBean2.setProgress("02:37");
        exerciseBean2.setViedoCurrent(157200);
        exerciseBean2.setIsAnswer(false);
        exerciseBean2.setCreatTime(date.getTime() + 10);
        exerciseBean2.setViewType(DIALOG_LEVEL_THREE);
        exerciseBean2.setDataType(DIALOG_TYPE_EXERCISE);
        exerciseBean2.setVideoprogress(0.6);
        exerciseBean2.save();
    }

    /**
     * 生成笔记
     */
    public boolean generatorNotes() {
        Date date = new Date();
        NotesBean notesBean = new NotesBean();
        notesBean.setContent("这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容这是内容");
        notesBean.setProgress("03:29");
        notesBean.setViedoCurrent(209600);
        notesBean.setCreatTime(date.getTime());
        notesBean.setViewType(DIALOG_LEVEL_ONE);
        notesBean.setDataType(DIALOG_TYPE_NOTES);
        notesBean.setVideoprogress(0.8);
        return notesBean.save();
    }
}
