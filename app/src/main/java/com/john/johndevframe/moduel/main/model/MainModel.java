package com.john.johndevframe.moduel.main.model;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.john.johndevframe.base.BaseAct;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/2 10:01
 * Description:M层接口类含有的方法
 */

public interface MainModel {

    void getSimpleData(LoadingCallBack callBack);

    void getPostData(LoadingCallBack callBack);

    void fileUpload(LoadingCallBack callBack);

    void fileUploads(LoadingCallBack callBack);

    void downLoadFile(LoadingCallBack callBack);

    void getSimpleCacheData(LoadingCallBack callBack);

    /**
     * M层回调的接口
     */
    interface LoadingCallBack{

        void simpleDataCompleted(String data);

        void simplePostCompleted(JsonObject jsonObject);

        void fileUploadCompleted(JsonObject jsonObject);

        void fileUploadsCompleted(JsonObject jsonObject);

        void downLoadFileCompleted();

        void simpleCacheDataCompleted(String data);

    }


}
