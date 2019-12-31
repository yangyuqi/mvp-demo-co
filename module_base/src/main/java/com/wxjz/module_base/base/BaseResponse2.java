package com.wxjz.module_base.base;

/**
 * 将网络返回数据封装，T为动态的内容，公共的单独拎出来
 * <p>
 * Created by a on 2019/7/5.
 */

public class BaseResponse2<T> {

    private int code = -1;
    private String msg;
    private T data;



    public T getDatas() {
        return data;
    }

    public void setDatas(T datas) {
        this.data = datas;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int errorCode) {
        this.code = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String errorMsg) {
        this.msg = errorMsg;
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                "errorCode=" + code +
                ", errorMsg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
