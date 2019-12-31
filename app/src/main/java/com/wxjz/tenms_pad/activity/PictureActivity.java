package com.wxjz.tenms_pad.activity;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.wxjz.module_base.apppickimagev3.model.Image;
import com.wxjz.module_base.base.BaseActivity;
import com.wxjz.module_base.util.StatusBarUtil;
import com.wxjz.tenms_pad.R;

public class PictureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        StatusBarUtil.setColor(this, Color.parseColor("#50000000"),0);
        initView();
    }


    protected void initView() {
        RelativeLayout rlRoot = findViewById(R.id.rl_root);
        ImageView ivImg = findViewById(R.id.iv_img);
        String imgUrl = getIntent().getStringExtra("imgUrl");
        Glide.with(this).load(imgUrl).into(ivImg);
        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
