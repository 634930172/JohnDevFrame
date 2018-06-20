package com.john.johndevframe.moduel.main;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.john.johndevframe.R;
import com.john.johndevframe.base.BaseAct;
import com.john.johndevframe.moduel.main.presenter.MainPresenter;
import com.john.johndevframe.moduel.main.view.MainView;

import butterknife.BindView;
import butterknife.OnClick;


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
    @OnClick({R.id.bt, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt://get请求
                mPresenter.SimpleGet();
                break;
            case R.id.bt2://post请求
                mPresenter.SimplePost();
                break;
            case R.id.bt3://单图上传
                mPresenter.fileUpload();
                break;
            case R.id.bt4://多图上传
                mPresenter.fileUploads();
                break;
            case R.id.bt5://文件带进度下载
                mPresenter.fileDownLoad();
                break;
            case R.id.bt6://无网络取缓存，测试时将网络关闭
                mPresenter.simpleGetCache();
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
    public void noNetworkCacheCallback(String str) {
        tv.setText(String.valueOf("无网络缓存数据: " + str));
    }


}
