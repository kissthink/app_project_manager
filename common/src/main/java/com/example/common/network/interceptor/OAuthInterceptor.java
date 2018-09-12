package com.example.common.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 授权拦截器
 */
public class OAuthInterceptor implements Interceptor {

    public OAuthInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("Content-Type", "application/json");
        Response originalResponse = chain.proceed(requestBuilder.build());
        String cacheControl = originalRequest.cacheControl().toString();
        Response.Builder responseBuilder = originalResponse.newBuilder()
                .header("Cache-Control", cacheControl);
        return responseBuilder.build();
    }
}
