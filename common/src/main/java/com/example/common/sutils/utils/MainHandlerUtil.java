package com.example.common.sutils.utils;

import android.os.Handler;
import android.os.Looper;

public class MainHandlerUtil {
    private static Handler mHandler;

    public static Handler getMainHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }
}
