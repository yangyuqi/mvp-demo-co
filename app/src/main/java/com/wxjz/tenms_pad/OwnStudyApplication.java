package com.wxjz.tenms_pad;

import com.aliyun.private_service.PrivateService;
import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.constant.BasisConstants;

/**
 * Created by a on 2019/9/6.
 */

public class OwnStudyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        PrivateService.initService(getApplicationContext(), BasisConstants.Aliyun.CACHE_PATH + "/encrypted/encryptedApp.dat");
    }
}
