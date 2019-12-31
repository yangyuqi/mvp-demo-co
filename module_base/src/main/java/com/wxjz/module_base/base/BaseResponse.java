package com.wxjz.module_base.base;

/**
 * 将网络返回数据封装，T为动态的内容，公共的单独拎出来
 * <p>
 * Created by a on 2019/7/5.
 */

public class BaseResponse<T> {

    private int code = -1;
    private String message;
    private T datas;



    public T getDatas() {
        return datas;
    }

    public void setDatas(T datas) {
        this.datas = datas;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int errorCode) {
        this.code = errorCode;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String errorMsg) {
        this.message = errorMsg;
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                "errorCode=" + code +
                ", errorMsg='" + message + '\'' +
                ", data=" + datas +
                '}';
    }
}
