package com.example.mvp.presenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.mvp.R;
import com.example.mvp.base.BasePresenter;
import com.example.mvp.bean.LoginBean;
import com.example.mvp.contract.ILogin;
import com.example.mvp.model.LoginMode;
import com.example.mvp.rehelper.RxObservable;
import com.example.mvp.utils.StringUtils;

public class LoginPresenter extends BasePresenter<ILogin.IVLogin, LoginMode> implements ILogin.IPLogin {

    public LoginPresenter(Context mContext, ILogin.IVLogin mView) {
        super(mContext, mView, new LoginMode());
    }

    @Override
    public void Login(String teltphone, String password) {
        //网络请求前加载loading
        mView.showLoading();
        mModel.Login(teltphone, password, new RxObservable() {

            @Override
            public void onSuccess(Object o) {
                //请求返回关闭loading
                mView.hideLoading();
                mView.LoginSuccess((LoginBean) o);
            }

            @Override
            public void onFail(String reason) {
                //请求返回关闭loading
                mView.hideLoading();
                mView.LoginError(reason);
            }

            @Override
            public void onExpired() {
                //网络错误关闭loading
                mView.hideLoading();
                mView.loginExpired();
            }
        });
    }

    //监听输入
    public void textListener(final TextView view) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mView.refreshButton();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}
