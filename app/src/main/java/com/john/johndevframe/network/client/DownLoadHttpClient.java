package com.john.johndevframe.network.client;

import android.util.Log;


import com.john.johndevframe.common.BaseConfig;
import com.john.johndevframe.network.converter.HttpCovertFactory;
import com.john.johndevframe.network.download.ProgressInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/5 0005
 * <p/>
 * Description:带进度的下载类
 */

public class DownLoadHttpClient {

    private Retrofit retrofit;

    private static final int DEFAULT_TIMEOUT = 5;

    //构造方法私有
    private DownLoadHttpClient() {
        // 创建一个OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置网络请求超时时间
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        // 失败后尝试重新请求
        builder.retryOnConnectionFailure(true);
        //----------------------------基本设置------------------------------------------------------
        builder.addInterceptor(new ProgressInterceptor());//设置有下载进度的拦截器 后面定制改动


        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(HttpCovertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BaseConfig.BASE_URL)
                .build();
        Log.e("TAG", "DownLoadHttpClient: >>>>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * 调用单例对象
     */
    private static DownLoadHttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 创建单例对象
     */
    private static class SingletonHolder {
        static DownLoadHttpClient INSTANCE = new DownLoadHttpClient();
    }

    /**
     * @return 指定service实例
     */
    public static <T> T getService(Class<T> clazz) {
        return DownLoadHttpClient.getInstance().retrofit.create(clazz);
    }

}
