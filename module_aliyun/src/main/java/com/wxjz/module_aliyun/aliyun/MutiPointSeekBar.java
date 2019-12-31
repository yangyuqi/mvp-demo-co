package com.wxjz.module_aliyun.aliyun;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wxjz.module_aliyun.R;
import com.wxjz.module_aliyun.aliyun.widget.AliyunVodPlayerView;
import com.wxjz.module_aliyun.popwindow.CustomPopWindow;
import com.wxjz.module_base.bean.PointListBean;
import com.wxjz.module_base.db.bean.BaseBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

import javax.xml.datatype.Duration;

/**
 * Created by a on 2019/7/16.
 */

@SuppressLint("AppCompatCustomView")
public class MutiPointSeekBar extends SeekBar {
    private Context context;
    //所有的点位集合
    private List<PointListBean.PointBean> points;
    //筛选出来的点位集合
    private List<PointListBean.PointBean> filterpoints;
    //控制是否画出点位
    private boolean isShowPoint = true;
    CustomPopWindow popWindow;
    CustomPopWindow trianglepopwindows;
    int width;
    int[] location = new int[2];
    public List<Boolean> popwindowIsShow = new ArrayList<>();
    public OnPopWindowOnClick onPopWindowOnClick;
    //用于保存弹出的popwindow
    public LinkedHashMap<Integer, CustomPopWindow> linkedHashMap = new LinkedHashMap<>();
    //用于保存弹出的popwindow下标
    public LinkedHashMap<Integer, CustomPopWindow> linkedHashMap1 = new LinkedHashMap<>();
    private MyPopWindowHandler myPopWindowHandler = new MyPopWindowHandler(this);

    private long videoDuration;

    public MutiPointSeekBar(Context context) {
        super(context);
    }


