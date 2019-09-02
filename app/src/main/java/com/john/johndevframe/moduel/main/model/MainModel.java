package com.john.johndevframe.moduel.main.model;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/2 10:01
 * Description:M层接口类含有的方法
 */

public interface MainModel {

    void fileUpload(LoadingCallBack callBack);

    void fileUploads(LoadingCallBack callBack);

    void downLoadFile(LoadingCallBack callBack);

    void getSimpleCacheData(LoadingCallBack callBack);

    void getListArea(LoadingCallBack callBack);

    void getAreaById(int areaId,LoadingCallBack callBack);

    void addArea(String areaName,int priority,LoadingCallBack callBack);

    void modifyArea(String areaName,int priority,int areaId,LoadingCallBack callBack);

    void removeAreaById(int areaId,LoadingCallBack callBack);

    void queryPageArea(int page,int size,LoadingCallBack callBack);

    /**
     * M层回调的接口
     */
    interface LoadingCallBack{

        void fileUploadCompleted(JsonObject jsonObject);

        void fileUploadsCompleted(JsonObject jsonObject);

        void downLoadFileCompleted();

        void simpleCacheDataCompleted(JsonArray data);

        void getListAreaCompleted(JsonArray data);

        void getAreaByIdCompleted(JsonObject jsonObject);

        void addAreaCompleted(String msg);

        void modifyAreaCompleted(String msg);

        void removeAreaByIdCompleted(String msg);

        void queryPageAreaCompleted(JsonArray jsonArray);
    }


}
