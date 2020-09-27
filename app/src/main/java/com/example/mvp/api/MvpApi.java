package com.example.mvp.api;


import com.example.mvp.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mvp.api.Url.BASE_URL;


public class MvpApi {
    private static Retrofit mRetrofit;
    private static volatile OkHttpClient sOkHttpClient;
    //超时时间
    private static final int CONN_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 60;

    /**
     * Retrofit初始化
     *
     * @return
     */
    public static Retrofit createApi() {
        if (mRetrofit == null) {
            synchronized (MvpApi.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .client(getOkHttpClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BASE_URL)
                            .build();
                }
            }
        }
        return mRetrofit;
    }

    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            sOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)
// =                   .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getContext())))
                    .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return sOkHttpClient;
    }

    public static void clearOkHttpClient() {
        sOkHttpClient=null;
        mRetrofit=null;
    }

    public static class HttpLogger implements HttpLoggingInterceptor.Logger {

        private StringBuilder mMessage = new StringBuilder();

        @Override
        public void log(String message) {
            /// 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.delete(0, mMessage.length());
            }
            mMessage.append(message.concat("\n"));
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                try {
                    LogUtils.e(message.format("mMessage=%s", mMessage));
                } catch (Exception e) {
                    LogUtils.e("erro---" + e.getMessage());
                }
                mMessage.delete(0, mMessage.length());
            }
        }
    }
}