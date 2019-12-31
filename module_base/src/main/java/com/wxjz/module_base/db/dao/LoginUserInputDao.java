package com.wxjz.module_base.db.dao;

import com.wxjz.module_base.db.bean.LoginUserInput;

import org.litepal.LitePal;

/**
 * @ClassName LoginUserInputDao
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-20 13:13
 * @Version 1.0
 */
public class LoginUserInputDao {
    private static LoginUserInputDao loginUserInputDao;

    public static LoginUserInputDao getInstence() {
        if (loginUserInputDao == null) {
            synchronized (LoginUserInputDao.class) {
                if (loginUserInputDao == null) {
                    loginUserInputDao = new LoginUserInputDao();
                }
            }
        }
        return loginUserInputDao;
    }

    public void addUserInput(String username, String password) {
        LoginUserInput first = LitePal.findFirst(LoginUserInput.class);
        if (first != null) {
            first.delete();
        }
        LoginUserInput loginUserInput = new LoginUserInput();
        loginUserInput.setUserName(username);
        loginUserInput.setUserPassword(password);
        loginUserInput.save();
    }

    public LoginUserInput getCheckedSchool() {
        LoginUserInput first = LitePal.findFirst(LoginUserInput.class);
        return first;
    }
}
