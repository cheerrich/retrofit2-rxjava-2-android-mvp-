package com.example.mvp.rehelper;

public interface CallBack<T> {

    void onSuccess(T t);

    void onFail(String reason);

    void onExpired();
}