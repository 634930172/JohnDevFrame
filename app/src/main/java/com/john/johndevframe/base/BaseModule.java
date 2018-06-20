package com.john.johndevframe.base;


import android.app.Activity;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: John
 * E-mail：634930172@qq.com
 * Date: 2018/1/30 10:02
 * Description:module基类 获取数据等操作
 */

public class BaseModule {

    protected BaseAct mActivity;
    protected BaseFrag mFragment;

    public BaseModule(BaseAct act){
        this.mActivity=act;
    }

    public BaseModule(BaseFrag frag){
        this.mFragment=frag;
    }

    protected  <T> void addActSubscribe(Observable<T> observable,Subscriber<T> subscriber ) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivity.<T>bindUntilEvent(ActivityEvent.DESTROY))//绑定生命周期，防止内存泄露
                .subscribe(subscriber);
    }

    protected  <T> void addFragSubscribe(Observable<T> observable,Subscriber<T> subscriber ) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragment.<T>bindUntilEvent(FragmentEvent.DESTROY))//绑定生命周期，防止内存泄露
                .subscribe(subscriber);
    }


}
