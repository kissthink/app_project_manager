package com.example.common.network.exception;


import com.example.common.sutils.utils.SUtils;

/**
 * 网络请求异常类
 */
public class HttpFailure {
    // 请求错误码
    private int errorCode;
    // 请求错误信息
    private String errorMsg;
    // 请求错误信息资源 id
    private int errorMsgId;

    public HttpFailure() {

    }

    public HttpFailure(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public HttpFailure(int errorCode, int errorMsgId) {
        this.errorCode = errorCode;
        this.errorMsgId = errorMsgId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorMsgId() {
        return errorMsgId;
    }

    public void setErrorMsgId(int errorMsgId) {
        this.errorMsgId = errorMsgId;
    }

    public String parseErrorMsg() {
        if (SUtils.getApp() != null && errorMsgId != 0) {
            return SUtils.getApp().getResources().getString(errorMsgId);
        } else {
            return null;
        }
    }
}
