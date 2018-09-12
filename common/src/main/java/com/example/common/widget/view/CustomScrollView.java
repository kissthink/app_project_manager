package com.example.common.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义的ScrollView，能过获监听到滑动的高度变化
 * Created by zhangyinlei on 2018/4/25.
 */

public class CustomScrollView extends ScrollView {

    private OnScrollChangeListener mOnScrollChangeListener;

    /**
     * 设置滚动接口
     *
     */
    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 定义一个滚动接口
     */

    public interface OnScrollChangeListener {
        void onScrollChanged(CustomScrollView scrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    /**
     * 当scrollView滑动时系统会调用该方法,并将该回调放过中的参数传递到自定义接口的回调方法中,
     * 达到scrollView滑动监听的效果
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScrollChanged(this, l, t, oldl, oldt);

        }
    }

}
