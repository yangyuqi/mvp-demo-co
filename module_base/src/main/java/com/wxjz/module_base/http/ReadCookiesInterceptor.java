package com.wxjz.module_base.http;

import android.util.Log;

import com.wxjz.module_base.util.SPCacheUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName ReadCookiesInterceptor
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-14 16:34
 * @Version 1.0
 */
public class ReadCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = SPCacheUtil.getHashSetData(SPCacheUtil.COOKIES, null);
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.e("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
        return chain.proceed(builder.build());
    }
}
