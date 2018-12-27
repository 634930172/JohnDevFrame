package com.john.johndevframe.network.download;

import android.util.Log;
import java.util.HashMap;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/15 0015 16:09
 * <p/>
 * Description:基于RxJava2的RxBus,支持背压
 */

public class RxBus {

    private static final String TAG = "RxBus";
    private static volatile RxBus mInstance;
    private FlowableProcessor<Object> mBus;
    private HashMap<String, CompositeDisposable> mSubscriptionMap;

    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     * Subject同时充当了Observer和Observable的角色，Subject是非线程安全的，要避免该问题，
     * 需要将 Subject转换为一个 SerializedSubject ，上述RxBus类中把线程非安全的PublishSubject包装成线程安全的Subject。
     */
    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
    }

    /**
     * 单例 双重锁
     */
    public static RxBus getInstance() {
        if (mInstance == null) {
            synchronized (RxBus.class) {
                if (mInstance == null) {
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送一个新的事件
     */
    public void post(Object o) {
        mBus.onNext(o);
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public  <T> Flowable<T> toFlowable(Class<T> type) {
        //ofType操作符只发射指定类型的数据，其内部就是filter+cast
        return mBus.ofType(type);
    }

    public <T> Disposable subscribe(Class<T> type, Consumer<T> next,Consumer<Throwable> err) {
        return toFlowable(type)
                .onBackpressureBuffer()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, err);
    }

    public void addCompositeDisposable(Object object, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = object.getClass().getName();
        if (mSubscriptionMap.get(key) != null) {
            mSubscriptionMap.get(key).add(disposable);
        } else {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(disposable);
            mSubscriptionMap.put(key, compositeDisposable);
            //  Log.e("air", "addSubscription:订阅成功 " );
        }
    }

    public void unSubscribe(Object object) {
        if (mSubscriptionMap == null) {
            return;
        }
        String key = object.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (mSubscriptionMap.get(key) != null && !mSubscriptionMap.get(key).isDisposed()) {
            mSubscriptionMap.get(key).dispose();
            Log.d(TAG, "unSubscribe event success");
        }
        mSubscriptionMap.remove(key);
    }
}

