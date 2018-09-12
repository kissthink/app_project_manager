package com.xywg.pm.projectmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyinlei on 2018/7/16
 */
public class ChildrenBean implements Serializable {

    private int flag;// 1：org 2:project
    private int id;
    private int pid;
    private String label;
    private List<ChildrenBean> children;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }
}
