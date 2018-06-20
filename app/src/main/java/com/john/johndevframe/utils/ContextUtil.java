package com.john.johndevframe.utils;

import android.app.Application;
import android.content.Context;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2016/11/11 16:46
 * <p/>
 * Description: application_context 工具类
 */
public class ContextUtil {
    private static Context APPLICATION_CONTEXT;

    /* Public Methods */

    /**
     * 初始化context，如果由于不同机型导致反射获取context失败可以在Application调用此方法
     */
    public static void init(Context context) {
        APPLICATION_CONTEXT = context;
    }

    /**
     * 反射获取 application context
     */
    public static Context getContext() {
        if (null == APPLICATION_CONTEXT) {
            try {
                Application application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null, (Object[]) null);
                if (application != null) {
                    APPLICATION_CONTEXT = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class.forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication")
                        .invoke(null, (Object[]) null);
                if (application != null) {
                    APPLICATION_CONTEXT = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new IllegalStateException("ContextHolder is not initialed, it is recommend to init with application context.");
        }
        return APPLICATION_CONTEXT;
    }
}
