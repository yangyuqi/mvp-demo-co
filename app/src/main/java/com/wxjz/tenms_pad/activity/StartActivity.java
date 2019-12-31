package com.wxjz.tenms_pad.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxjz.module_base.base.BaseActivity;
import com.wxjz.module_base.db.bean.FirstLoadBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.FirstLoadDao;
import com.wxjz.tenms_pad.R;

/**
 * @ClassName StartActivity
 * @Description 启动页
 * @Author liufang
 * @Date 2019-10-15 15:18
 * @Version 1.0
 */
public class StartActivity extends BaseActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected boolean needEventBus() {
        return false;
    }

    @Override
    protected void initData() {
        String first = FirstLoadDao.getInstance().querrIsFirstload();
        if (TextUtils.isEmpty(first)) {
            //如果是第一登陆
            FirstLoadDao.getInstance().addFirstLoad("Second");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(StartActivity.this, GuidePagesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }, 3000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(StartActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }, 3000);
        }
    }

    @Override
    protected void initView() {
        imageView = findViewById(R.id.iv_image_start);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_start);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }
}
