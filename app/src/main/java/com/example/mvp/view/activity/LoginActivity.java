package com.example.mvp.view.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.mvp.R;
import com.example.mvp.base.BaseActivity;
import com.example.mvp.bean.LoginBean;
import com.example.mvp.contract.ILogin;
import com.example.mvp.presenter.LoginPresenter;
import com.example.mvp.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILogin.IVLogin {
    @BindView(R.id.et_telephone)
    AppCompatEditText etTelephone;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    public void initView() {
        //监听输入
        mPresenter.textListener(etTelephone);
        mPresenter.textListener(etPassword);
    }

    @Override
    public int setViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void createPresenter() {
        mPresenter = new LoginPresenter(this, this);
    }

    @Override
    public void LoginSuccess(LoginBean bean) {
        //登录成功
    }

    @Override
    public void LoginError(String reason) {
        //登录失败
        showToast(reason);
    }

    @Override
    public void refreshButton() {
        //判断按钮是否可以点击
    }

    @Override
    public void showLoading() {
        //加载loading
    }

    @Override
    public void hideLoading() {
        //关闭loading
    }

    @Override
    public void loginExpired() {
        //登录失效处理
    }


    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        //判断符合条件请求登录
        if (!StringUtils.IsStringNull(etTelephone.getText().toString()) && !StringUtils.IsStringNull(etPassword.getText().toString())) {
            mPresenter.Login(etTelephone.getText().toString(), etPassword.getText().toString());
        }
    }

    //可以重写接受eventbus通知的方法.
    @Override
    protected void acceptMessage(int code, Object object) {
        super.acceptMessage(code, object);
    }
}
