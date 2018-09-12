package com.example.common.widget.album;

import android.graphics.Bitmap;

/**
 * Created by zhangyinlei on 2018/3/30 17:07
 */
public class MClipImageGetBitmap {

    private Bitmap bitmap;

    public MClipImageGetBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
