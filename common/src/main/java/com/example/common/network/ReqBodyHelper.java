package com.example.common.network;


import com.example.common.network.request.RequestBodyHandler;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * 请求体帮助类
 */
public class ReqBodyHelper {

    public static RequestBody createJson(Map map) {
        if (map == null) return null;
        map.put("access_token", "5TyL8PmU7cX49YtUI3QwPmTw530vbYnF");
        return RequestBodyHandler.createJson(map);
    }
}
