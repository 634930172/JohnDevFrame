package com.john.johndevframe.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;


import com.john.johndevframe.LeakApplication;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;


/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/1 14:59
 * Description: MVP BaseAct
 */

public abstract class BaseAct<V,P extends BasePresenter<V>> extends RxAppCompatActivity {
    public Activity mActivity;
    public P mPresenter;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        setContentView(getLayoutId());
        initView();
        initData();
        initEvent();

    }


    protected abstract int getLayoutId();

    protected abstract P createPresenter();


    /**
     * 初始化View
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
        //监控Activity类的对象
        RefWatcher refWatcher = LeakApplication.getRefWatcher();
        refWatcher.watch(this);
    }


}
