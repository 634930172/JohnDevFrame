package com.john.johndevframe.base;




import com.john.johndevframe.network.callback.DownloadObserver;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


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

    protected  <T> void addActSubscribe(Observable<T> observable, DisposableObserver<T> subscriber ) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivity.<T>bindUntilEvent(ActivityEvent.DESTROY))//绑定生命周期，防止内存泄露
                .subscribe(subscriber);
    }

    protected  <T> void addFragSubscribe(Observable<T> observable,DisposableObserver<T> subscriber ) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mFragment.<T>bindUntilEvent(FragmentEvent.DESTROY))//绑定生命周期，防止内存泄露
                .subscribe(subscriber);
    }

    /**
     * 文件下载
     *
     * @param downloadObserver  下载观察者类
     */
    protected void download(Observable<ResponseBody> downloadObservable, final DownloadObserver<ResponseBody> downloadObserver) {
        downloadObservable.subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        downloadObserver.saveFile(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .compose(mActivity.<ResponseBody>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(downloadObserver);
    }


}
