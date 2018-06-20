package com.john.johndevframe.moduel.main.presenter;

import com.google.gson.JsonObject;
import com.john.johndevframe.base.BaseAct;
import com.john.johndevframe.base.BasePresenter;
import com.john.johndevframe.moduel.main.model.MainModel;
import com.john.johndevframe.moduel.main.model.MainModelImp;
import com.john.johndevframe.moduel.main.view.MainView;


/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/2 9:57
 * Description:P层
 */

public class MainPresenter extends BasePresenter<MainView> implements MainModel.LoadingCallBack {

    private MainModel mMainModelImp;

    public MainPresenter(BaseAct act) {
        mMainModelImp = new MainModelImp(act);
    }

    //------------------------V层请求-------------------------------

    /**
     * 普通get请求
     */
    public void SimpleGet() {
        mMainModelImp.getSimpleData(this);
    }

    /**
     * 普通post请求
     */
    public void SimplePost() {
        mMainModelImp.getPostData(this);
    }


    /**
     * 单图上传上传
     */
    public void fileUpload() {
        mMainModelImp.fileUpload(this);
    }

    /**
     * 多图片上传
     */
    public void fileUploads() {
        mMainModelImp.fileUploads(this);
    }

    /**
     * 文件带进度下载
     */
    public void fileDownLoad() {
        mMainModelImp.downLoadFile(this);
    }

    /**
     * 无网络取缓存
     */
    public void simpleGetCache() {
        mMainModelImp.getSimpleCacheData(this);
    }

    //---------------------M层回调-----------------------------------

    /**
     *
     */
    @Override
    public void simpleDataCompleted(String data) {
        getView().simpleGetCallback(data);
    }

    @Override
    public void simplePostCompleted(JsonObject jsonObject) {
        getView().simplePostCallback(jsonObject);
    }

    @Override
    public void fileUploadCompleted(JsonObject jsonObject) {
        getView().fileUploadCallback(jsonObject);
    }

    @Override
    public void fileUploadsCompleted(JsonObject jsonObject) {
        getView().fileUploadsCallback(jsonObject);
    }

    @Override
    public void downLoadFileCompleted() {
        getView().fileDownLoadCallback();
    }

    @Override
    public void simpleCacheDataCompleted(String data) {
        getView().noNetworkCacheCallback(data);
    }


}
