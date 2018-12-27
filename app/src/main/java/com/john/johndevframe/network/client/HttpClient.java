package com.john.johndevframe.network.client;

import com.john.johndevframe.common.BaseConfig;
import com.john.johndevframe.network.converter.HttpCovertFactory;
import com.john.johndevframe.network.intercepter.AcheInterceptor;
import com.john.johndevframe.utils.ContextUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;


import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/5 0005
 * <p/>
 * Description:
 */

public class HttpClient {

    private static final long cacheSize = 1024 * 1024 * 10;// 缓存文件最大限制大小10M
    private static String cacheDirectory = ContextUtil.getContext().//缓存路径
            getExternalCacheDir().getAbsolutePath() + "/OkHttp_Cache";
    private static Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //缓存对象

    private Retrofit retrofit;

    private static final int DEFAULT_TIMEOUT = 5;

    //构造方法私有
    private HttpClient() {
        // 创建一个OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置网络请求超时时间
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        // 失败后尝试重新请求
        builder.retryOnConnectionFailure(true);
        //----------------------------基本设置------------------------------------------------------
        builder.addInterceptor(new AcheInterceptor());//缓存拦截器
        builder.cache(cache);//缓存设置
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(HttpCovertFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseConfig.BASE_URL)
                .build();
    }

    /**
     * 调用单例对象
     */
    private static HttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 创建单例对象
     */
    private static class SingletonHolder {
        static HttpClient INSTANCE = new HttpClient();
    }

    /**
     * @return 指定service实例
     */
    public static <T> T getService(Class<T> clazz) {
        return HttpClient.getInstance().retrofit.create(clazz);
    }

}
