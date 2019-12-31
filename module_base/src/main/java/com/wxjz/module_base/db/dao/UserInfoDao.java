package com.wxjz.module_base.db.dao;

import android.text.TextUtils;
import android.util.Log;

import com.wxjz.module_base.db.bean.UserInfoBean;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by a on 2019/9/19.
 */

public class UserInfoDao {
    private static UserInfoDao userInfoDao;

    public static UserInfoDao getInstence() {
        if (userInfoDao == null) {
            synchronized (UserInfoDao.class) {
                if (userInfoDao == null) {
                    userInfoDao = new UserInfoDao();
                }
            }
        }
        return userInfoDao;
    }

    public UserInfoBean getCurrentUserInfo() {
        UserInfoBean userInfo = LitePal.findFirst(UserInfoBean.class);
        return userInfo;
    }

    public void deleteUserInfo() {
        LitePal.deleteAll(UserInfoBean.class, "id>?", "0");
    }

    public void saveUserInfo(com.wxjz.module_base.bean.UserInfoBean userInfoBean) {
        deleteUserInfo();
        com.wxjz.module_base.bean.UserInfoBean.UserBean user = userInfoBean.getUser();
        com.wxjz.module_base.bean.UserInfoBean.Zxxx0200Bean zxxx0200Bean = userInfoBean.getZxxx0200();
        UserInfoBean userInfoBean1 = new UserInfoBean();
        userInfoBean1.setUserId(user.getUserId());
        userInfoBean1.setFullName(user.getFullName());
        userInfoBean1.setHeadUrl((String) user.getHeadUrl());
        userInfoBean1.setSchName(user.getXxmc());
        userInfoBean1.setLoginName(user.getLoginName());
        userInfoBean1.setUserType(user.getUserType());
        userInfoBean1.setGradeName(zxxx0200Bean.getGradeName());
        userInfoBean1.setIsMember(user.getIsMember());
        userInfoBean1.setNianji(zxxx0200Bean.getNJ());
        userInfoBean1.setOwnLevelId((String) user.getOwnLevelId());
        userInfoBean1.setOwnLevelName((String) user.getOwnLevelName());
        userInfoBean1.save();
    }

    public int querryNjId() {
        List<UserInfoBean> list = DataSupport.findAll(UserInfoBean.class);
        if (list.size() > 0) {
            return Integer.parseInt(list.get(0).getNianji());
        } else {
            return -1;
        }
    }
}
