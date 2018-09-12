package com.xywg.pm.projectmanager.util;

import android.content.Context;
import android.content.res.Resources;

import com.xywg.pm.projectmanager.R;

/**
 * Created by zhangyinlei on 2018/7/31
 */
public class ADFilterTool {

    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}
