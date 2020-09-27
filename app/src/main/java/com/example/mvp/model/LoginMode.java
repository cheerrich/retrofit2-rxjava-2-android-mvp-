package com.example.mvp.model;

import com.example.mvp.base.BaseMode;
import com.example.mvp.rehelper.RxObservable;
import com.example.mvp.utils.RxTransformerUtils;

public class LoginMode extends BaseMode {
    public void Login(String teltphone,String password,RxObservable rxObservable) {
        apiService()
                .login(teltphone,password)
                .compose(RxTransformerUtils.switchSchedulers(this))
                .subscribe(rxObservable);

    }

}
