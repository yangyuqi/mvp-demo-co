package com.wxjz.tenms_pad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxjz.module_base.base.BaseActivity;
import com.wxjz.module_base.util.HtmlFromUtils;
import com.wxjz.tenms_pad.R;

public class RichTextActivity extends BaseActivity {

    private TextView tv_title_name;
    private WebView webView;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean needEventBus() {
        return false;
    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", 1);
        String web_url = getIntent().getStringExtra("web_url");
        String rich_text = getIntent().getStringExtra("rich_text");
        if (type == 1) {
            //链接型
            webView.loadUrl(web_url);
        } else {
            webView.loadDataWithBaseURL(null, getHtmlData(rich_text), "text/html", "utf-8", null);//加载html数据
            tv_title_name.setText(title);

        }
        initSetting();
    }

    private void initSetting() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (type==1){
                    tv_title_name.setText(title);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
    @Override
    protected void initView() {
        tv_title_name = findViewById(R.id.tv_title_name);
        ImageView iv_back = findViewById(R.id.iv_back);
        webView = findViewById(R.id.webview);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rich_text;
    }
}
