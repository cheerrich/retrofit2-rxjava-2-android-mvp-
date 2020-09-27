package com.example.mvp.rehelper;


import com.example.mvp.base.BaseBean;
import com.example.mvp.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;


public abstract class RxObservable<T> implements Observer<T>, CallBack<T> {
    public RxObservable() {
    }


//    /**
//     * 失败回调
//     *
//     * @param errorMsg 错误信息
//     */
//    protected abstract void onError(String errorMsg);
//
//    /**
//     * 成功回调
//     *
//     * @param data 结果
//     */
//    protected abstract void onSuccess(T data);
//


    @Override
    public void onSubscribe(Disposable s) {
        {
            LogUtils.e(s + "");
        }
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof CompositeException) {
            CompositeException compositeException = (CompositeException) t;
            t = compositeException.getExceptions().get(0);
        }
        LogUtils.e(t.toString());
        String error = RxException.handleException(t).getMessage();
        onFail(error);
    }


    @Override
    public void onNext(final T t) {
        //可以根据需求对code统一处理
        if (t != null) {
            final BaseBean responseCode = (BaseBean) t;
            LogUtils.e(responseCode.toString());
            if (responseCode.getStatusCode() == 0) {
                onSuccess(t);
            } else {
                if (responseCode.getStatusCode() == 100001) onExpired();
                onFail(responseCode.getErrorMessage());
            }
        }

    }


    @Override
    public void onComplete() {

    }
}