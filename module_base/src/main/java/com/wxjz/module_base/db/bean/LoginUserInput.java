package com.wxjz.module_base.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName loginUserInput
 * @Description TODO
 * @Author liufang
 * @Date 2019-11-20 13:10
 * @Version 1.0
 */
public class LoginUserInput extends LitePalSupport {

    private String userName;
    private String userPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
