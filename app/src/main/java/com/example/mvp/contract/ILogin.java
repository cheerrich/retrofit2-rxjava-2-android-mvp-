package com.example.mvp.contract;


import com.example.mvp.base.BaseBean;
import com.example.mvp.base.BaseView;
import com.example.mvp.bean.LoginBean;

public interface ILogin {

    interface IPLogin {
        void Login(String teltphone,String password);

    }

    interface IVLogin extends BaseView {

        void LoginSuccess(LoginBean bean);

        void LoginError(String reason);

        void refreshButton();
    }
}