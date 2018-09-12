package com.example.common.network.request;


import com.example.common.sutils.utils.io.GsonUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 请求体处理类
 */
public class RequestBodyHandler {
    private static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * 将请求参数转化为 Json 格式提交
     *
     * @param object
     * @return RequestBody
     */
    public static RequestBody createJson(Object object) {
        if (object == null) return null;
        return RequestBody.create(MEDIATYPE_JSON, GsonUtil.obj2String(object));
    }
}
