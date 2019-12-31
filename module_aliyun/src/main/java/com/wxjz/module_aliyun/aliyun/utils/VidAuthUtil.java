package com.wxjz.module_aliyun.aliyun.utils;

import android.os.AsyncTask;

import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.VcPlayerLog;
import com.wxjz.module_aliyun.aliyun.playlist.vod.core.AliyunVodHttpCommon;
import com.wxjz.module_aliyun.aliyun.view.download.HttpClientUtil;

import org.json.JSONObject;

/**
 * Created by pengshuang on 31/08/2017.
 */
public class VidAuthUtil {


    private static final String TAG = VidAuthUtil.class.getSimpleName();

    public static VidAuth getVidAuth(String videoId) {

        try {
            //以前的连接地址"https://demo-vod.cn-shanghai.aliyuncs.com/voddemo/CreateSecurityToken?BusinessType=vodai&TerminalType=pc&DeviceModel=iPhone9,2&UUID=59ECA-4193-4695-94DD-7E1247288&AppVersion=1.0.0&VideoId=" + videoId"
            String stsUrl = AliyunVodHttpCommon.getInstance().getVodStsDomain() + "course-select/api/download/uploadFilePlayAuth?videoId="+videoId;
            String response = HttpClientUtil.doGet(stsUrl);
            JSONObject jsonObject = new JSONObject(response);

            JSONObject securityTokenInfo = jsonObject.getJSONObject("datas");
            if (securityTokenInfo == null) {

                VcPlayerLog.e(TAG, "SecurityTokenInfo == null ");
                return null;
            }

            String playAuth = securityTokenInfo.getString("playAuth");

            VcPlayerLog.e("radish", "accessKeyId = " + playAuth);
            VidAuth vidAuth = new VidAuth();
            vidAuth.setVid(videoId);
            vidAuth.setPlayAuth(playAuth);
            return vidAuth;

        } catch (Exception e) {
            VcPlayerLog.e(TAG, "e = " + e.getMessage());
            return null;
        }
    }

    public interface OnAuthResultListener {
        void onSuccess(String vid, String auth);

        void onFail();
    }

    public static void getVidAuth(final String vid, final OnAuthResultListener onAuthResultListener) {
        AsyncTask<Void, Void, VidAuth> asyncTask = new AsyncTask<Void, Void, VidAuth>() {

            @Override
            protected VidAuth doInBackground(Void... params) {
                return getVidAuth(vid);
            }

            @Override
            protected void onPostExecute(VidAuth s) {
                if (s == null) {
                    onAuthResultListener.onFail();
                } else {
                    onAuthResultListener.onSuccess(s.getVid(), s.getPlayAuth());
                }
            }
        };
        asyncTask.execute();

        return;
    }


}
