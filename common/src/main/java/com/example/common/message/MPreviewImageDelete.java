package com.example.common.message;

/**
 * Created by zhangyinlei on 2018/5/21 17:37
 */
public class MPreviewImageDelete {

    private int position;

    public MPreviewImageDelete(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
