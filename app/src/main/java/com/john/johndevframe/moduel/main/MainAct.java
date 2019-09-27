package com.john.johndevframe.moduel.main;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
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
    @BindView(R.id.bt3)
    Button bt3;
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
    @OnClick({R.id.bt, R.id.bt3, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9, R.id.bt10, R.id.bt11, R.id.bt12})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt:////单图上传
                mPresenter.fileUpload();
                break;
            case R.id.bt3://多文件上传
                mPresenter.fileUploads();
                break;
            case R.id.bt5://文件带进度下载
                mPresenter.fileDownLoad();
                break;
            case R.id.bt6://无网络取缓存，测试时将网络关闭
                mPresenter.simpleGetCache();
                break;
            case R.id.bt7://获取所有区域信息
                mPresenter.getListArea();
                break;
            case R.id.bt8://根据ID查询区域
                mPresenter.getListAreaById(getInt(areaId));
                break;
            case R.id.bt9://增加区域信息
                mPresenter.addArea(getString(areaName), getInt(priority));
                break;
            case R.id.bt10://修改区域信息
                mPresenter.modifyArea(getString(areaName), getInt(priority), getInt(areaId));
                break;
            case R.id.bt11://根据ID删除区域信息
                mPresenter.removeAreaById(getInt(areaId));
                break;
            case R.id.bt12://分页展示
                mPresenter.queryPageArea(getInt(areaPage), getInt(size));
                break;
        }
    }

    //------------------------------业务回调---------------------------

    /**
     * get请求回调
     */
    @Override
    public void simpleGetCallback(String str) {
        tv.setText(getString(R.string.get_success, str));
    }


    /**
     * Post请求回调
     */
    @Override
    public void simplePostCallback(JsonObject jsonObject) {
        tv.setText(getString(R.string.post_success, jsonObject));
    }


    /**
     * 单图上传回调
     */
    @Override
    public void fileUploadCallback(JsonObject jsonObject) {
        tv.setText(getString(R.string.pic_post_success, jsonObject));
    }


    /**
     * 多图上传回调
     */
    @Override
    public void fileUploadsCallback(JsonObject jsonObject) {
        tv.setText(getString(R.string.pics_post_success, jsonObject));
    }


    /**
     * 文件下载回调
     */
    @Override
    public void fileDownLoadCallback() {
        tv.setText(getString(R.string.download_success));
    }

    /**
     * 无网络取缓存回调
     */
    @Override
    public void noNetworkCacheCallback(JsonArray str) {
        tv.setText(getString(R.string.cache_get_success, str));
    }

    /**
     * 获取区域信息回调
     */
    @Override
    public void getListAreaCallback(JsonArray areas) {
        tv.setText(String.valueOf(areas));
    }

    /**
     * 根据id获取区域信息回调
     */
    @Override
    public void getAreaByIdCallback(JsonObject jsonObject) {
        tv.setText(String.valueOf(jsonObject));
    }

    /**
     * 增加区域信息回调
     */
    @Override
    public void addAreaCallback(String msg) {
        tv.setText(String.valueOf(msg));
    }

    /**
     * 更新区域信息回调
     */
    @Override
    public void modifyAreaCallback(String msg) {
        tv.setText(String.valueOf(msg));
    }

    /**
     * 根据ID删除区域信息回调
     */
    @Override
    public void removeAreaByIdCallback(String msg) {
        tv.setText(String.valueOf(msg));
    }

    /**
     * 查询区域分页信息回调
     */
    @Override
    public void queryPageAreaCallback(JsonArray jsonArray) {
        tv.setText(String.valueOf(jsonArray));
    }


    //-----------------工具类方法----------------------------

    private int getInt(EditText editText) {
        String s = editText.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return 1;
        }
        int i = Integer.parseInt(s);
        if (i < 0) {
            return 1;
        }
        return i;
    }

    private String getString(EditText editText) {
        return editText.getText().toString();
    }


}
