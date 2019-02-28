package com.john.johndevframe.moduel.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.john.johndevframe.R;
import com.john.johndevframe.base.BaseAct;
import com.john.johndevframe.common.AppService;
import com.john.johndevframe.moduel.main.presenter.MainPresenter;
import com.john.johndevframe.moduel.main.view.MainView;
import com.john.johndevframe.network.callback.RxRequestCallBack;
import com.john.johndevframe.network.client.HttpClient;
import com.john.johndevframe.network.entity.HttpResult;
import com.john.johndevframe.network.networkutils.UploadUtil;
import com.john.johndevframe.utils.BitmapUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/2 9:55
 * Description:主页面
 */

public class MainAct extends BaseAct<MainView, MainPresenter> implements MainView {

    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.bt3)
    Button bt3;
    @BindView(R.id.bt4)
    Button bt4;
    @BindView(R.id.bt5)
    Button bt5;
    @BindView(R.id.bt6)
    Button bt6;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.bt7)
    Button bt7;
    @BindView(R.id.bt8)
    Button bt8;
    @BindView(R.id.bt9)
    Button bt9;
    @BindView(R.id.bt10)
    Button bt10;
    @BindView(R.id.bt11)
    Button bt11;
    @BindView(R.id.bt12)
    Button bt12;
    @BindView(R.id.areaId)
    EditText areaId;
    @BindView(R.id.areaName)
    EditText areaName;
    @BindView(R.id.priority)
    EditText priority;
    @BindView(R.id.areaPage)
    EditText areaPage;
    @BindView(R.id.size)
    EditText size;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    /**
     * 点击事件 业务请求
     */
    @OnClick({R.id.bt, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9, R.id.bt10, R.id.bt11, R.id.bt12})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt://get请求
                mPresenter.SimpleGet();
                break;
            case R.id.bt2://post请求
                mPresenter.SimplePost();
                break;
            case R.id.bt3://单图上传
//                mPresenter.fileUpload();
//                Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_launcher);
//                if(bitmap==null){
//                    Log.e("TAG", "bitmap==null");
//                    return;
//                }
//                byte[] bytes = BitmapUtil.bitmapToBytes(bitmap);//拿到数组
                File  file=new File("/sdcard/Android/data/com.john.johndevframe/files/app.apk");

                UploadUtil.Builder builder = new UploadUtil.Builder().
                        addFile("file1", file);//文件上传工具类

                HttpClient.getService(AppService.class).uploadImg(builder.build()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxRequestCallBack<String>() {
                            @Override
                            public void onSuccess(HttpResult<String> httpResult) {
                                Log.e("TAG", "onSuccess: " + httpResult.getData());
                                tv.setText(httpResult.getData());
                            }
                        });

                break;
            case R.id.bt4://多图上传
