package com.john.johndevframe.network.client;

import com.john.johndevframe.common.BaseConfig;
import com.john.johndevframe.network.converter.HttpCovertFactory;
import com.john.johndevframe.network.download.ProgressInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/5 0005
 * <p/>
 * Description:带进度的下载类
 */

public class DownLoadClient {

    private Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 60 * 5;//下载超时时间为5分钟

    //构造方法私有
    private DownLoadClient() {
        // 创建一个OkHttpClient
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        // 设置网络请求超时时间
        okHttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //实现进度回调的拦截器
//        okHttpBuilder.addInterceptor(new ProgressInterceptor());
        // 失败后尝试重新请求
        okHttpBuilder.retryOnConnectionFailure(true);
        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(HttpCovertFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseConfig.BASE_URL)
                .build();
    }

    /**
     * 调用单例对象
     */
    private static DownLoadClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 创建单例对象
     */
    private static class SingletonHolder {
        static DownLoadClient INSTANCE = new DownLoadClient();
    }


    /**
     * @return 指定service实例
     */
    public static <T> T getService(Class<T> clazz) {
        return DownLoadClient.getInstance().retrofit.create(clazz);
    }



}
