package com.xywg.pm.projectmanager.activity;

import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.xywg.pm.projectmanager.constant.RouterPath;

import java.util.Map;

/**
 * Created by zhangyinlei on 2018/7/27
 */
public class AliPushActivity extends AndroidPopupActivity {

    private static final String TAG = AliPushActivity.class.getSimpleName();

    @Override
    protected void onSysNoticeOpened(String title, String content, Map<String, String> extraMap) {
        Log.d(TAG, "Receive XiaoMi notification, title: " + title + ", content: " + content + ", extraMap: " + extraMap);
        ARouter.getInstance().build(RouterPath.ROUTER_LAUNCH).navigation();
        finish();
    }

}
