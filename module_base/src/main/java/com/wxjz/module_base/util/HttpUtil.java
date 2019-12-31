package com.wxjz.module_base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.http.ReadCookiesInterceptor;
import com.wxjz.module_base.http.ReceivedCookiesInterceptor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @ClassName HttpUtil
 * @Description TODO
 * @Author liufang
 * @Date 2019-09-17 15:36
 * @Version 1.0
 */
public class HttpUtil {

    private static OkHttpClient okHttpClient;
    private volatile static HttpUtil instance;//防止多个线程同时访问

    /**
     * 懒汉式加锁单例模式
     *
     * @return
     */
    public HttpUtil() {
        okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //.addInterceptor(cacheInterceptor)
                //.addNetworkInterceptor(cacheNetWorkInterceptor)
//                .addInterceptor(new MoreBaseUrlInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor())
                 .addInterceptor(new ReadCookiesInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();
    }

    public static HttpUtil getInstance() {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if (instance == null) {
                    instance = new HttpUtil();
                }
            }
        }
        return instance;
    }


    public interface HttpCallbackListener {
        void onFinish(String response);

        void onError(Exception e);
    }

    public static void sendHttpURLRequset(final String address, final HttpCallbackListener listener) {
        if (!isNetworkAvailable()) {
            Toast toast = Toast.makeText(BaseApplication.getApplication(), "network is unavailable", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public void sendOKhttpRequest(String address, okhttp3.Callback callback) {
        Request request = new Request.Builder()
                .url(address)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void sendRequestOKhttpRequest(String address, RequestBody body, okhttp3.Callback callback) {

        Request request = new Request.Builder()
                .url(address)
                .post(body)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
