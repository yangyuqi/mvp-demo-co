package com.wxjz.module_base.bean;

import java.util.List;

/**
 * @ClassName PointListBean
 * @Description 点位数据
 * @Author liufang
 * @Date 2019-09-29 17:23
 * @Version 1.0
 */
public class PointListBean {

    private List<PointBean> data;

    public List<PointBean> getData() {
        return data;
    }

    public void setData(List<PointBean> data) {
        this.data = data;
    }

    public class PointBean {
        private int id;
        private long videoDom;
        //标题
        private String domTitle;
        //题目内容
        private String domContent;
        private String userAnswer;
        private String rightAnswer;
        private int isSelect;
        private int domType;
        private int isAnswer;
        private int isRight;
        private List<PointListBean.ChooseItem> tOwnDomOptions;
        private List<PointListBean.QuestionIcon> tOwnDomOptionPictures;
        private boolean canAnswer;
        //控制是展开还是收起的状态
        private boolean isStatus;

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

        public int getIsRight() {
            return isRight;
        }

        public void setIsRight(int isRight) {
            this.isRight = isRight;
        }

        public void setIsAnswer(int isAnswer) {
            this.isAnswer = isAnswer;
        }

        public String getDomTitle() {
            return domTitle;
        }

        public void setDomTitle(String domTitle) {
            this.domTitle = domTitle;
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

        public boolean isCanAnswer() {
            return canAnswer;
        }

        public void setCanAnswer(boolean canAnswer) {
            this.canAnswer = canAnswer;
        }

        public boolean isStatus() {
            return isStatus;
        }

        public void setStatus(boolean status) {
            isStatus = status;
        }
    }


    public class ChooseItem {
        private int id;
        private String option;
        private String optionContent;
        private String url;
        //是否是选中状态
        private boolean isSelect;
        //是单选还是多选
        private boolean isSingle;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isSingle() {
            return isSingle;
        }

        public void setSingle(boolean single) {
            isSingle = single;
        }
    }

    public class QuestionIcon {
        private String url;

        private String optionTag;

        private String location;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOptionTag() {
            return optionTag;
        }

        public void setOptionTag(String optionTag) {
            this.optionTag = optionTag;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
