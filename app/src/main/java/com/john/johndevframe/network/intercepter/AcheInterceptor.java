package com.john.johndevframe.network.intercepter;

import android.support.annotation.NonNull;


import com.john.johndevframe.network.networkutils.NetworkUtil;
import com.john.johndevframe.utils.ContextUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/4 0004 17:42
 * <p/>
 * Description:有网取最新数据,无网取缓存，缓存时间7天;只对get请求做缓存处理
 * 在需要的get接口添加注解@Headers(AcheInterceptor.CACHE)
 */

public class AcheInterceptor implements Interceptor {
    public static final String CACHE = "Cache-Control:max-stale=" + (60 * 60 * 24 * 7);//离线缓存7天

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("GET")) {
            if (!NetworkUtil.isNetworkConnected(ContextUtil.getContext())) {
                request = request.newBuilder()
                        .cacheControl(new CacheControl.Builder().onlyIfCached()
                                .maxStale(request.cacheControl().maxStaleSeconds(), TimeUnit.SECONDS).build())
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetworkUtil.isNetworkConnected(ContextUtil.getContext())) {
                response.newBuilder()
                        .header("Cache-Control", "Cache-Control:,max-age=0")
                        .removeHeader("Pragma")
                        .build();
            } else {
                response.newBuilder()
                        .header("Cache-Control", "Cache-Control:public, only-if-cached")
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        }
        return chain.proceed(request);
    }
}
