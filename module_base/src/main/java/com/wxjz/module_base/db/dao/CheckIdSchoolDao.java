package com.wxjz.module_base.db.dao;

import com.wxjz.module_base.db.bean.CheckedSchool;

import org.litepal.LitePal;

/**
 * Created by a on 2019/9/19.
 */

public class CheckIdSchoolDao {
    private static CheckIdSchoolDao checkIdSchoolDao;

    public static CheckIdSchoolDao getInstance() {
        if (checkIdSchoolDao == null) {
            synchronized (CheckIdSchoolDao.class) {
                if (checkIdSchoolDao == null) {
                    checkIdSchoolDao = new CheckIdSchoolDao();
                }
            }
        }
        return checkIdSchoolDao;
    }

    public void addCheckedSchool(String schName, int schId, String lxdh) {
        CheckedSchool first = LitePal.findFirst(CheckedSchool.class);
        if (first != null) {
            first.delete();
        }
        CheckedSchool checkedSchool = new CheckedSchool();
        checkedSchool.setSchId(schId);
        checkedSchool.setSchName(schName);
        checkedSchool.setLxdh(lxdh);
        checkedSchool.save();
    }

    public CheckedSchool getCheckedSchool() {
        CheckedSchool first = LitePal.findFirst(CheckedSchool.class);
        return first;
    }
}
