package com.wxjz.module_base.bean;

import java.util.List;

/**
 * Created by a on 2019/9/17.
 */

public class ClassRankBean {


    /**
     * learingTime : 0
     * list : [{"courseCount":2,"learingRealTime":null,"userId":"87783a0c806d4cabaf259b9509dc4f74","userName":"刘方","xqhid":234,"graid":2231275,"claid":1600000000,"gradeName":null},{"courseCount":9,"learingRealTime":null,"userId":"cd518bb19498432785aa1bdc95f114c3","userName":"仲小玲","xqhid":234,"graid":2231275,"claid":1600000000,"gradeName":null}]
     */

    private int learingTime;
    private int todayTime;
    private int rank;
    private List<ListBean> list;
    public int getTodayTime() {
        return todayTime;
    }

    public void setTodayTime(int todayTime) {
        this.todayTime = todayTime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getLearingTime() {
        return learingTime;
    }

    public void setLearingTime(int learingTime) {
        this.learingTime = learingTime;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * courseCount : 2
         * learingRealTime : null
         * userId : 87783a0c806d4cabaf259b9509dc4f74
         * userName : 刘方
         * xqhid : 234
         * graid : 2231275
         * claid : 1600000000
         * gradeName : null
         */

        private int courseCount;
        private int learingRealTime;
        private String userId;
        private String userName;
        private int xqhid;
        private int graid;
        private int claid;
        private Object gradeName;


        public int getCourseCount() {
            return courseCount;
        }

        public void setCourseCount(int courseCount) {
            this.courseCount = courseCount;
        }

        public int getLearingRealTime() {
            return learingRealTime;
        }

        public void setLearingRealTime(int learingRealTime) {
            this.learingRealTime = learingRealTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getXqhid() {
            return xqhid;
        }

        public void setXqhid(int xqhid) {
            this.xqhid = xqhid;
        }

        public int getGraid() {
            return graid;
        }

        public void setGraid(int graid) {
            this.graid = graid;
        }

        public int getClaid() {
            return claid;
        }

        public void setClaid(int claid) {
            this.claid = claid;
        }

        public Object getGradeName() {
            return gradeName;
        }

        public void setGradeName(Object gradeName) {
            this.gradeName = gradeName;
        }
    }
}
