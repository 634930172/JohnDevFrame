package com.john.johndevframe.common;

import com.google.gson.JsonObject;
import com.john.johndevframe.network.entity.HttpResult;
import com.john.johndevframe.network.intercepter.AcheInterceptor;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/2 10:11
 * Description:AppService类
 */

public interface AppService {

    //Get请求
    @GET("https://www.mrallen.cn/test.php")
    Observable<HttpResult<String>> simpleGet();

    //Post请求
    @FormUrlEncoded
    @POST("https://www.mrallen.cn/api/allen_restful/rcloud/group/queryGroupMemberInfo.php")
    Observable<HttpResult<JsonObject>> simplePost(@Field("group_id") String groupId);

    //单图上传
    @Multipart
    @POST("allen_restful/upload/upload.php")
    Observable<HttpResult<JsonObject>> uploadPic(@PartMap Map<String, RequestBody> map);

    //多图上传
    @Multipart
    @POST("https://www.mrallen.cn/api/allen_restful/upload/testMuchUploadImg.php")
    Observable<HttpResult<JsonObject>> uploadPics(@PartMap Map<String, RequestBody> map);

    //文件下载
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //无网络取缓存
    @Headers(AcheInterceptor.CACHE)
    @GET("https://www.mrallen.cn/test.php")
    Observable<HttpResult<String>> simpleGetCache();


}