    public MutiPointSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);

    }

    public MutiPointSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        this.context = context;
    }

    @SuppressLint("DrawAllocation")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint hintPaint = new Paint();
        hintPaint.setColor(getResources().getColor(R.color.hint_color));
        Paint analysisPaint = new Paint();
        analysisPaint.setColor(getResources().getColor(R.color.analysis_color));
        Paint exercisePaint = new Paint();
        exercisePaint.setColor(getResources().getColor(R.color.exercise_color));
        Paint notePaint = new Paint();
        notePaint.setColor(getResources().getColor(R.color.note_color));
        int centerY = getHeight() / 2;
        if (isShowPoint) {
            if (videoDuration != 0) {
                for (PointListBean.PointBean tipPoint : points) {
                    long videoDom = tipPoint.getVideoDom() * 1000;
                    double progress = videoDom * 1.0f / videoDuration;
                    /**
                     *  0 提示
                     *  1 术语
                     *  2 题目
                     *  3 笔记
                     */
                    int type = tipPoint.getDomType();
                    switch (type) {
                        case 0:
                            setPointProgressAndTip(canvas, centerY, progress, hintPaint);
                            break;
                        case 1:
                            setPointProgressAndTip(canvas, centerY, progress, analysisPaint);
                            break;
                        case 2:
                            setPointProgressAndTip(canvas, centerY, progress, exercisePaint);
                            break;
                        case 3:
                            setPointProgressAndTip(canvas, centerY, progress, notePaint);
                            break;
                        default:
                            break;
                    }
                }

            }

        }

    }

    /**
     * 设置点的位置和图片
     *
     * @param canvas
     * @param centerY
     * @param progress
     * @param paint
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setPointProgressAndTip(Canvas canvas, int centerY, double progress, Paint paint) {
        width = getWidth() - getPaddingLeft() - getPaddingRight();
        canvas.drawCircle((float) (width * progress) + getPaddingLeft() + 5, getHeight() / 2, 5f, paint);
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
//        canvas.drawBitmap(bitmap, (float) (progress * width + getPaddingLeft() - bitmap.getWidth() / 2), centerY - 70, paint);
    }

    /**
     * 添加点位
     *
     * @param pointBeans
     * @param points
     */
    public void setMutiPoints(List<PointListBean.PointBean> pointBeans, List<PointListBean.PointBean> points, long duration) {
        this.points = pointBeans;
        this.filterpoints = points;
        this.videoDuration = duration;
        getLocationOnScreen(location);
        popwindowIsShow.clear();
        for (int i = 0; i < points.size(); i++) {
            popwindowIsShow.add(i, false);
        }
        invalidate();
    }

    /**
     * 设置是否开启学习模式
     *
     * @param isShowPoint
     */
    public void setBoolean(boolean isShowPoint) {
        this.isShowPoint = isShowPoint;
        if (!isShowPoint) {
            if (popWindow != null) {
                myPopWindowHandler.removeCallbacksAndMessages(null);
                popWindow.dissmiss();
            }
        }
        invalidate();
    }


    /**
     * 弹出提示框，内容，标题
     *
     * @param type     弹出的类型
     * @param title    内容的title
     * @param progress 进度条的占比
     * @param index    数据的下标
     */
    public void showTipsInSeekBar(int type, String title, double progress, int index) {
        if (isShowPoint) {
            ImageView circle = null;
            TextView textView = null;
            getLocationOnScreen(location);
            View contentview = LayoutInflater.from(getContext()).inflate(R.layout.popwindows_tips_content, null);
            View triangle = LayoutInflater.from(getContext()).inflate(R.layout.pop_windows_triangle, null);
            if (contentview != null) {
                circle = contentview.findViewById(R.id.iv_tips_type);
                textView = contentview.findViewById(R.id.tv_pop_content);
                switch (type) {
                    case 0:
                        //提示
                        if (circle != null) {
                            circle.setImageDrawable(getResources().getDrawable(R.drawable.circle_orange_bg));
                        }
                        if (textView != null) {
                            textView.setText(title);
                        }
                        if (!popwindowIsShow.get(index)) {
                            if (isShown()) {
                                if (triangle != null) {
                                    //展示提示框下面的三角形
                                    popTriangleShow(triangle, progress, location[1], index);
                                }
                                //展示提示框
                                popwindowshow(contentview, progress, location[1], index);
                            }
                        }
                        break;
                    case 1:
                        //术语
                        if (circle != null) {
                            circle.setImageDrawable(getResources().getDrawable(R.drawable.circle_green_bg));
                        }
                        if (textView != null) {
                            textView.setText(title);
                        }
                        if (!popwindowIsShow.get(index)) {
                            //如果没有展示，就show
                            if (isShown()) {
                                if (triangle != null) {
                                    //展示提示框下面的三角形
                                    popTriangleShow(triangle, progress, location[1], index);
                                }
                                //展示提示框
                                popwindowshow(contentview, progress, location[1], index);
                            }

                        }
                        break;
                    case 2:
                        //题目
                        if (circle != null) {
                            circle.setImageDrawable(getResources().getDrawable(R.drawable.circle_blue_bg));
                        }
                        if (textView != null) {
                            textView.setText(title);
                        }
                        if (!popwindowIsShow.get(index)) {
                            if (isShown()) {
                                //当前是否是展现出来的
                                if (triangle != null) {
                                    //展示提示框下面的三角形
                                    popTriangleShow(triangle, progress, location[1], index);
                                }
                                //展示提示框
                                popwindowshow(contentview, progress, location[1], index);
                            }
                        }

                        break;
                    case 3:
                        //记笔记
                        if (circle != null) {
                            circle.setImageDrawable(getResources().getDrawable(R.drawable.circle_red_bg));
                        }
                        if (textView != null) {
                            textView.setText(title);
                        }

                        if (!popwindowIsShow.get(index)) {
                            if (isShown()) {
                                if (triangle != null) {
                                    //展示提示框下面的三角形
                                    popTriangleShow(triangle, progress, location[1], index);
                                }
                                //展示提示框
                                popwindowshow(contentview, progress, location[1], index);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }

    }


    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 8) {
            //如果不能显示的话移除所有的handler消息
            myPopWindowHandler.removeCallbacksAndMessages(null);
            if (filterpoints != null && filterpoints.size() > 0) {
                //for循环，将所有的弹出状态设置为false
                for (int i = 0; i < filterpoints.size(); i++) {
                    popwindowIsShow.set(i, false);
                }
            }
            //如果map里面有值，就将其中的popwindow 关闭
            if (linkedHashMap.size() > 0) {
                for (int i : linkedHashMap.keySet()) {
                    if (linkedHashMap.get(i) != null) {
                        linkedHashMap.get(i).dissmiss();
                    }
                }
            }
            //如果map循环里面有值，就将其中的popwindow 关闭
            if (linkedHashMap1.size() > 0) {
                for (int i : linkedHashMap1.keySet()) {
                    if (linkedHashMap1.get(i) != null) {
                        linkedHashMap1.get(i).dissmiss();
                    }
                }
            }
        }

    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
//        if (!isVisible) {
//            //如果不能显示的话移除所有的handler消息
//            myPopWindowHandler.removeCallbacksAndMessages(null);
//            if (points != null && points.size() > 0) {
//                //for循环，将所有的弹出状态设置为false
//                for (int i = 0; i < points.size(); i++) {
//                    popwindowIsShow.set(i, false);
//                }
//            }
//            //如果map里面有值，就将其中的popwindow 关闭
//            if (linkedHashMap.size() > 0) {
//                for (int i : linkedHashMap.keySet()) {
//                    if (linkedHashMap.get(i) != null) {
//                        linkedHashMap.get(i).dissmiss();
//                    }
//                }
//            }
//            //如果map循环里面有值，就将其中的popwindow 关闭
//            if (linkedHashMap1.size() > 0) {
//                for (int i : linkedHashMap1.keySet()) {
//                    if (linkedHashMap1.get(i) != null) {
//                        linkedHashMap1.get(i).dissmiss();
//                    }
//                }
//            }
//        }
    }

    /**
     * @param view     自定义的视图
     * @param progress 进度占比
     * @param v        seekbar 的纵坐标位置
     * @param index    当前是哪个点
     */

    public void popwindowshow(View view, double progress, final int v, int index) {
        //给view设置标签，判断是哪个popwindow 被点击了
        view.setTag(index);
        popWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(view)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .size((int) getResources().getDimension(R.dimen.dp_140), (int) getResources().getDimension(R.dimen.dp_40))
                .create();
        if (popWindow.getWidth() / 2 + (int) (width * progress) > width) {
            popWindow.showAtLocation(this, Gravity.NO_GRAVITY, getWidth(), v - popWindow.getHeight() - 10);
        } else if (popWindow.getWidth() / 2 > (int) (width * progress)) {
            popWindow.showAtLocation(this, Gravity.NO_GRAVITY, 0, v - popWindow.getHeight() - 10);
        } else {
            popWindow.showAtLocation(this, Gravity.NO_GRAVITY, (int) ((width * progress) - popWindow.getWidth() / 2 + getPaddingLeft() + 5), v - popWindow.getHeight() - 10);
        }
        //将弹出的popwindow和位置添加
        linkedHashMap.put(index, popWindow);
        //发送消息，5秒钟自动销毁
        if (!AliyunVodPlayerView.inSeek) {
            myPopWindowHandler.sendEmptyMessageDelayed(index, 5000);
        }
        //将popwindow的弹出状态设置为true
        popwindowIsShow.set(index, true);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPopWindowOnClick != null) {
                    onPopWindowOnClick.onPopWindowClick(view.getTag());
                }
            }
        });

    }

    public void popTriangleShow(View view, double progress, int v, int index) {
        trianglepopwindows = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(view)
                .setFocusable(false)
                .setOutsideTouchable(false)
                .create();
        linkedHashMap1.put(index, trianglepopwindows);
        trianglepopwindows.showAtLocation(this, Gravity.NO_GRAVITY, (int) (width * progress + getPaddingLeft() - trianglepopwindows.getWidth() / 2 + 5), v - 10);
    }

    public interface OnPopWindowOnClick {
        void onPopWindowClick(Object tag);
    }

    public void setOnPopWindowOnClick(OnPopWindowOnClick onPopWindowOnClick) {
        this.onPopWindowOnClick = onPopWindowOnClick;
    }

    public class MyPopWindowHandler extends Handler {
        public WeakReference<MutiPointSeekBar> mutiPointSeekBarWeakReference;

        public MyPopWindowHandler(MutiPointSeekBar seekBar) {
            mutiPointSeekBarWeakReference = new WeakReference<>(seekBar);
        }

        @Override
        public void handleMessage(Message msg) {
            MutiPointSeekBar mutiPointSeekBar = mutiPointSeekBarWeakReference.get();
            if (mutiPointSeekBar != null) {
                if (linkedHashMap1.size() > 0) {
                    for (int i : linkedHashMap1.keySet()) {
                        if (linkedHashMap1.get(i) != null && i == msg.what) {
                            linkedHashMap1.get(i).dissmiss();
                        }
                    }
                }
                if (linkedHashMap.size() > 0) {
                    for (int i : linkedHashMap.keySet()) {
                        if (linkedHashMap.get(i) != null && i == msg.what) {
                            popwindowIsShow.set(i, false);
                            linkedHashMap.get(i).dissmiss();
                        }
                    }
                }
            }
            super.handleMessage(msg);
        }
    }

    public void closeDialog() {
        if (popWindow != null) {
            myPopWindowHandler.removeCallbacksAndMessages(null);
            popWindow.dissmiss();
        }
    }
}
