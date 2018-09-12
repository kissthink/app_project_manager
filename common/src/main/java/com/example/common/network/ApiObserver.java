package com.example.common.network;

import com.example.common.network.response.HttpObserver;
import com.example.common.network.response.HttpResponseListener;


public class ApiObserver<T> extends HttpObserver<T> {

    public ApiObserver(HttpResponseListener<T> listener) {
        super(listener);
    }
}
