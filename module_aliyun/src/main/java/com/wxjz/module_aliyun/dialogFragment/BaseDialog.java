package com.wxjz.module_aliyun.dialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_base.base.BaseObserver;
import com.wxjz.module_base.base.BaseResponse;
import com.wxjz.module_base.db.bean.DialogShow;
import com.wxjz.module_base.db.dao.DialogShowDao;
import com.wxjz.module_base.http.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * User: 11653
 * Date: 2019/7/25
 * Time: 9:47
 */
public abstract class BaseDialog extends DialogFragment {

    @LayoutRes
    protected int mLayoutResId;
    private CompositeDisposable composite;
    private float mDimAmount = 0.5f;//背景昏暗度
    private boolean mShowBottomEnable;//是否底部显示
    private int mMargin = 0;//左右边距
    private int mAnimStyle = 0;//进入退出动画
    private boolean mOutCancel = true;//点击外部取消
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mGravity = 0;
    private boolean isSendEventBus;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog);
        mLayoutResId = setUpLayoutId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLayoutResId != 0) {
            View view = inflater.inflate(mLayoutResId, container, false);
            convertView(ViewHolder.create(view), this);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = mDimAmount;
            //设置dialog显示位置
            if (mShowBottomEnable) {
                params.gravity = Gravity.BOTTOM;
            } else if (mGravity != 0) {
                params.gravity = mGravity;
            }

            //设置dialog宽度
            if (mWidth == 0) {
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                params.width = mWidth;
            }

            //设置dialog高度
            if (mHeight == 0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = mHeight;
            }
            window.setAttributes(params);
            //设置dialog动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle);
            }
        }
        setCancelable(mOutCancel);
    }

    /**
     * 设置背景昏暗度
     *
     * @param dimAmount
     * @return
     */
    public BaseDialog setDimAmout(@FloatRange(from = 0, to = 1) float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    /**
     * 是否显示底部
     *
     * @param showBottom
     * @return
     */
    public BaseDialog setShowBottom(boolean showBottom) {
        mShowBottomEnable = showBottom;
        return this;
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    public BaseDialog setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    /**
     * 设置位置
     *
     * @param gravity
     * @return
     */
    public BaseDialog setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    /**
     * 设置左右margin
     *
     * @param margin
     * @return
     */
    public BaseDialog setMargin(int margin) {
        mMargin = margin;
        return this;
    }

    public BaseDialog dismss(OnDialogDismissListener dialogDismissListener) {
        dialogDismissListener.onDismiss();
        return this;
    }

    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * @return
     */
    public BaseDialog setAnimStyle(@StyleRes int animStyle) {
        mAnimStyle = animStyle;
        return this;
    }

    /**
     * 设置是否点击外部取消
     *
     * @param outCancel
     * @return
     */
    public BaseDialog setOutCancel(boolean outCancel) {
        mOutCancel = outCancel;
        return this;
    }

    public BaseDialog show(FragmentManager manager) {
        DialogShowDao.getInstance().addDialogshow(true);
        super.show(manager, String.valueOf(System.currentTimeMillis()));
        return this;
    }

    /**
     * 判断是否用EventBus
     *
     * @param isEventBus
     * @return
     */
    public BaseDialog setUseEventBus(boolean isEventBus) {
        isSendEventBus = isEventBus;
        return this;
    }

    /**
     * 设置dialog布局
     *
     * @return
     */
    public abstract int setUpLayoutId();

    /**
     * 操作dialog布局
     *
     * @param holder
     * @param dialog
     */
    public abstract void convertView(ViewHolder holder, BaseDialog dialog);

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public interface OnDialogDismissListener {
        void onDismiss();
    }

    public boolean isSendEventBus() {
        return isSendEventBus;
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
