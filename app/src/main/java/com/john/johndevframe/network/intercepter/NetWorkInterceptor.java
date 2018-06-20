package com.john.johndevframe.network.intercepter;

import android.support.annotation.NonNull;


import com.john.johndevframe.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/4 0004 17:43
 * <p/>
 * Description:网络请求拦截器
 */

public class NetWorkInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.e(">>>>>>NetWorkInterceptor:"+request.method());
        return  chain.proceed(request);
    }
}
