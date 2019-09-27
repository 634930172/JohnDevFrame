package com.john.johndevframe.network.callback;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;


import com.john.johndevframe.network.entity.HttpResult;
import com.john.johndevframe.network.networkutils.LoadingDialog;
import com.john.johndevframe.utils.LogUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.observers.DisposableObserver;


/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/7 0007
 * <p/>
 * Description:
 */

public abstract class CallbackObserver<T> extends DisposableObserver<HttpResult<T>> implements DialogInterface.OnCancelListener {

    private LoadingDialog dialog;
    private static final String TAG="RxRequestCallBack";
    private long mStartCurrentLong;
    /**
     * 网络请求成功的回调方法，必须重写
     */
    public abstract void onSuccess(HttpResult<T> httpResult);

    /**
     * 默认访问请求
     */
    protected CallbackObserver() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mStartCurrentLong=System.currentTimeMillis();
        if (dialog != null) {
            dialog.show();
            LogUtil.e("dialogShow");
        }
    }

    /**
     * 请求有进度框
     */
    protected CallbackObserver(Context context) {
        super();
        dialog = new LoadingDialog(context);
        dialog.setOnCancelListener(this);
    }

    /**
     * 请求有进度框
     */
    public CallbackObserver(Context context, String loadingMsg) {
        super();
        dialog = new LoadingDialog(context, loadingMsg);
        dialog.setOnCancelListener(this);
    }

    @Override
    public void onComplete() {
        dismissDialog();
        Log.e(TAG, "onCompleted: " );
    }

    @Override
    public void onError(Throwable e) {
        dismissDialog();
        if (e instanceof SocketTimeoutException) {
            Log.e(TAG,"SocketTimeoutException: 网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            Log.e(TAG,"ConnectException: 网络中断，请检查您的网络状态");
        } else if (e instanceof UnknownHostException) {
            Log.e(TAG,"UnknownHostException: 网络中断，请检查您的网络状态");
        } else {
            Log.e(TAG,"onError:其他错误：" + e.getMessage() + "  cause: " + e.getCause());
        }
        e.printStackTrace();
    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        Log.e(TAG,"onNext:   code--" + tHttpResult.getCode() + "--msg--" + tHttpResult.getMsg());
        if (tHttpResult.getCode() == 401) {
            Log.e(TAG,"onNext:  Json_error");
        } else if (tHttpResult.getCode() != 200) {
            onFailed(tHttpResult);
            Log.e(TAG,"onNext:  onFailed");
        } else {
            onSuccess(tHttpResult);
            long callBackTime=System.currentTimeMillis()-mStartCurrentLong;
            Log.e(TAG,"onNext:  onSuccess , callback time： "+callBackTime+" ms");
        }
    }

    protected void onFailed(HttpResult<T> tHttpResult) {
    }


    private void dismissDialog() {
        //延时消除dialog，为了演示效果  实际项目中可去掉
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    Log.e(TAG,"dialogDismiss");
                }
            }
        },3000);


    }

    /**
     * dialog消失的时候取消订阅
     */
    @Override
    public void onCancel(DialogInterface dialogInterface) {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }


}
