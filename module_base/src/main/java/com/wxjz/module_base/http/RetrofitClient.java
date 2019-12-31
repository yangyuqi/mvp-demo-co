package com.wxjz.module_base.http;

import com.wxjz.module_base.constant.BasisConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by a on 2019/7/5.
 */

public class RetrofitClient {

    private final OkHttpClient okHttpClient;
    //private String BASE = "http://192.168.31.27:8721/";

    private String BASE_SERVICE = BasisConstants.BASE_NEWS_WEB_URL + "backend-web/auth/callback?schId=";
    private final Retrofit retrofit;
    private static RetrofitClient mInstance;

    private RetrofitClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //.addInterceptor(cacheInterceptor)
                //.addNetworkInterceptor(cacheNetWorkInterceptor)
//                .addInterceptor(new MoreBaseUrlInterceptor())
                //         .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new ReadCookiesInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        okHttpClient = builder.build();
        retrofit = new Retrofit.Builder().client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BasisConstants.BASE_NEWS_WEB_URL)
                .build();
    }


    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public String getBASE_SERVICE() {
        return BASE_SERVICE;
    }
}