//                mPresenter.fileUploads();
                File  file2=new File("/sdcard/Android/data/com.john.johndevframe/files/ic_launcher.jpg");
                File  file3=new File("/sdcard/Android/data/com.john.johndevframe/files/ic_launcher.png");
                File  file4=new File("/sdcard/Android/data/com.john.johndevframe/files/新合同2.png");

                UploadUtil.Builder manyBuilder = new UploadUtil.Builder();
                manyBuilder.addFile("file", file2);//文件上传工具类
                manyBuilder.addFile("file", file3);//文件上传工具类
                manyBuilder.addFile("file", file4);//文件上传工具类
                HttpClient.getService(AppService.class).uploadImgs(manyBuilder.build()).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxRequestCallBack<String>() {
                            @Override
                            public void onSuccess(HttpResult<String> httpResult) {
                                Log.e("TAG", "onSuccess: " + httpResult.getData());
                                tv.setText(httpResult.getData());
                            }
                        });
                break;
            case R.id.bt5://文件带进度下载 在model层
                mPresenter.fileDownLoad();
                break;
            case R.id.bt6://无网络取缓存，测试时将网络关闭 在model层
                mPresenter.simpleGetCache();
                break;
            case R.id.bt7://获取区域信息
                HttpClient.getService(AppService.class).getListArea().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxRequestCallBack<JsonArray>() {
                            @Override
                            public void onSuccess(HttpResult<JsonArray> httpResult) {
                                Log.e("TAG", "onSuccess: " + httpResult.getData());
                                tv.setText(httpResult.getData().toString());
                            }
                        });
                break;
            case R.id.bt8://ID查询区域
                HttpClient.getService(AppService.class).getAreaById(getInt(areaId)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxRequestCallBack<JsonObject>() {
                            @Override
                            public void onSuccess(HttpResult<JsonObject> httpResult) {
                                Log.e("TAG", "onSuccess: " + httpResult.getData());
                                tv.setText(httpResult.getData().toString());
                            }

                            @Override
                            protected void onFailed(HttpResult<JsonObject> jsonObjectHttpResult) {
                                tv.setText("查询失败");
                            }
                        });
                break;
            case R.id.bt9://增加区域信息
                Map<String, Object> map = new HashMap<>();
                map.put("areaName", getString(areaName));
                map.put("priority",getInt(priority));
                HttpClient.getService(AppService.class).addArea(map).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxRequestCallBack<String>() {
                            @Override
                            public void onSuccess(HttpResult<String> httpResult) {
                                Log.e("TAG", "onSuccess: " + httpResult.getData());
                                tv.setText(httpResult.getData());
                            }
                        });
                break;
            case R.id.bt10://修改区域信息
                Map<String, Object> map2 = new HashMap<>();
                map2.put("areaName", getString(areaName));
                map2.put("priority", getString(priority));
                map2.put("areaId", getInt(areaId));
                HttpClient.getService(AppService.class).modifyArea(map2).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxRequestCallBack<String>() {
                            @Override
                            public void onSuccess(HttpResult<String> httpResult) {
                                Log.e("TAG", "onSuccess: " + httpResult.getData());
                                tv.setText(httpResult.getData());
                            }

                            @Override
                            protected void onFailed(HttpResult<String> stringHttpResult) {
                                tv.setText("修改失败");
                            }
                        });
                break;
            case R.id.bt11://根据ID删除区域信息
                HttpClient.getService(AppService.class).removeArea(getInt(areaId)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxRequestCallBack<String>() {
                            @Override
                            public void onSuccess(HttpResult<String> httpResult) {
                                Log.e("TAG", "onSuccess: " + httpResult.getData());
                                tv.setText(httpResult.getData());
                            }

                            @Override
                            protected void onFailed(HttpResult<String> stringHttpResult) {
                                tv.setText("删除失败");
                            }
                        });
                break;
            case R.id.bt12://分页展示
                HttpClient.getService(AppService.class).queryPageArea(getInt(areaPage),getInt(size)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new RxRequestCallBack<JsonArray>() {
                            @Override
                            public void onSuccess(HttpResult<JsonArray> httpResult) {
                                Log.e("TAG", "onSuccess: " + httpResult.getData());
                                tv.setText(httpResult.getData().toString());
                            }
                        });

                break;
        }
    }

    //------------------------------业务回调---------------------------

    /**
     * get请求回调
     */
    @Override
    public void simpleGetCallback(String str) {
        tv.setText(String.valueOf("get成功请求返回数据: " + str));
    }


    /**
     * Post请求回调
     */
    @Override
    public void simplePostCallback(JsonObject jsonObject) {
        tv.setText(String.valueOf("post成功请求返回数据: " + jsonObject));
    }


    /**
     * 单图上传回调
     */
    @Override
    public void fileUploadCallback(JsonObject jsonObject) {
        tv.setText(String.valueOf("单图上传成功返回数据: " + jsonObject));
    }


    /**
     * 多图上传回调
     */
    @Override
    public void fileUploadsCallback(JsonObject jsonObject) {
        tv.setText(String.valueOf("多图上传成功返回数据: " + jsonObject));
    }


    /**
     * 文件下载回调
     */
    @Override
    public void fileDownLoadCallback() {
        tv.setText(String.valueOf("文件下载成功"));
    }

    /**
     * 无网络取缓存回调
     */
    @Override
    public void noNetworkCacheCallback(JsonArray str) {
        tv.setText(String.valueOf("无网络缓存数据: " + str));
    }

    //-----------------工具类方法----------------------------

    private int getInt(EditText editText){
        String s=editText.getText().toString();
        if(TextUtils.isEmpty(s)){
            return 1;
        }
        int i=Integer.parseInt(s);
        if(i<0){
            return 1;
        }
        return i;
    }

    private String getString(EditText editText){
        return editText.getText().toString();
    }

}
