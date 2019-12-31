package com.wxjz.module_base.db.dao;

import com.wxjz.module_base.db.bean.DialogShow;

import org.litepal.LitePal;

/**
 * @ClassName DialogShowDao
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-30 16:52
 * @Version 1.0
 */
public class DialogShowDao {
    private static DialogShowDao dialogShowDao;

    public static DialogShowDao getInstance() {
        if (dialogShowDao == null) {
            synchronized (DialogShowDao.class) {
                if (dialogShowDao == null) {
                    dialogShowDao = new DialogShowDao();
                }
            }
        }
        return dialogShowDao;
    }

    public void addDialogshow(boolean isshow) {
        DialogShow dialogShow = LitePal.findFirst(DialogShow.class);
        if (dialogShow != null) {
            dialogShow.delete();
        }
        DialogShow dialogShow1 = new DialogShow();
        dialogShow1.setShow(isshow);
        dialogShow1.save();
    }


    public DialogShow getDialogShow() {
        DialogShow show = LitePal.findFirst(DialogShow.class);
        return show;
    }
}
