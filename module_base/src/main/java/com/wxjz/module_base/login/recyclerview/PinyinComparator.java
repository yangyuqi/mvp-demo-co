package com.wxjz.module_base.login.recyclerview;

import com.wxjz.module_base.db.bean.SchoolItemBean;

import java.util.Comparator;

/**
 * @ClassName PinyinComparator
 * @Description 根据拼音来排列RecyclerView里面的数据类
 * @Author liufang
 * @Date 2019-09-05 15:42
 * @Version 1.0
 */
public class PinyinComparator implements Comparator<SchoolItemBean> {

    @Override
    public int compare(SchoolItemBean schoolItemBean, SchoolItemBean t1) {
        if (schoolItemBean.getLetters().equals("@")
                || t1.getLetters().equals("#")) {
            return 1;
        } else if (schoolItemBean.getLetters().equals("#")
                || t1.getLetters().equals("@")) {
            return -1;
        } else {
            return schoolItemBean.getLetters().compareTo(t1.getLetters());
        }
    }
}
