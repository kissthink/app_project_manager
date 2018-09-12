package com.example.common.network.response;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.example.common.network.exception.ExceptionHandler;
import com.example.common.network.progress.LoadingDlgFmt;


import io.reactivex.observers.DisposableObserver;

/**
 * Retrofit2.0 中使用的观察者基类
 */
public class HttpObserver<T> extends DisposableObserver<T> {
    // 请求标记
    private int mSign;
    //    private Disposable mDisposable;
    // 响应回调
    private HttpResponseListener<T> mListener;
    private Context mContext;
    private LoadingDlgFmt loadingDlgFmt;

    public HttpObserver(HttpResponseListener<T> listener) {
        this.mListener = listener;
    }

    public HttpObserver setSign(int sign) {
        this.mSign = sign;
        return this;
    }

    public HttpObserver showLoading(Context context) {
        createLoading(context, null);
        return this;
    }

    public HttpObserver showLoading(Context context, String loadingMsg) {
        createLoading(context, loadingMsg);
        return this;
    }

    /**
     * 创建加载对话框
     *
     * @param context
     * @param loadingMsg
     */
    private void createLoading(Context context, String loadingMsg) {
        mContext = context;
        loadingDlgFmt = new LoadingDlgFmt();
        if (!TextUtils.isEmpty(loadingMsg)) {
            loadingDlgFmt.setMessage(loadingMsg);
        }
        loadingDlgFmt.setCancelCallback(new LoadingDlgFmt.OnCancelCallback() {
            @Override
            public void onCancel() {
                cancel();
            }
        });
    }

    /**
     * 显示加载对话框
     */
    private void showLoading() {
        if (mContext != null && loadingDlgFmt != null) {
            loadingDlgFmt.show((FragmentActivity) mContext);
        }
    }

    /**
     * 关闭加载对话框
     */
    private void closeLoading() {
        if (loadingDlgFmt != null) {
            loadingDlgFmt.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showLoading();
    }

//    @Override
//    public void onSubscribe(Disposable d) {
//        this.mDisposable = d;
//        showLoading();
//    }

    @Override
    public void onNext(T t) {
        if (mListener != null) {
            mListener.onSuccess(mSign, t);
        }
    }

    @Override
    public void onError(Throwable e) {
        closeLoading();
        if (mListener != null) {
            mListener.onFailure(mSign, ExceptionHandler.parseError(e));
        }
    }

    @Override
    public void onComplete() {
        closeLoading();
        cancel();
    }

    /**
     * 取消订阅，如果请求未完成，请求也会被取消
     */
    public void cancel() {
        // 如果处于订阅状态，则取消订阅
        //  if (mDisposable != null && !mDisposable.isDisposed()) {
        //      mDisposable.dispose();
        //  }
        if (!isDisposed()) {
            dispose();
        }
    }
}
