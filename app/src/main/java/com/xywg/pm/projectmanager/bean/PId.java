package com.xywg.pm.projectmanager.bean;

import java.io.Serializable;

/**
 * Created by zhangyinlei on 2018/7/17
 */
public class PId implements Serializable {

    private String pid;
    private String id;

    public PId(String pid, String id) {
        this.pid = pid;
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
