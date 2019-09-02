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

    //-----------------------------

    /**
     * 获取区域信息回调
     */
    void getListAreaCallback(JsonArray areas);

    /**
     * 根据Id查询区域信息
     */
    void getAreaByIdCallback(JsonObject jsonObject);

    /**
     * 增加区域信息
     */
    void addAreaCallback(String msg);

    /**
     * 修改区域信息
     */
    void modifyAreaCallback(String msg);

    /**
     * 根据Id删除区域信息
     */
    void removeAreaByIdCallback(String msg);

    /**
     * 获取Area分页信息
     */
    void queryPageAreaCallback(JsonArray jsonArray);

}
