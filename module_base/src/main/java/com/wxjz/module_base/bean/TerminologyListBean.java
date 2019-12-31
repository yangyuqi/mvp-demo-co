package com.wxjz.module_base.bean;

import java.util.List;

/**
 * @ClassName TerminologyListBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-26 20:23
 * @Version 1.0
 */
public class TerminologyListBean {

    public List<TerminologyBean> data;

    public class TerminologyBean {
        long videoDom;
        String domContent;
        String domTitle;

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

        public String getDomTitle() {
            return domTitle;
        }

        public void setDomTitle(String domTitle) {
            this.domTitle = domTitle;
        }
    }

    public List<TerminologyBean> getData() {
        return data;
    }

    public void setData(List<TerminologyBean> data) {
        this.data = data;
    }
}
