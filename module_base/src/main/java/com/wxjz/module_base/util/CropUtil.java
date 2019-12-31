package com.wxjz.module_base.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;


import com.wxjz.module_base.R;
import com.wxjz.module_base.apppickimagev3.ui.MultiImageSelectorActivity;
import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.constant.BasisConstants;
import com.wxjz.module_base.constant.PermissionConstants;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yfzhu on 2017/9/19.
 * 图片剪切
 */

public class CropUtil {
    public static final int GET_PHOTO_WITHOUT_CROP = 888;
    public static final int GET_CAMERA_WITHOUT_CROP = 889;
    public static final int OPEN_PHOTO_REQUEST_CODE = 9999;
    public static final int OPEN_CAMERA_REQUEST_CODE = 10000;

    /**
     * 开启剪切页面
     *
     * @param context
     * @param sourceString
     */
    public static void startCrop(Activity context, String sourceString) {
        Uri sourceUri = Uri.fromFile(new File(sourceString));
        //裁剪后保存到文件中
        Uri destinationUri = Uri.fromFile(new File(context.getCacheDir(), System.currentTimeMillis() + "_CropImage.jpeg"));
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(context.getResources().getColor(R.color.white));
        options.setToolbarWidgetColor(context.getResources().getColor(R.color.black));
        UCrop.of(sourceUri, destinationUri).withOptions(options).start(context);
    }

    /**
     * 从相册获取图片
     *
     * @param activity
     */
    public static void openSinglePhoto(final Activity activity) {
        PermissionUtil.requestPermission(activity, new PermissionUtil.OnPermissionListener() {
            @Override
            public void onDenied() {

            }

            @Override
            public void onGranted() {
                Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
                // 是否显示调用相机拍照
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity
                // .MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                // 最多选中几张图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                activity.startActivityForResult(intent, OPEN_PHOTO_REQUEST_CODE);
            }
        }, PermissionConstants.getPermissionsFromGroup(PermissionConstants.STORAGE));
    }

    /**
     * 从相册获取图片
     *
     * @param activity
     */
    public static void openMultiPhoto(final Activity activity, final int num) {
        PermissionUtil.requestPermission(activity, new PermissionUtil.OnPermissionListener() {
            @Override
            public void onDenied() {

            }

            @Override
            public void onGranted() {
                Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
                // 是否显示调用相机拍照
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity
                // .MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                // 最多选中几张图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, num);
                activity.startActivityForResult(intent, GET_PHOTO_WITHOUT_CROP);
            }
        }, PermissionConstants.getPermissionsFromGroup(PermissionConstants.STORAGE));

    }

    /**
     * 打开相机
     */
    public static void openCameraWithOut(final Activity context, final File tempFile) {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {

            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            context.startActivityForResult(cameraIntent, OPEN_CAMERA_REQUEST_CODE);
        } else {
            ToastUtil.show(context,"没有相机");
        }
    }

    /**
     * 打开相机
     */
    public static void openCameraWithOutCrop(final Activity context, final File tempFile) {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {

            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            context.startActivityForResult(cameraIntent, GET_CAMERA_WITHOUT_CROP);
        } else {
            ToastUtil.show(context,"没有相机");
        }
    }

    /**
     * 打开相机
     */
    public static void openCamera(final Activity context, final File tempFile) {
        // 跳转到系统照相机
         Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {

            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            context.startActivityForResult(cameraIntent, OPEN_CAMERA_REQUEST_CODE);
        } else {
            ToastUtil.show(context,"没有相机");
        }
    }

    public static File createTmpFile() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 已挂载
            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new
                    Date());
            String fileName = "";
            if ("meizu".equalsIgnoreCase(Build.MANUFACTURER)) {
                fileName = pic.getAbsolutePath()
                        + File.separator + "IMG_" + timeStamp + ".jpg";
            } else {
                fileName = pic.getAbsolutePath() + BasisConstants.File.CAMERA_SHORT_PATH_ROOT
                        + File.separator + "IMG_" + timeStamp + ".jpg";
            }

            File tmpFile = new File(fileName);
            return tmpFile;
        } else {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new
                    Date());
            String appCachePath = FileUtil.getCacheDirectory(BaseApplication.getApplication(), true, true)
                    .getPath();
            appCachePath += File.separator + timeStamp + ".jpg";

            return new File(appCachePath);
        }
    }

    /**
     * 通过Uri返回File文件
     * 注意：通过相机的是类似content://media/external/images/media/231321
     * 通过相册选择的：file:///storage/sdcard0/DCIM/Camera/IMG_12312321.jpg
     * 通过查询获取实际的地址
     *
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);

            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
