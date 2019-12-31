package com.wxjz.module_base.util;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.db.bean.HistoryBean;
import com.wxjz.module_base.db.dao.CheckGradeDao;
import com.wxjz.module_base.db.dao.ClassifyDao;
import com.wxjz.module_base.db.dao.HistoryDBDao;
import com.wxjz.module_base.db.dao.UserInfoDao;
import com.wxjz.module_base.event.ExitEvent;
import com.wxjz.module_base.http.RetrofitClient;
import com.wxjz.module_base.listener.LoginListener;
import com.wxjz.module_base.widgt.ActionSheetDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by a on 2019/9/19.
 */

public class SystemManager {
    private static SystemManager mSystemManager;

    public static SystemManager getInstance() {
        if (mSystemManager == null) {
            synchronized (SystemManager.class) {
                mSystemManager = new SystemManager();
            }
        }
        return mSystemManager;
    }

    /**
     * 退出登录
     */
    public void exit(final Activity activity) {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(activity)
                .builder();
        actionSheetDialog.setTitle("确定退出登录？");
        actionSheetDialog.addSheetItem("退出登录", ActionSheetDialog.SheetItemColor.Red, new
                ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        loginOut();
                        activity.finish();
                    }
                });
        actionSheetDialog.show();

    }

    private void loginOut() {

        SPCacheUtil.clearSpCache();
        ClassifyDao.getInstence().clear();
        HistoryDBDao.getInstence().clearSearchHistory();
        UserInfoDao.getInstence().deleteUserInfo();
        CheckGradeDao.getInstance().clear();
        EventBus.getDefault().post(new ExitEvent());
    }

    public void login(String name, String password, final int schoolId, final LoginListener loginListener) {
        RequestBody body = new FormBody.Builder()
                .add("username", name)
                .add("password", password)
                .add("schId", String.valueOf(schoolId))
                .build();
        HttpUtil.getInstance().sendRequestOKhttpRequest(BasisConstants.BASE_NEWS_WEB_URL + "cas/v1/tickets", body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loginListener.onLoginFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if (json.indexOf("TGT") != -1) {
                    //说明获取TGT成功
                    gotoRequest2(json, loginListener,schoolId);
                } else {
                    //说明登录失败
                    loginListener.onLoginFailed();
                }
            }
        });
    }

    /**
     * 再请求接口
     *  @param json
     * @param loginListener
     * @param schoolId
     */
    private void gotoRequest2(String json, final LoginListener loginListener, final int schoolId) {
        String service = RetrofitClient.getInstance().getBASE_SERVICE() + schoolId;
        String s = tosplit(json);
        if (!TextUtils.isEmpty(s)) {
            RequestBody body = new FormBody.Builder()
                    .add("service", service)
                    .build();
            //如果不为空，再请求
            HttpUtil.getInstance().sendRequestOKhttpRequest(BasisConstants.BASE_NEWS_WEB_URL + "cas/v1/tickets/" + s, body, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    loginListener.onLoginFailed();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    if (json.indexOf("ST") != -1) {
                        //说明获取ST成功
                        getRequest3(schoolId, json, loginListener);
                    } else {
                        //说明获取ST失败
                        loginListener.onLoginFailed();
                    }
                }
            });
        } else {
            loginListener.onLoginFailed();
        }
    }

    /**
     * 拿到ST去请求接口
     *
     * @param schoolId
     * @param json
     * @param loginListener
     */
    private void getRequest3(int schoolId, String json, final LoginListener loginListener) {
        final String url = BasisConstants.BASE_NEWS_WEB_URL + "backend-web/auth/callback?schId=" + schoolId + "&ticket=" + json;
        if (schoolId != 0) {
            HttpUtil.getInstance().sendOKhttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    loginListener.onLoginFailed();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    if (json.indexOf("window.location.href = Util.loadUrl") != -1) {
                        //说明登录成功，拿到Cookies
                        loginListener.loginSuccess();
                        ;
                    } else {
                        loginListener.onLoginFailed();
                    }
                    Log.d("OkHTTP", json + "\n" + url);
                }
            });
        } else {
            loginListener.onLoginFailed();
        }
    }

    /**
     * 分割强取TGT
     *
     * @param json
     */
    private String tosplit(String json) {

        String[] strings = json.split("action=");
        if (strings.length > 1) {
            String[] strings1 = strings[1].split("\"");
            if (strings1.length > 1) {
                String[] strings2 = strings1[1].split("/");
                if (strings2.length > 5) {
                    String s = strings2[6];
                    if (s.indexOf("TGT") != -1) {
                        return s;
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

}
