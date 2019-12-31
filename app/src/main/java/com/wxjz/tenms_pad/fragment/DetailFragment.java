package com.wxjz.tenms_pad.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxjz.module_base.base.BaseMvpFragment;
import com.wxjz.module_base.bean.CourseDetailBean;
import com.wxjz.module_base.bean.VideoDetailBean;
import com.wxjz.module_base.util.HtmlFromUtils;
import com.wxjz.tenms_pad.R;
import com.wxjz.tenms_pad.mvp.contract.DetailFragmentContract;
import com.wxjz.tenms_pad.mvp.presenter.DetailFragmentPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by a on 2019/9/20.
 */

public class DetailFragment extends BaseMvpFragment<DetailFragmentPresenter> implements DetailFragmentContract.View {
    private VideoDetailBean.DetailBean courseDetail;
    private CircleImageView iv_teacher;
    private TextView tvIntroduce;
    // private TextView tvCourseIntroduce;
    private WebView webView;

    public DetailFragment() {

    }

    @Override
    protected DetailFragmentPresenter createPresenter() {
        return new DetailFragmentPresenter();
    }

    @Override
    protected void initView(View view) {
        iv_teacher = view.findViewById(R.id.iv_teacher);
        tvIntroduce = view.findViewById(R.id.tv_introduce);
        //tvCourseIntroduce = view.findViewById(R.id.tv_course_introduce);
        webView = view.findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);//允许使用js
//不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
//不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        courseDetail = (VideoDetailBean.DetailBean) arguments.getParcelable("data");
        if (courseDetail == null) {
            return;
        }
        String teacherUrl = courseDetail.getTeacherImgUrl();
        String teacherDesc = courseDetail.getTeacherDesc();
        String courseDesc = courseDetail.getVideoDesc();
        if (TextUtils.isEmpty(courseDesc)) {
            courseDesc = "";
        }
        Glide.with(this).load(teacherUrl).error(R.drawable.default_head).into(iv_teacher);

        tvIntroduce.setText(teacherDesc);
        // HtmlFromUtils.setTextFromHtml(getActivity(), tvIntroduce, translateHTML(teacherDesc));

//        webView.loadDataWithBaseURL(null, htmlcontent, "text/html", "UTF-8", null);
        webView.loadDataWithBaseURL(null, getHtmlData(courseDesc), "text/html", "utf-8", null);//加载html数据

        //  HtmlFromUtils.setTextFromHtml(getActivity(), tvCourseIntroduce, courseDesc);

    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    public String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    public static DetailFragment getInstance() {
        return new DetailFragment();
    }

}
