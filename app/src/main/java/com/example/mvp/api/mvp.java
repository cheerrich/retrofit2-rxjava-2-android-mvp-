package com.example.mvp.api;


import com.example.mvp.base.BaseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface mvp {

    @FormUrlEncoded
    @POST("/xxxxxx")
    Observable<BaseBean> login(@Field("telephone") String telephone,@Field("password") String password);

    @GET("/xxxxxx")
    Observable<BaseBean> coin(@Query("token") String token);
    /**
     * 上传图片
     */
    @Multipart
    @POST("/xxxxxx")
    Observable<BaseBean> upload(@Part() MultipartBody.Part file, @PartMap Map<String, RequestBody> map);

}