package com.example.mvp;

import android.app.Application;
import android.content.Context;

public class MvpApplication extends Application {
    private static MvpApplication app;
    public static String language = "";
    public static Boolean switchPass =true;


    public static Context getAppContext() {
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

}