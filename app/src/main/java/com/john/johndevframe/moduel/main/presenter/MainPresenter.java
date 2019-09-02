package com.john.johndevframe.moduel.main.presenter;

import com.google.gson.JsonArray;
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

    //--------------------------------------------------------
    /**
     * 获取区域信息
     */
    public void getListArea(){
        mMainModelImp.getListArea(this);
    }


    /**
     * 根据ID获取区域信息
     */
    public void getListAreaById(int areaId){
        mMainModelImp.getAreaById(areaId,this);
    }

    /**
     * 增加区域信息
     */
    public void addArea(String areaName,int priority){
        mMainModelImp.addArea(areaName,priority,this);
    }

    /**
     * 修改区域信息
     */
    public void modifyArea(String areaName,int priority,int areaId){
        mMainModelImp.modifyArea(areaName,priority,areaId,this);
    }

    /**
     * 根据ID删除区域信息
     */
    public void removeAreaById(int areaId){
        mMainModelImp.removeAreaById(areaId,this);
    }

    /**
     * 获取Area分页信息
     */
    public void queryPageArea(int page,int size){
        mMainModelImp.queryPageArea(page,size,this);
    }


    //---------------------M层回调-----------------------------------


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
    public void simpleCacheDataCompleted(JsonArray data) {
        getView().noNetworkCacheCallback(data);
    }

    @Override
    public void getListAreaCompleted(JsonArray data) {
        getView().getListAreaCallback(data);
    }

    @Override
    public void getAreaByIdCompleted(JsonObject jsonObject) {
        getView().getAreaByIdCallback(jsonObject);
    }

    @Override
    public void addAreaCompleted(String msg) {
        getView().addAreaCallback(msg);
    }

    @Override
    public void modifyAreaCompleted(String msg) {
        getView().modifyAreaCallback(msg);
    }

    @Override
    public void removeAreaByIdCompleted(String msg) {
        getView().removeAreaByIdCallback(msg);
    }

    @Override
    public void queryPageAreaCompleted(JsonArray jsonArray) {
        getView().queryPageAreaCallback(jsonArray);
    }


}
