package com.wxjz.module_base.bean;

import java.util.List;

/**
 * @ClassName ExerciseListBean
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-27 11:30
 * @Version 1.0
 */
public class ExerciseListBean {

    private List<ExerciseBean> data;

    public List<ExerciseBean> getData() {
        return data;
    }

    public void setData(List<ExerciseBean> data) {
        this.data = data;
    }

    /**
     * {
     * "id": 373,
     * "domId": null,
     * "videoDom": 100,
     * "domTitle": null,
     * "domContent": "如图所示装置进行实验（图中铁架台等仪器均已略去）．在I中加入试剂后，塞紧橡皮塞，立即打开止水夹，II中有气泡冒出；一段时间后关闭止水夹，II 中液面上升，溶液由无色变为浑浊．符合以上实验现象的I和II中应加入的试剂是",
     * "userAnswer": null,
     * "rightAnswer": "A,B",
     * "isSelect": 0,
     * "domType": 2,
     * "createTime": "2019-09-19 17:22:31.0",
     * "updateTime": "2019-09-19 17:22:31.0",
     * "videoId": 359,
     * "userId": null,
     * "isAnswer": 0,
     * "tOwnDomOptions": [
     * {
     * "id": 601,
     * "option": "A",
     * "optionContent": "sdfjlksdjfsdjlfkjslkjsdfkldjflksd kjfkjsadkkldjf",
     * "createTime": 1568884951000,
     * "updateTime": 1568884951000
     * },
     * {
     * "id": 602,
     * "option": "B",
     * "optionContent": "了解公司就打开了几个了快递费了的空间够可怜的房间里快递费",
     * "createTime": 1568884951000,
     * "updateTime": 1568884951000
     * },
     * {
     * "id": 603,
     * "option": "C",
     * "optionContent": "打扫房间拉萨的副驾驶的水电费",
     * "createTime": 1568884951000,
     * "updateTime": 1568884951000
     * },
     * {
     * "id": 604,
     * "option": "D",
     * "optionContent": "adfasdfsdfksdfkljsdfj",
     * "createTime": 1568884951000,
     * "updateTime": 1568884951000
     * }
     * ],
     * "tOwnDomOptionPictures": [
     * {
     * "id": 130,
     * "domId": 373,
     * "location": "project",
     * "optionTag": null,
     * "url": "http://www.bestudy360.com/CSP/res/course-select/55598a04-71fd-4997-89ec-713ef5d9d888.jpg",
     * "createTime": 1568884951000,
     * "updateTime": 1568884951000
     * }
     * ],
     * "playAddress": null,
     * "videoTitle": null,
     * "videoDesc": null
     * }
     */
    public class ExerciseBean {
        private int id;
        private long videoDom;
        //题目内容
        private String domContent;
        private String userAnswer;
        private String rightAnswer;
        private int isSelect;
        private int domType;
        private int isAnswer;
        private List<ChooseItem> tOwnDomOptions;
        private List<QuestionIcon> tOwnDomOptionPictures;
        private int isRight;

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

        public String getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }

        public String getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public int getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(int isSelect) {
            this.isSelect = isSelect;
        }

        public int getDomType() {
            return domType;
        }

        public void setDomType(int domType) {
            this.domType = domType;
        }

        public int getIsAnswer() {
            return isAnswer;
        }

        public void setIsAnswer(int isAnswer) {
            this.isAnswer = isAnswer;
        }

        public List<ChooseItem> gettOwnDomOptions() {
            return tOwnDomOptions;
        }

        public void settOwnDomOptions(List<ChooseItem> tOwnDomOptions) {
            this.tOwnDomOptions = tOwnDomOptions;
        }

        public List<QuestionIcon> gettOwnDomOptionPictures() {
            return tOwnDomOptionPictures;
        }

        public void settOwnDomOptionPictures(List<QuestionIcon> tOwnDomOptionPictures) {
            this.tOwnDomOptionPictures = tOwnDomOptionPictures;
        }

        public int getIsRight() {
            return isRight;
        }

        public void setIsRight(int isRight) {
            this.isRight = isRight;
        }
    }

    public class ChooseItem {
        private int id;
        private String option;
        private String optionContent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getOptionContent() {
            return optionContent;
        }

        public void setOptionContent(String optionContent) {
            this.optionContent = optionContent;
        }
    }

    public class QuestionIcon {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
