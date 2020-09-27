package com.example.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.example.mvp.bean.EventBean;
import com.example.mvp.view.view.ToastView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.mvp.api.MessageCode.EXIT_APP;


public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity {
    public Context mContext;
    public T mPresenter;
    private Unbinder mBind;
    public Bundle mSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        mContext = this;
        if (setViewId() != 0) {
            setContentView(setViewId());
        } else {
            throw new RuntimeException("layoutRes==-1");
        }
        createPresenter();
        mBind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    /**
     * 初始化方法
     */

    public abstract void initView();

    /**
     * 获取contentView 资源id
     */
    public abstract int setViewId();

    /**
     * 创建presenter实例
     */
    public abstract void createPresenter();

    /**
     * activity跳转（无参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className) {
        Intent intent = new Intent(mContext, className);
        startActivity(intent);
    }

    /**
     * 吐司
     *
     * @param text
     */
    public void showToast(String text) {
        ToastView.showToast(text);
        //  Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    /**
     * activity跳转（有参数）
     *
     * @param className
     */
    public void startActivity(Class<?> className, Bundle bundle) {
        Intent intent = new Intent(mContext, className);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /*
     *通知
     */
    protected void acceptMessage(int code, Object object) {
       if(code==EXIT_APP){
           if(!isFinishing()){
              finish();
           }
       }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void simpleEventBus(EventBean userEvent) {
        acceptMessage(userEvent.getCode(), userEvent.getJob());
    }

    EventBean eventBean;

    //发送消息
    public void sendMessage(int code, Object o) {
        if (eventBean == null) {
            eventBean = new EventBean();
        }
        eventBean.setCode(code);
        eventBean.setJob(o);
        EventBus.getDefault().postSticky(eventBean);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();//页面销毁 网络请求同销毁
        }
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}