package com.john.johndevframe.base;


import java.lang.ref.WeakReference;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/3/1 15:43
 * Description:
 */


public abstract class BasePresenter<V> implements Presenter<V> {

    protected WeakReference<V> mMvpView;

    @Override
    public void attachView(V mvpView) {
        this.mMvpView = new WeakReference<>(mvpView);
    }


    protected V getView() {
        return mMvpView.get();
    }


    @Override
    public void detachView() {
        if (mMvpView != null) {
            mMvpView.clear();
            mMvpView = null;
        }
    }

}
