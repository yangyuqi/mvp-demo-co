package com.wxjz.module_base.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.wxjz.module_base.R;

import org.litepal.LitePal;

import java.util.HashMap;

public class BaseApplication extends Application {

    private static BaseApplication application;
    private static Context context;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.colorAccent);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private static HashMap<String, Integer> colorMap;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        application = this;
        initRouter(this);
        LitePal.initialize(this);
        initColorMap();
        /**
         * 初始化讯飞
         */
        initTTS();

    }

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化讯飞
     */
    private void initTTS() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5dd35949," + SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);

    }

    private void initColorMap() {
        colorMap = new HashMap<>();
        colorMap.put("生物", R.drawable.course_shengwu_bg);
        colorMap.put("化学", R.drawable.course_huaxue_bg);
        colorMap.put("物理", R.drawable.course_wuli_bg);
        colorMap.put("地理", R.drawable.course_dili_bg);
        colorMap.put("政治", R.drawable.course_zhengzhi_bg);
        colorMap.put("语文", R.drawable.course_yuwen_bg);
        colorMap.put("数学", R.drawable.course_shuxue_bg);
        colorMap.put("英语", R.drawable.course_yingyu_bg);
        colorMap.put("历史", R.drawable.course_lishi_bg);
        colorMap.put("美术", R.drawable.course_meishu_bg);
    }

    public static HashMap<String, Integer> getColorMap() {
        return colorMap;
    }

    /**
     * 初始化 ARouter
     *
     * @param baseApplication
     */
    private void initRouter(BaseApplication baseApplication) {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(baseApplication); // 尽可能早，推荐在Application中初始化
    }

    public static BaseApplication getApplication() {
        return application;
    }

    private boolean isDebug() {
        return true;
    }

    public static Context getInstance() {
        return application;
    }
}
