package com.example.mvp.base;


import com.example.mvp.api.mvp;
import com.example.mvp.api.MvpApi;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseMode {


    public CompositeDisposable mDisposable = new CompositeDisposable();

    /**
     * 初始化调用网络请求
     * @return
     */
    public mvp apiService() {
        return MvpApi.createApi().create(mvp.class);
    }
    /**
     * 取消网络请求
     */
    public void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable.clear();
        }
    }

}