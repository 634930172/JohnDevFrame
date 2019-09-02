package com.john.johndevframe.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.john.johndevframe.network.entity.HttpResult;
import com.john.johndevframe.network.intercepter.AcheInterceptor;

import java.util.Map;
import io.reactivex.Observable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/2 10:11
 * Description:AppService类
 */

public interface AppService {

    //查询区域信息
    @GET("superadmin/listarea")
    Observable<HttpResult<JsonArray>> getListArea();

    //增加区域信息
    @FormUrlEncoded
    @POST("superadmin/addarea")
    Observable<HttpResult<String>> addArea(@FieldMap Map<String, Object> map);

    //修改区域信息
    @FormUrlEncoded
    @POST("superadmin/modifyarea")
    Observable<HttpResult<String>> modifyArea(@FieldMap Map<String, Object> map);


    //根据ID查询区域信息
    @GET("superadmin/getareabyid")
    Observable<HttpResult<JsonObject>> getAreaById(@Query("areaId") int id);

    //根据ID删除区域信息
    @GET("superadmin/removearea")
    Observable<HttpResult<String>> removeAreaById(@Query("areaId") int id);

    //分页展示
    @GET("superadmin/addpagearea")
    Observable<HttpResult<JsonArray>> queryPageArea(@Query("page") int page,@Query("size") int size);

    //单图上传
    @Multipart
    @POST("testUploadFile")
    Observable<HttpResult<JsonObject>> uploadImg(@PartMap Map<String, RequestBody> map);

    //多图上传
    @Multipart
    @POST("testUploadFiles")
    Observable<HttpResult<JsonObject>> uploadImgs(@PartMap Map<String, RequestBody> map);

    //文件下载
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //无网络取缓存
    @Headers(AcheInterceptor.CACHE)
    @GET("superadmin/listarea")
    Observable<HttpResult<JsonArray>> getListAreaCache();

}
