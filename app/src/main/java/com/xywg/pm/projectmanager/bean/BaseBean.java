package com.xywg.pm.projectmanager.bean;

import java.io.Serializable;

/**
 * Created by zhangyinlei on 2018/6/28
 */
public class BaseBean<T> implements Serializable {

    private int status;
    private T data;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
