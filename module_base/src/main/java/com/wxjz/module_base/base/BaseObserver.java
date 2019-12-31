package com.wxjz.module_base.base;

import com.wxjz.module_base.event.NotLoginEvent;
import com.wxjz.module_base.http.ApiException;
import com.wxjz.module_base.http.ExceptionHandler;
import com.wxjz.module_base.mvp.IBaseView;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by a on 2019/7/5.
 */

public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>> {
    private IBaseView mView;

    public BaseObserver() {
    }

    public BaseObserver(IBaseView view) {
        this.mView = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mView != null) {
            mView.showLoading();
        }
    }

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
        if (mView != null) {
            mView.showLoading();
        }
        int returnCode = tBaseResponse.getCode();
        /**
         * 目前任务code==1为请求成功并有返回数据
         * 如果返回code!=1 则交给ExceptionHandler处理
         */
        if (returnCode == 1) {
            T data = tBaseResponse.getDatas();
            onSuccess(data);
        } else {
            onError(new ApiException(returnCode, tBaseResponse.getMsg()));
        }

    }

    @Override
    public void onComplete() {
        if (mView != null) {
            mView.hideLoading();
        }
    }

    /**
     * 异常处理，包括两方面数据：
     * (1) 服务端没有没有返回数据，HttpException，如网络异常，连接超时;
     * (2) 服务器返回数据，Code !=0
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (mView != null) {
            mView.hideLoading();
        }
//        if (e instanceof HttpException){
//            int code = ((HttpException) e).code();
//            if (code==401){
//                //未登陆
//                EventBus.getDefault().post(new NotLoginEvent());
//                return;
//            }
//        }
        ExceptionHandler.handleException(e);


    }

    protected abstract void onSuccess(T t);

}
