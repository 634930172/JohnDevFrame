package com.john.johndevframe.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.john.johndevframe.LeakApplication;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/1 14:59
 * Description: MVP BaseFrag
 */

public abstract class BaseFrag<V,P extends BasePresenter<V>> extends RxFragment {
    public Activity mActivity;
    protected View mRootView;
    protected P mPresenter;
    private Unbinder unbinder;

    protected abstract int getLayoutId();

    protected abstract P createPresenter();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initView();
        initData();
        initEvent();
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        mActivity = getActivity();
    }


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
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
        //监控Fragment对象
        RefWatcher refWatcher = LeakApplication.getRefWatcher();
        refWatcher.watch(this);
    }

}
