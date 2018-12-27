package com.john.johndevframe;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/12/27 13:52
 * <p/>
 * Description:
 */
public class LeakApplication extends Application {

    private static RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = setupLeakCanary();
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

}
