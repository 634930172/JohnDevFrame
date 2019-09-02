package com.john.johndevframe;

import android.app.Application;

import com.john.johndevframe.utils.AssetUtils;
import com.john.johndevframe.utils.ContextUtil;
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
        ContextUtil.init(this);
        //插入测试的图片
        AssetUtils.saveFileFromAsset("java从入门到放弃.png");
        AssetUtils.saveFileFromAsset("仙道1.jpeg");
        AssetUtils.saveFileFromAsset("仙道2.png");
        AssetUtils.saveFileFromAsset("仙道3.jpg");
        //改变了22222222223333333333333344444444445555555
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
