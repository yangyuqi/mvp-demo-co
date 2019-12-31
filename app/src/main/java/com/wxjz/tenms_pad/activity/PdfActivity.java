package com.wxjz.tenms_pad.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxjz.module_base.base.BaseActivity;
import com.wxjz.module_base.base.BaseMvpActivity;
import com.wxjz.module_base.mvp.IBasePresenter;
import com.wxjz.tenms_pad.R;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class PdfActivity extends BaseMvpActivity implements DownloadFile.Listener {

    private TextView tvTitleName;
    private PDFPagerAdapter adapter;
    private RemotePDFViewPager remotePDFViewPager;
    private RelativeLayout remotePdfRoot;
    private TextView tv_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean needEventBus() {
        return false;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("pdf_url");
        String title = getIntent().getStringExtra("title");
        tvTitleName.setText(title);
        remotePDFViewPager = new RemotePDFViewPager(this, url, this);
        //remotePDFViewPager.setId(R.id.pdfViewPager);
    }

    @Override
    protected void initView() {
        tv_index = findViewById(R.id.tv_index);
        tvTitleName = findViewById(R.id.tv_title_name);
        ImageView iv_back = findViewById(R.id.iv_back);
        remotePdfRoot = findViewById(R.id.remote_pdf_root);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdf;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.close();
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        hideLoading();
        adapter = new PDFPagerAdapter(this, destinationPath);
        remotePDFViewPager.setAdapter(adapter);
        // setContentView(remotePDFViewPager);
        remotePdfRoot.removeAllViewsInLayout();
        remotePdfRoot.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int currentItem = remotePDFViewPager.getCurrentItem();
     //   final int childCount = remotePDFViewPager.getChildCount();
        final int childCount =   remotePDFViewPager.getAdapter().getCount();
        tv_index.setVisibility(View.VISIBLE);
        tv_index.setText((currentItem + 1) + "/" + childCount);
        remotePDFViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tv_index.setText((i + 1) + "/" + childCount);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onFailure(Exception e) {
        hideLoading();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        showLoading();
    }
}
