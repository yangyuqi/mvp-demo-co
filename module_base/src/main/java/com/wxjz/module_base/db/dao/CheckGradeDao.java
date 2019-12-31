package com.wxjz.module_base.db.dao;

import android.text.TextUtils;

import com.wxjz.module_base.db.bean.CheckgradeBean;
import com.wxjz.module_base.db.bean.GuestChooseGrade;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @ClassName CheckGradeDao
 * @Description TODO
 * @Author liufang
 * @Date 2019-10-28 14:00
 * @Version 1.0
 */
public class CheckGradeDao {
    private static CheckGradeDao checkGradeDao;

    public static CheckGradeDao getInstance() {
        if (checkGradeDao == null) {
            synchronized (CheckGradeDao.class) {
                if (checkGradeDao == null) {
                    checkGradeDao = new CheckGradeDao();
                }
            }
        }
        return checkGradeDao;
    }

    public void clear() {
        DataSupport.deleteAll(GuestChooseGrade.class);
    }

    /**
     * 获取保存的学习阶段和年级
     *
     * @return
     */
    public GuestChooseGrade getGuestChooseGrade() {
        GuestChooseGrade getChoose = LitePal.findFirst(GuestChooseGrade.class);
        return getChoose;
    }

    /**
     * 保存选择的学习阶段和年级
     *
     * @param levelId
     * @param levelName
     * @param gradeId
     * @param gradeName
     * @param isMatch   年级字段是否匹配上了
     */

    public void addGuestChooseGrade(int levelId, String levelName, String gradeId, String gradeName, Boolean isMatch) {
        GuestChooseGrade getChoose = LitePal.findFirst(GuestChooseGrade.class);
        if (getChoose != null) {
            getChoose.delete();
        }

        GuestChooseGrade guestChooseGrade = new GuestChooseGrade();
        guestChooseGrade.setLevelid(levelId);
        guestChooseGrade.setLevelName(levelName);
        guestChooseGrade.setGradeId(gradeId);
        guestChooseGrade.setGradeName(gradeName);
        guestChooseGrade.setGradeNameCanMatch(isMatch);
        guestChooseGrade.save();
    }

    public void addGuestChooseGrade(int levelId, String levelName, String gradeId, String gradeName) {
        GuestChooseGrade getChoose = LitePal.findFirst(GuestChooseGrade.class);
        if (getChoose != null) {
            getChoose.delete();
        }

        GuestChooseGrade guestChooseGrade = new GuestChooseGrade();
        guestChooseGrade.setLevelid(levelId);
        guestChooseGrade.setLevelName(levelName);
        guestChooseGrade.setGradeId(gradeId);
        guestChooseGrade.setGradeName(gradeName);
        guestChooseGrade.save();
    }

    public void updataGuestChooseGradeId(String gradeId) {
        GuestChooseGrade getChoose = LitePal.findFirst(GuestChooseGrade.class);
        if (getChoose != null) {
            getChoose.setGradeId(gradeId);
            getChoose.save();
        }
    }

    public boolean isGradeIdMatch() {
        List<GuestChooseGrade> list = DataSupport.findAll(GuestChooseGrade.class);
        if (list.size() > 0) {
            boolean levelid = list.get(0).getGradeNameCanMatch();
            return levelid;
        } else {
            return false;
        }

    }

    public int queryleveId() {
        List<GuestChooseGrade> list = DataSupport.findAll(GuestChooseGrade.class);
        if (list.size() > 0) {
            int levelid = list.get(0).getLevelid();
            return levelid;
        } else {
            return 1;
        }
    }

    public String queryGradeId() {
        List<GuestChooseGrade> list = DataSupport.findAll(GuestChooseGrade.class);
        if (list.size() > 0) {
            if (TextUtils.isEmpty(list.get(0).getGradeId())) {
                return "";
            } else {
                String grdeId = list.get(0).getGradeId();
                return grdeId;
            }
        } else {
            return "";
        }
    }

    public String queryGradeName() {
        List<GuestChooseGrade> list = DataSupport.findAll(GuestChooseGrade.class);
        if (list.size() > 0) {
            if (TextUtils.isEmpty(list.get(0).getGradeName())) {
                return "";
            } else {
                String grdeId = list.get(0).getGradeName();
                return grdeId;
            }
        } else {
            return "";
        }
    }

}
