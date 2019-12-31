package com.wxjz.module_base.bean;

import java.util.List;

/**
 * @ClassName SchoollistBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-17 18:01
 * @Version 1.0
 */
public class SchoollistBean {

    private int total;

    private List<SchoolBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SchoolBean> getDatas() {
        return list;
    }

    public void setDatas(List<SchoolBean> datas) {
        this.list = datas;
    }

    /**
     * "id": 1,
     * "xxdm": "wxm",
     * "xxmc": "北师大长沙附校",
     * "xxywmc": "BSD",
     * "xxdz": "湖南省长沙市",
     * "xxbxlxm": "105",
     * "dzxx": "www.changshafuxiao.com",
     * "logoimage": "http://images.8dsun.com/C%3A%5Cfakepath%5Clogin-logo.png1544156810531",
     * "defaultadmin": "48bda45556a0be9caf0f38813008b3c2",
     * "createTime": 1543399563000,
     * "topLogo": "http://images.8dsun.com/C%3A%5Cfakepath%5Clogin-logo_2.png1544156801643",
     */
    public class SchoolBean {
        /**
         * 学校id
         */
        private int id;

        private String xxdm;

        private String xxmc;
        /**
         * 学校logo图片
         */
        private String logoimage;

        private String lxdh;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getXxdm() {
            return xxdm;
        }

        public void setXxdm(String xxdm) {
            this.xxdm = xxdm;
        }

        public String getLogoimage() {
            return logoimage;
        }

        public void setLogoimage(String logoimage) {
            this.logoimage = logoimage;
        }

        public String getXxmc() {
            return xxmc;
        }

        public void setXxmc(String xxmc) {
            this.xxmc = xxmc;
        }

        public String getLxdh() {
            return lxdh;
        }

        public void setLxdh(String lxdh) {
            this.lxdh = lxdh;
        }
    }
}
