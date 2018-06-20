package com.john.johndevframe.utils;

import android.util.Log;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2017/12/15 0015 10:00
 * <p/>
 * Description:Loger日志类
 */

public class LogUtil {

    public static final String TAG = "test";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }


}
