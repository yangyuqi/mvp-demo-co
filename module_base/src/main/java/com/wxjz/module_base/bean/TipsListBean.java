package com.wxjz.module_base.bean;

import java.util.List;

/**
 * @ClassName TipsListBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-26 18:42
 * @Version 1.0
 */
public class TipsListBean {

    public List<TipsBean> data;

    public class TipsBean {
        long videoDom;
        String domContent;

        public long getVideoDom() {
            return videoDom;
        }

        public void setVideoDom(long videoDom) {
            this.videoDom = videoDom;
        }

        public String getDomContent() {
            return domContent;
        }

        public void setDomContent(String domContent) {
            this.domContent = domContent;
        }
    }

    public List<TipsBean> getData() {
        return data;
    }

    public void setData(List<TipsBean> data) {
        this.data = data;
    }
}
