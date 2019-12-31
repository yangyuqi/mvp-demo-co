package com.wxjz.module_base.db.bean;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

/**
 * Created by a on 2019/9/19.
 */

public class UserInfoBean extends LitePalSupport {
    private int id;
    private String loginName;
    private String userId;
    private String fullName;
    private String headUrl;

    private String schName;
    private String userType;
    private String gradeName;
    /**
     * 是否是会员 1是，2是过期，0不是会员。
     */
    private int isMember;
    /**
     * 获取年级
     */
    private String nianji;
    /**
     * 曾经注册过会员，才能拿到
     */
    private String ownLevelId;
    /**
     * 曾经注册过会员，才能拿到
     */
    private String ownLevelName;

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public String getNianji() {
        return nianji;
    }

    public void setNianji(String nianji) {
        this.nianji = nianji;
    }

    public String getOwnLevelId() {
        return ownLevelId;
    }

    public void setOwnLevelId(String ownLevelId) {
        this.ownLevelId = ownLevelId;
    }

    public String getOwnLevelName() {
        return ownLevelName;
    }

    public void setOwnLevelName(String ownLevelName) {
        this.ownLevelName = ownLevelName;
    }
}
