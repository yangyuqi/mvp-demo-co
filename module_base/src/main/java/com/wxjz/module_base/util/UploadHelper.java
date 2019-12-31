package com.wxjz.module_base.util;

import android.util.Log;

import com.wxjz.module_base.constant.BasisConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by qinlei on 2018/9/3.
 */

public class UploadHelper {
    public static final String TAG = "UploadHelper";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final OkHttpClient client = new OkHttpClient();

    public String upload(File file, OnUploadListener onUploadListener) {

        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("multipartFile", file.getName(), fileBody)
                // .addFormDataPart("destFilePath",file.getName())

                .build();
        Request request = new Request.Builder()
                .url(BasisConstants.BASE_NEWS_WEB_URL + "wxm-base/OSS/uploadSingle")
                .post(requestBody)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            LogUtils.e("ql"," upload jsonString =" + jsonString);

            if (!response.isSuccessful()) {
                onUploadListener.onError();
                LogUtils.e("ql","失败");
                return null;
                //   throw new NetworkException("upload error code " + response);
            } else {
                JSONObject jsonObject = new JSONObject(jsonString);

                String imgUrl = jsonObject.getString("filePreviewPathFull");
                LogUtils.e("ql",jsonString);
                onUploadListener.onFinish(imgUrl);
                return imgUrl;
            }

        } catch (IOException e) {
            onUploadListener.onError();
            LogUtils.d(TAG, "upload IOException "+e);
        } catch (JSONException e) {
            onUploadListener.onError();
            LogUtils.d(TAG, "upload JSONException "+e);
        }
        return null;
    }

    public interface OnUploadListener {
        void onError();

        void onFinish(String imgUrl);
    }
}
