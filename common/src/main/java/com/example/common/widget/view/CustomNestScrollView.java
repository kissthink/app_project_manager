package com.example.common.widget.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * 自定义的NestedScrollView，能过获监听到滑动的高度变化
 * Created by zhangyinlei on 2018/5/19.
 */

public class CustomNestScrollView extends NestedScrollView {

    private OnScrollChangeListener mOnScrollChangeListener;

    public CustomNestScrollView(@NonNull Context context) {
        super(context);
    }

    public CustomNestScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置滚动接口
     *
     */
    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    /**
     * 定义一个滚动接口
     */

    public interface OnScrollChangeListener {
        void onScrollChanged(CustomNestScrollView scrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScrollChanged(this, l, t, oldl, oldt);

        }
    }
}
