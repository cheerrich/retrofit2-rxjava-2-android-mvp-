package com.example.mvp.base;

import android.content.Context;


public class BasePresenter<V extends BaseView, M extends BaseMode> {
    protected V mView;
    protected M mModel;
    protected Context mContext;

    public BasePresenter(Context mContext, V mView, M mModel) {
        this.mView = mView;
        this.mModel = mModel;
        this.mContext = mContext;
    }

    public void onDestroy() {
        if (mModel != null) {
            mModel.onDestroy();
        }
    }

}