package com.john.johndevframe.moduel.main.model;

import android.util.Log;

import com.google.gson.JsonArray;

import com.google.gson.JsonObject;
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
import com.john.johndevframe.utils.AssetUtils;
import com.john.johndevframe.utils.ContextUtil;
import com.john.johndevframe.utils.LogUtil;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

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

    private static final String TAG="MainModelImp";

    public MainModelImp(BaseAct act) {
        super(act);
    }

    /**
     * post单图上传
     */
    @Override
    public void fileUpload(final LoadingCallBack callBack) {
        File file = AssetUtils.getFile("java从入门到放弃.png");
        Log.d(TAG, "fileUpload: path is "+file.getAbsolutePath());
        UploadUtil.Builder builder = new UploadUtil.Builder().
                addFile("file1", file);//文件上传工具类
        addActSubscribe(HttpClient.getService(AppService.class).uploadImg(builder.build()), new RxRequestCallBack<JsonObject>() {
            @Override
            public void onSuccess(HttpResult<JsonObject> httpResult) {
                Log.e("TAG", "onSuccess: " + httpResult.getData());
                callBack.fileUploadCompleted(httpResult.getData());
            }
        });
    }

    /**
     * post多文件上传
     */
    @Override
    public void fileUploads(final LoadingCallBack callBack) {
        File file2 = AssetUtils.getFile("仙道1.jpeg");
        File file3 = AssetUtils.getFile("仙道2.png");
        File file4 = AssetUtils.getFile("仙道3.jpg");

        UploadUtil.Builder manyBuilder = new UploadUtil.Builder();
        manyBuilder.addFile("file", file2);//文件上传工具类
        manyBuilder.addFile("file", file3);//文件上传工具类
        manyBuilder.addFile("file", file4);//文件上传工具类
        addActSubscribe(HttpClient.getService(AppService.class).uploadImgs(manyBuilder.build()), new RxRequestCallBack<JsonObject>() {
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
        String fileName = "app-download.apk";
        final File externalFilesDir;//外部存储的私有目录，应用删除后此文件也会被删除
        externalFilesDir = ContextUtil.getContext().getExternalFilesDir(null);
        if (externalFilesDir == null) {
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
        //        String URL = "http://download.fir.im/v2/app/install/5818acbcca87a836f50014af?download_token=a01301d7f6f8f4957643c3fcfe5ba6ff";
        //        String URL="http://httpbin.org/get?username=cicinnus&age=22";
        String URL2 = "http://94.191.50.122/demo/testDownload";
        DownLoadHttpClient.getService(AppService.class).download(URL2)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
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
        addActSubscribe(HttpClient.getService(AppService.class).getListAreaCache(), new RxRequestCallBack<JsonArray>() {
            @Override
            public void onSuccess(HttpResult<JsonArray> httpResult) {
                callBack.simpleCacheDataCompleted(httpResult.getData());
            }
        });
    }

    //---------------------------------------------------------

    /**
     * 获取所有区域信息
     */
    @Override
    public void getListArea(final LoadingCallBack callBack) {
        addActSubscribe(HttpClient.getService(AppService.class).getListArea(), new RxRequestCallBack<JsonArray>() {
            @Override
            public void onSuccess(HttpResult<JsonArray> httpResult) {
                callBack.getListAreaCompleted(httpResult.getData());
            }
        });
    }

    /**
     * 根据id查询Area
     */
    @Override
    public void getAreaById(int areaId, final LoadingCallBack callBack) {
        addActSubscribe(HttpClient.getService(AppService.class).getAreaById(areaId), new RxRequestCallBack<JsonObject>() {
            @Override
            public void onSuccess(HttpResult<JsonObject> httpResult) {
                callBack.getAreaByIdCompleted(httpResult.getData());
            }
        });
    }

    /**
     * 增加Area
     */
    @Override
    public void addArea(String areaName, int priority, final LoadingCallBack callBack) {
        Map<String, Object> map = new HashMap<>();
        map.put("areaName", areaName);
        map.put("priority", priority);
        addActSubscribe(HttpClient.getService(AppService.class).addArea(map), new RxRequestCallBack<String>() {
            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                callBack.addAreaCompleted(httpResult.getMsg());
            }
        });
    }

    /**
     * 修改Area
     */
    @Override
    public void modifyArea(String areaName, int priority, int areaId, final LoadingCallBack callBack) {
        Map<String, Object> map2 = new HashMap<>();
        map2.put("areaName", areaName);
        map2.put("priority", priority);
        map2.put("areaId", areaId);
        addActSubscribe(HttpClient.getService(AppService.class).modifyArea(map2), new RxRequestCallBack<String>() {
            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                callBack.modifyAreaCompleted(httpResult.getMsg());
            }
        });

    }

    /**
     * 根据ID删除Area
     */
    @Override
    public void removeAreaById(int areaId, final LoadingCallBack callBack) {
        addActSubscribe(HttpClient.getService(AppService.class).removeAreaById(areaId), new RxRequestCallBack<String>() {
            @Override
            public void onSuccess(HttpResult<String> httpResult) {
                callBack.removeAreaByIdCompleted(httpResult.getMsg());
            }
        });

    }

    /**
     * 展示分页信息
     */
    @Override
    public void queryPageArea(int page, int size, final LoadingCallBack callBack) {
        addActSubscribe(HttpClient.getService(AppService.class).queryPageArea(page, size), new RxRequestCallBack<JsonArray>() {
            @Override
            public void onSuccess(HttpResult<JsonArray> httpResult) {
                callBack.queryPageAreaCompleted(httpResult.getData());
            }
        });
    }

}
