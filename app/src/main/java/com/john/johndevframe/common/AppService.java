package com.john.johndevframe.common;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.john.johndevframe.network.entity.HttpResult;
import com.john.johndevframe.network.intercepter.AcheInterceptor;

import java.util.Map;


import io.reactivex.Observable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryName;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


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

    //无网络取缓存
    @Headers(AcheInterceptor.CACHE)
    @GET("https://www.mrallen.cn/test.php")
    Observable<HttpResult<String>> simpleGetCache();

    //---------------------------以上Api过期-------------------------------------
    //--------------------------新增服务端项目------------------------------------

    //增加区域信息
    @FormUrlEncoded
    @POST("http://10.10.67.78:8086/api/superadmin/addarea")
    Observable<HttpResult<String>> addArea(@FieldMap Map<String, Object> map);

    //修改区域信息
    @FormUrlEncoded
    @POST("http://10.10.67.78:8086/api/superadmin/modifyarea")
    Observable<HttpResult<String>> modifyArea(@FieldMap Map<String, Object> map);

    //查询区域信息
    @GET("http://10.10.67.78:8086/api/superadmin/listarea")
    Observable<HttpResult<JsonArray>> getListArea();

    //根据ID查询区域信息
    @GET("http://10.10.67.78:8086/api/superadmin/getareabyid")
    Observable<HttpResult<JsonObject>> getAreaById(@Query("areaId") int id);

    //根据ID删除区域信息
    @GET("http://10.10.67.78:8086/api/superadmin/removearea")
    Observable<HttpResult<String>> removeArea(@Query("areaId") int id);

    //根据ID删除区域信息
    @GET("http://10.10.67.78:8086/api/superadmin/addpagearea")
    Observable<HttpResult<JsonArray>> queryPageArea(@Query("page") int page,@Query("size") int size);

    //单图上传
    @Multipart
    @POST("http://10.10.67.78:8086/api/testUploadFile")
    Observable<HttpResult<String>> uploadImg(@PartMap Map<String, RequestBody> map);

    //多图上传
    @Multipart
    @POST("http://10.10.67.78:8086/api/testUploadFiles")
    Observable<HttpResult<String>> uploadImgs(@PartMap Map<String, RequestBody> map);

    //文件下载
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //无网络取缓存
    @Headers(AcheInterceptor.CACHE)
    @GET("http://10.10.67.78:8086/api/superadmin/listarea")
    Observable<HttpResult<JsonArray>> getListAreaCache();

}
