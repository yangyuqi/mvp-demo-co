package com.wxjz.module_base.http;

import android.util.Log;

import com.wxjz.module_base.util.SPCacheUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @ClassName ReceivedCookiesInterceptor
 * @Description 将登录的Cookies保存到本地
 * @Author liufang
 * @Date 2019-09-14 15:59
 * @Version 1.0
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response receivedResopnse = chain.proceed(chain.request());

        if (!receivedResopnse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : receivedResopnse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SPCacheUtil.putHashSetData(SPCacheUtil.COOKIES, cookies);
        }
        return receivedResopnse;
    }
}
