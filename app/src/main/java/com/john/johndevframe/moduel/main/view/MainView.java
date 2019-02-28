package com.john.johndevframe.moduel.main.view;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/2 9:55
 * Description:V层回调接口
 */

public interface MainView {
    /**
     * get请求回调
     */
    void simpleGetCallback(String str);

    /**
     * post请求回调
     */
    void simplePostCallback(JsonObject jsonObject);

    /**
     * 单图上传回调
     */
    void fileUploadCallback(JsonObject jsonObject);

    /**
     * 多图上传回调
     */
    void fileUploadsCallback(JsonObject jsonObject);

    /**
     * 文件下载回调
     */
    void fileDownLoadCallback();

    /**
     * 无网络取缓存回调
     */
    void noNetworkCacheCallback(JsonArray str);
}
