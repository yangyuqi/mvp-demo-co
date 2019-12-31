package com.wxjz.module_base.mvp;

import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.base.BaseObserver2;
import com.wxjz.module_base.base.BaseResponse;
import com.wxjz.module_base.base.BaseResponse2;
import com.wxjz.module_base.http.RetrofitClient;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by a on 2019/7/5.
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private WeakReference<V> vWeakReference;
    private CompositeDisposable composite;

    @Override
    public void attachView(V view) {
        vWeakReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        this.vWeakReference = null;
        if (composite != null) {
            composite.dispose();

        }
    }

    @Override
    public boolean isViewAttached() {
        return vWeakReference.get() != null;
    }

    @Override
    public V getView() {
        return vWeakReference.get();
    }

    /**
     * 返回类型为datas message
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> void makeRequest(Observable<BaseResponse<T>> observable, BaseObserver<T>
            observer) {
        if (composite == null) {
            composite = new CompositeDisposable();
        }
        composite.add(observable//可以结合compose封装
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable)
                            throws Exception {
//                        if (mBaseView != null && !NetWorkUtil.isNetWorkAvailable() ) {
//                            mBaseView.alertWarn(R.string.network_is_unavailable);
//                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    /**
     * 返回类型为data msg
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> void makeRequest2(Observable<BaseResponse2<T>> observable, BaseObserver2<T>
            observer) {
        if (composite == null) {
            composite = new CompositeDisposable();
        }
        BaseObserver2<T> tBaseObserver2 = observable//可以结合compose封装
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable)
                            throws Exception {
//                        if (mBaseView != null && !NetWorkUtil.isNetWorkAvailable() ) {
//                            mBaseView.alertWarn(R.string.network_is_unavailable);
//                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        composite.add(tBaseObserver2);
    }


    protected <T> T create(Class<T> clazz) {
        return RetrofitClient.getInstance().getRetrofit().create(clazz);
    }
}
