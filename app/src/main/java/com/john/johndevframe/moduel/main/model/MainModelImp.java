package com.john.johndevframe.moduel.main.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.JsonObject;
import com.john.johndevframe.R;
import com.john.johndevframe.base.BaseAct;
import com.john.johndevframe.base.BaseModule;
import com.john.johndevframe.common.AppService;
import com.john.johndevframe.network.callback.RxRequestCallBack;
import com.john.johndevframe.network.client.DownLoadHttpClient;
import com.john.johndevframe.network.client.HttpClient;
import com.john.johndevframe.network.download.FileCallBack;
import com.john.johndevframe.network.download.FileLoadEvent;
import com.john.johndevframe.network.download.FileSubscriber;
import com.john.johndevframe.network.entity.HttpResult;
import com.john.johndevframe.network.networkutils.UploadUtil;
import com.john.johndevframe.utils.BitmapUtil;
import com.john.johndevframe.utils.ContextUtil;
import com.john.johndevframe.utils.LogUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;


import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;



/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/2 9:54
 * Description:M层实现类 在这里写业务逻辑，将结果回调至P层
 */

public class MainModelImp extends BaseModule implements MainModel {


    public MainModelImp(BaseAct act) {
        super(act);
    }

    /**
     * M层get请求的方法，带dialog形式请求
     */
    @Override
    public void getSimpleData(final LoadingCallBack callBack) {
        addActSubscribe(HttpClient.getService(AppService.class).simpleGet(), new RxRequestCallBack<String>(mActivity) {
            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                callBack.simpleDataCompleted(httpResult.getData());
            }
        });
    }

    /**
     * M层post请求数据的方法
     */
    @Override
    public void getPostData(final LoadingCallBack callBack) {

        String GROUP_ID = "298cea3dabeb1545004451982d6c04f6";
        addActSubscribe(HttpClient.getService(AppService.class).simplePost(GROUP_ID), new RxRequestCallBack<JsonObject>() {
            @Override
            public void onSuccess(HttpResult<JsonObject> httpResult) {
                callBack.simplePostCompleted(httpResult.getData());
            }
        });

    }

    /**
     * post单图上传
     */
    @Override
    public void fileUpload( final LoadingCallBack callBack) {
        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher);
        if(bitmap==null){
            Log.e("TAG", "bitmap==null");
            return;
        }
        byte[] bytes = BitmapUtil.bitmapToBytes(bitmap);//拿到数组
        UploadUtil.Builder builder = new UploadUtil.Builder().
                addByte("upload", bytes);//文件上传工具类
        addActSubscribe(HttpClient.getService(AppService.class).uploadPic(builder.build()), new RxRequestCallBack<JsonObject>() {
            @Override
            public void onSuccess(HttpResult<JsonObject> httpResult) {
                callBack.fileUploadCompleted(httpResult.getData());
            }
        });
    }

    /**
     * post多图上传
     */
    @Override
    public void fileUploads( final LoadingCallBack callBack) {
        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher);
        if(bitmap==null){
            Log.e("TAG", "bitmap==null");
            return;
        }
        byte[] bytes = BitmapUtil.bitmapToBytes(bitmap);//拿到数组
        UploadUtil.Builder builder = new UploadUtil.Builder();
        //多张图片
        for (int i = 0; i < 3; i++) {
            builder.addByte("image[]", bytes, i);
        }
        addActSubscribe(HttpClient.getService(AppService.class).uploadPics(builder.build()), new RxRequestCallBack<JsonObject>() {
            @Override
            public void onSuccess(HttpResult<JsonObject> httpResult) {
                callBack.fileUploadsCompleted(httpResult.getData());
            }
        });
    }

    /**
     * 文件下载
     */
    @Override
    public void downLoadFile(final LoadingCallBack callBack) {
        String fileName = "app.apk";
        File externalFilesDir = ContextUtil.getContext().getExternalFilesDir(null);//外部存储的私有目录，应用删除后此文件也会被删除
        if(externalFilesDir==null){
            return;
        }
        final FileCallBack<ResponseBody> downLoadCallback = new FileCallBack<ResponseBody>(externalFilesDir.toString(), fileName) {

            @Override
            public void onSuccess(ResponseBody responseBody) {
                callBack.downLoadFileCompleted();
            }

            @Override
            public void progress(long progress) {
                LogUtil.e("progress: " + progress / 1024 + "kb  total: " + FileLoadEvent.getInstance().getTotal() / 1024 + "kb");
            }

            @Override
            public void onStart() {
                LogUtil.e("onStart");
            }

            @Override
            public void onCompleted() {
                LogUtil.e("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    LogUtil.e("SocketTimeoutException: 网络中断，请检查您的网络状态");
                } else if (e instanceof ConnectException) {
                    LogUtil.e("ConnectException: 网络中断，请检查您的网络状态");
                } else if (e instanceof UnknownHostException) {
                    LogUtil.e("UnknownHostException: 网络中断，请检查您的网络状态");
                } else {
                    LogUtil.e("onError:其他错误：" + e.getMessage() + "  cause: " + e.getCause());
                }
                e.printStackTrace();
            }
        };
        //重写了ResponseBody的HttpClient

        String URL = "http://download.fir.im/v2/app/install/5818acbcca87a836f50014af?download_token=a01301d7f6f8f4957643c3fcfe5ba6ff";
//        String URL="http://httpbin.org/get?username=cicinnus&age=22";
        DownLoadHttpClient.getService(AppService.class).download(URL)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody){
                        downLoadCallback.saveFile(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .compose(mActivity.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new FileSubscriber<ResponseBody>(downLoadCallback));
    }


    /**
     * 无网络取缓存
     */
    @Override
    public void getSimpleCacheData(final LoadingCallBack callBack) {
        addActSubscribe(HttpClient.getService(AppService.class).simpleGetCache(), new RxRequestCallBack<String>() {
            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                callBack.simpleCacheDataCompleted(httpResult.getData());
            }
        });
    }


}
