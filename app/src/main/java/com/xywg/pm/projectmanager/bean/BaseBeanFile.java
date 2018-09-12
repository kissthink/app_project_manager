package com.xywg.pm.projectmanager.bean;

import java.io.Serializable;

/**
 * Created by zhangyinlei on 2018/6/28
 */
public class BaseBeanFile<T> implements Serializable {


    /**
     * status : 1
     * resultData : bpims//app/1530188054299.jpg
     * message : 上传文件成功
     */

    private int status;
    private T resultData;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
