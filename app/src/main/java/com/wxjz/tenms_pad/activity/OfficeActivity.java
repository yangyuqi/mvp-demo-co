package com.wxjz.tenms_pad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.mvp.IBasePresenter;
import com.wxjz.tenms_pad.R;

public class OfficeActivity extends BaseMvpActivity {

    private WebView webView;
    private String miscsoft = "https://view.officeapps.live.com/op/view.aspx?src=";
    private TextView tvTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_office;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        webView = findViewById(R.id.webview);
        ImageView iv_back = findViewById(R.id.iv_back);
        tvTitleName = findViewById(R.id.tv_title_name);
        webView.setWebViewClient(new AppWebViewClients());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public class AppWebViewClients extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        String url = getIntent().getStringExtra("pdf_url");
        String title = getIntent().getStringExtra("title");
        tvTitleName.setText(title);
        webView.loadUrl(miscsoft + url);

    }
}
