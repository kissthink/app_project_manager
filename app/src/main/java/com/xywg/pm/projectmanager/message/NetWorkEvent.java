package com.xywg.pm.projectmanager.message;

/**
 * Created by zhangyinlei on 2018/8/7
 */
public class NetWorkEvent {
    private boolean netIsWork;

    public NetWorkEvent(boolean netIsWork) {
        this.netIsWork = netIsWork;
    }

    public boolean isNetIsWork() {
        return netIsWork;
    }

    public void setNetIsWork(boolean netIsWork) {
        this.netIsWork = netIsWork;
    }
}
