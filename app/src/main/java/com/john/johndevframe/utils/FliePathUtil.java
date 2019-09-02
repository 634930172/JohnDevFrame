package com.john.johndevframe.utils;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/2/2 9:27
 * Description:数据库存储路径工具类
 */

public final class FliePathUtil {

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    private FliePathUtil() {
    }

    /**
     * 数据库文件缓存在SD卡私有目录或者系统目录，应用被删除后此方法生成的文件也被删除
     */
    public static File getIndividualCacheDirectory(Context context, String cacheDir, String fileName) {
        File appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        File dbPath=new File(individualCacheDir,fileName);
        if (!individualCacheDir.exists()) {//定义数据库的文件包目录
            if (!individualCacheDir.mkdir()) {
                return null;
            }
        }
        if(!dbPath.exists()){//创建数据库文件
            try {
                if(!dbPath.createNewFile()){
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return dbPath;
    }

    private static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    private static File getCacheDirectory(Context context, boolean preferExternal) {
        File appCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) { // (sh)it happens (Issue #660)
            externalStorageState = "";
        } catch (IncompatibleClassChangeError e) { // (sh)it happens too (Issue #989)
            externalStorageState = "";
        }
        // 外部存储私有缓存目录 /storage/emulated/0/Android/data/com.john.testproject/cache
        if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context);
        }
        //内部缓存目录 /data/data/com.john.testproject/cache
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        //内部缓存目录 /data/data/com.john.testproject/cache 和上一种差不多 之前有/ 现在没有了
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }
    /**
     * 外部存储私有缓存目录
     */
    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
            }
        }
        return appCacheDir;
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}