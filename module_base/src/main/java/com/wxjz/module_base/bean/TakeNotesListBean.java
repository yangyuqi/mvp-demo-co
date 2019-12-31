package com.wxjz.module_base.bean;

import java.util.List;

/**
 * @ClassName TakeNotesListBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-25 19:06
 * @Version 1.0
 */
public class TakeNotesListBean {

    private List<TakeNoteBean> data;

    public List<TakeNoteBean> getData() {
        return data;
    }

    public void setData(List<TakeNoteBean> data) {
        this.data = data;
    }

    /**
     * "id": 33,
     * "domId": null,
     * "videoDom": 234,
     * "domTitle": null,
     * "domContent": "22222223333333",
     * "userAnswer": null,
     * "rightAnswer": null,
     * "isSelect": null,
     * "domType": null,
     * "createTime": null,
     * "updateTime": null,
     * "videoId": 359,
     * "userId": "123",
     * "isAnswer": null,
     * "tOwnDomOptions": null,
     * "tOwnDomOptionPictures": null,
     * "playAddress": "http://www.bestudy360.com/CSP/res//mobilecompus/file1245a2ef-34dc-4d8c-acea-d0f24babdced.mp4",
     * "videoTitle": "奥德赛",
     * "videoDesc": "敖德萨多所"
     */

    public class TakeNoteBean {
        int id;
        /**
         * 笔记位置，秒数
         */
        long videoDom;
        /**
         * 笔记内容
         */
        String domContent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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
}
