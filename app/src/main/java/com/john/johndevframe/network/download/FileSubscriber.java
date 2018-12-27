package com.john.johndevframe.network.download;

import android.util.Log;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/15 0015 16:02
 * <p/>
 * Description:文件回调类
 */
public class FileSubscriber<T> extends DisposableObserver<T> {

    private FileCallBack fileCallBack;
    private static final String TAG="FileSubscriber";
    public FileSubscriber(FileCallBack fileCallBack) {
        this.fileCallBack = fileCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        subscribeLoadProgress();
        if (fileCallBack != null)
            fileCallBack.onStart();
    }

    @Override
    public void onComplete() {
        unsubscribe();
        if (fileCallBack != null)
            fileCallBack.onCompleted();

    }

    @Override
    public void onError(Throwable e) {
        unsubscribe();
        if (fileCallBack != null)
            fileCallBack.onError(e);

    }

    @Override
    public void onNext(T t) {
        if (fileCallBack != null)
            fileCallBack.onSuccess(t);
    }

    /**
     * 订阅加载的进度条
     */
    private void subscribeLoadProgress() {
        Disposable disposable = RxBus.getInstance().subscribe(FileLoadEvent.class, new Consumer<FileLoadEvent>() {
            @Override
            public void accept(FileLoadEvent fileLoadEvent) {
                Log.e(TAG, "accept:---- " + fileLoadEvent.getBytesLoaded() + " --total-- " + fileLoadEvent.getTotal());
                fileCallBack.progress(fileLoadEvent.getBytesLoaded());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Log.e(TAG, "accept: error---");

            }
        });
        RxBus.getInstance().addCompositeDisposable(this, disposable);
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    private void unsubscribe() {
        RxBus.getInstance().unSubscribe(this);
    }


}
