package com.wxjz.module_base.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wxjz.module_base.R;
import com.wxjz.module_base.http.RetrofitClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName BaseDialog
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-11 10:31
 * @Version 1.0
 */
public class BaseDialog extends Dialog {
    private CompositeDisposable composite;
    public BaseDialog(
            Context context) {
        this(context, R.style.Custom_Dialog_Fullscreen);

    }


    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去除对话框的标题
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(0x00000000);
        getWindow().setBackgroundDrawable(gradientDrawable);//设置对话框边框背景,必须在代码中设置对话框背景，不然对话框背景是黑色的

        dimAmount(0.2f);

    }

    public BaseDialog contentView(@LayoutRes int layoutResID) {
        getWindow().setContentView(layoutResID);
        return this;
    }


    public BaseDialog contentView(@NonNull View view) {
        getWindow().setContentView(view);
        return this;
    }

    public BaseDialog contentView(@NonNull View view, @Nullable ViewGroup.LayoutParams params) {
        getWindow().setContentView(view, params);
        return this;
    }

    public BaseDialog layoutParams(@Nullable ViewGroup.LayoutParams params) {
        getWindow().setLayout(params.width, params.height);
        return this;
    }


    /**
     * 点击外面是否能dissmiss
     *
     * @param canceledOnTouchOutside
     * @return
     */
    public BaseDialog canceledOnTouchOutside(boolean canceledOnTouchOutside) {
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (!canceledOnTouchOutside) {
            setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
                    return false;
                }
            });
        }
        return this;
    }

    /**
     * 位置
     *
     * @param gravity
     * @return
     */
    public BaseDialog gravity(int gravity) {

        getWindow().setGravity(gravity);

        return this;

    }

    /**
     * 偏移
     *
     * @param x
     * @param y
     * @return
     */
    public BaseDialog offset(int x, int y) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.x = x;
        layoutParams.y = y;

        return this;
    }

    /*
       设置背景阴影,必须setContentView之后调用才生效
        */
    public BaseDialog dimAmount(float dimAmount) {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = dimAmount;
        return this;
    }


    /*
   动画类型
    */
    public BaseDialog animType(BaseDialog.AnimInType animInType) {


        switch (animInType.getIntType()) {
            case 0:
                getWindow().setWindowAnimations(R.style.dialog_zoom);

                break;
            case 1:
                getWindow().setWindowAnimations(R.style.dialog_anim_left);

                break;
            case 2:
                getWindow().setWindowAnimations(R.style.dialog_anim_top);

                break;
            case 3:
                getWindow().setWindowAnimations(R.style.dialog_anim_right);

                break;
            case 4:
                getWindow().setWindowAnimations(R.style.dialog_anim_bottom);

                break;
        }
        return this;
    }


    /*
    动画类型
     */
    public enum AnimInType {
        CENTER(0),
        LEFT(1),
        TOP(2),
        RIGHT(3),
        BOTTOM(4);

        AnimInType(int n) {
            intType = n;
        }

        final int intType;

        public int getIntType() {
            return intType;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Subscribe
    public void onSubscribe() {

    }

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

    protected <T> T create(Class<T> clazz) {
        return RetrofitClient.getInstance().getRetrofit().create(clazz);
    }
}
