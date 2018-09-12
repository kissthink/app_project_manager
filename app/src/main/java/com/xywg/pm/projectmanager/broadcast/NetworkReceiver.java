package com.xywg.pm.projectmanager.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.common.sutils.utils.EventBusUtil;
import com.example.common.sutils.utils.NetUtil;
import com.xywg.pm.projectmanager.message.NetWorkEvent;

/**
 * Created by zhangyinlei on 2018/8/7
 */
public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        EventBusUtil.post(new NetWorkEvent(NetUtil.isConnected()));
    }

}
