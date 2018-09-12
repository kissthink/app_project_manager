package com.example.common.network.interceptor;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 日志拦截器
 */
public class LoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 这个 chain 里面包含了 request 和 response，所以你要什么都可以从这里拿
        // =========== 发送 ===========
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String reqBodyStr = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            reqBodyStr = buffer.readString(charset);
        }
        // 打印发送信息
        Log.d(this.getClass().getSimpleName(),
                String.format("发送请求 ===>  \nmethod：%s\nheaders: %s\nurl：%s\nbody：%s",
                        request.method(), request.headers(), request.url(), reqBodyStr));

        //=========== 接收 ===========
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        String respBodyStr = null;
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        respBodyStr = buffer.clone().readString(charset);
        // 打印接收信息
        Log.d(this.getClass().getSimpleName(),
                String.format("收到响应 ===>  \n延迟时间：%s\n响应码：%s\n响应信息: %s\n响应body：%s",
                        tookMs, response.code(), response.message(), respBodyStr));
        return response;
    }
}
