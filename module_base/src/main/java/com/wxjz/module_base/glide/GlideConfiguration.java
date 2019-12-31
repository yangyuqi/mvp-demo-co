package com.wxjz.module_base.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.wxjz.module_base.constant.BasisConstants;

/**
 * Created by a on 2019/9/23.
 */

public class GlideConfiguration implements GlideModule {
    // 需要在AndroidManifest.xml中声明
    // <meta-data
    //    android:name="xxx.xxx.GlideConfiguration"
    //    android:value="GlideModule" />
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //自定义缓存目录
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,
                BasisConstants.Glide.GLIDE_CARCH_DIR,
                BasisConstants.Glide.GLIDE_CATCH_SIZE));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
