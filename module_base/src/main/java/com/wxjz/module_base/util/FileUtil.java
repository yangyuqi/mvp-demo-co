package com.wxjz.module_base.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.wxjz.module_base.base.BaseApplication;
import com.wxjz.module_base.constant.BasisConstants;

import org.litepal.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by a on 2019/9/23.
 */

public class FileUtil {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission" +
            ".WRITE_EXTERNAL_STORAGE";

    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> (if card is mounted and app has
     * appropriate permission) or
     * on device's file system depending incoming parameters.
     *
     * @param context        Application context
     * @param preferExternal Whether prefer external location for cache 是否存储在扩展存储卡
     * @param persist        Whether persist cache 是否持续存储
     * @return Cache {@link File directory}.<br />
     * <b>NOTE:</b> Can be null in some unpredictable cases (if SD card is unmounted and
     * {@link android.content.Context#getCacheDir() Context.getCacheDir()} returns null).
     */
    public static File getCacheDirectory(Context context, boolean preferExternal, boolean persist) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        }
        //判断扩展存储卡是否存储有效
        if (preferExternal && android.os.Environment.MEDIA_MOUNTED.equals(externalStorageState)
                && hasExternalStoragePermission(context)) {
            if (persist) {
                //获得扩展存储卡缓存目录wismcp/包/cache
                appCacheDir = getExternalPersistCacheDir(context);
            } else {
                //获得扩展存储卡缓存目录 Android/data/包/cache
                appCacheDir = getExternalCacheDir(context);
            }
        }
        //如上都不行获取应用缓存目录
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        //自动获取应用缓存失败则手动创建
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    /**
     * 获得扩展存储卡缓存目录wxjz/包/cache
     * <br>
     * 目录会创建.nomedia文件
     *
     * @param context
     * @return
     */
    private static File getExternalPersistCacheDir(Context context) {
        File dataDir = new File(BasisConstants.Aliyun.CACHE_PATH);
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.e("ql", "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.e("ql", "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    /**
     * 获得扩展存储卡缓存目录 Android/data/包/cache
     * <br>
     * 目录会创建.nomedia文件
     *
     * @param context
     * @return
     */
    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"),
                "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.e("ql", "Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i("ql", "Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    public static String getCacheSize(String path) {
        try {
            return getFormatSize(getFolderSize(new File(path)));
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    // 获取指定文件夹内所有文件大小的和
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    // 格式化单位
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // 按目录删除文件夹文件方法
    public static boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (File file1 : files) {
                    deleteFolderFile(file1.getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    if (file.listFiles().length == 0) {
                        file.delete();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取sdcard剩余内存
     *
     * @return 单位b
     */
    public static long getSdcardAvailableSize() {

        File directory = Environment.getExternalStorageDirectory();

        StatFs statFs = new StatFs(directory.getPath());
        //获取可供程序使用的Block数量
        long blockAvailable = statFs.getAvailableBlocks();
        //获得Sdcard上每个block的size
        long blockSize = statFs.getBlockSize();

        return blockAvailable * blockSize;
    }

    /**
     * 获取sdcard剩余内存
     *
     * @return 单位b
     */
    public static long getSdcardTotalSize() {

        File directory = Environment.getExternalStorageDirectory();

        StatFs statFs = new StatFs(directory.getPath());
        long totalSize = statFs.getTotalBytes();
        return totalSize;
    }

}
