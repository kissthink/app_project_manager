package com.xywg.msc;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by zhangyinlei on 2018/7/12
 */
public class MscApplication {

    public void onCreate(Application application) {
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(application, SpeechConstant.APPID + "=5b46acd5");
    }
}
