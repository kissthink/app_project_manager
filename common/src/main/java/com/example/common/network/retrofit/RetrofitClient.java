package com.example.common.network.retrofit;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit2.0 客户端
 */
public class RetrofitClient {
    private Retrofit.Builder mRetrofitBuilder;
    private OkHttp mOkHttp;

    public RetrofitClient(Context context) {
        init(context);
    }

    private void init(Context context) {
        mOkHttp = new OkHttp(context);
        mRetrofitBuilder = new Retrofit.Builder()
                // 设置 OkHttpClient
                .client(mOkHttp.getOkHttpClient())
                // 添加 String 转换器
                .addConverterFactory(ScalarsConverterFactory.create())
                // 添加 Gson 转化器
                .addConverterFactory(GsonConverterFactory.create())
                // 配合 RxJava2 使用
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }
}
