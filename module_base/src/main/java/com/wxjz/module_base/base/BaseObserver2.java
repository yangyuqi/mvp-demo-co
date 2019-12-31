package com.wxjz.module_base.base;

import com.wxjz.module_base.http.ApiException;
import com.wxjz.module_base.http.ExceptionHandler;
import com.wxjz.module_base.mvp.IBaseView;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by a on 2019/7/5.
 */

public abstract class BaseObserver2<T> extends DisposableObserver<BaseResponse2<T>> {
    private IBaseView mView;

    public BaseObserver2() {
    }

    public BaseObserver2(IBaseView view) {
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
    public void onNext(BaseResponse2<T> tBaseResponse) {
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
        ExceptionHandler.handleException(e);
        if (mView != null) {
            mView.hideLoading();
        }

    }

   protected abstract void onSuccess(T t);

}
