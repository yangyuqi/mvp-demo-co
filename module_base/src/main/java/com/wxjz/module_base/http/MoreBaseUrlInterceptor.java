package com.wxjz.module_base.http;

import android.util.Log;

import com.wxjz.module_base.http.RetrofitClient;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName MoreBaseUrlInterceptor
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-17 10:29
 * @Version 1.0
 */
public class MoreBaseUrlInterceptor implements Interceptor {
    private String BASE = "http://192.168.31.27:8721/";
    private String BASE_Login = "http://edu.k12c.com/";

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原始的originalRequest
        Request originalRequest;
        originalRequest = chain.request();
        //获取originalRequest的创建者builder
        Request.Builder builder = originalRequest.newBuilder();
        //获取头信息的集合如：manage,mdffx
        List<String> urlnameList = originalRequest.headers("url_name");
        if (urlnameList != null && urlnameList.size() > 0) {
            //删除原有配置中的值,就是namesAndValues集合里的值
            builder.removeHeader("url_name");
            //获取头信息中配置的value,如：manage或者mdffx
            String urlname = urlnameList.get(0);
            HttpUrl baseURL = null;
            //根据头信息中配置的value,来匹配新的base_url地址
            if ("base".equals(urlname)) {
                baseURL = HttpUrl.parse("http://192.168.31.27:8721/");
            } else if ("login".equals(urlname)) {
                baseURL = HttpUrl.parse("http://edu.k12c.com/");
            }
            //获取老的url
            HttpUrl oldUrl = originalRequest.url();
            //重建新的HttpUrl，需要重新设置的url部分
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .port(baseURL.port())//端口
                    .removePathSegment(0)//移除第一个参数
                    .build();
            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();
            Log.d("okHttp", originalRequest.toString());
            return chain.proceed(newRequest);
        } else {
            Log.d("okHttp", originalRequest.toString());
            return chain.proceed(originalRequest);
        }

    }
}
