package com.example.common.network.retrofit;


import com.example.common.sutils.utils.SUtils;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * 网络请求工具类
 */
public class HttpUtil {
    private static volatile HttpUtil INSTANCE;
    // Retrofit2.0 客户端
    private RetrofitClient mRetrofitClient;
    private Retrofit mRetrofit;
    // 订阅统一管理类
    private CompositeDisposable mDisposableSite;

    private HttpUtil() {
        init();
    }

    public static HttpUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpUtil();
                }
            }
        }
        return INSTANCE;
    }

    private void init() {
        mRetrofitClient = new RetrofitClient(SUtils.getApp().getApplicationContext());
        mDisposableSite = new CompositeDisposable();
    }

    /**
     * 设置域名
     *
     * @param baseUrl
     */
    public void setBaseUrl(String baseUrl) {
        mRetrofitClient.getRetrofitBuilder().baseUrl(baseUrl);
        mRetrofit = mRetrofitClient.getRetrofitBuilder().build();
    }

    /**
     * 创建服务
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> cls) {
        return mRetrofit.create(cls);
    }

    /**
     * 订阅 Api 请求
     *
     * @param observable 被观察者
     * @param observer   观察者
     */
    public void subscribeApi(Observable observable, DisposableObserver observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        if (mDisposableSite == null) {
            mDisposableSite = new CompositeDisposable();
        }
        mDisposableSite.add(observer);
    }

    /**
     * 清除所有的订阅关系
     */
    public void clear() {
        if (mDisposableSite != null) {
            mDisposableSite.clear();
        }
    }
}
