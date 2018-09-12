package com.example.common.network.response;


import com.example.common.network.exception.HttpFailure;

/**
 * 网络请求回调响应接口
 */
public interface HttpResponseListener<T> {
    /**
     * 网络请求成功
     *
     * @param what 请求的标记
     * @param t    返回的数据
     */
    void onSuccess(int what, T t);

    /**
     * 网络请求失败
     *
     * @param what    请求的标记
     * @param failure 请求失败时，返回的信息类
     */
    void onFailure(int what, HttpFailure failure);
}
