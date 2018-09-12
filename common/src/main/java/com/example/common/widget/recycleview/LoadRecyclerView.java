package com.example.common.widget.recycleview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

/**
 * 滑动中不加载图片得recycleviewr
 * Created by zhangyinlei on 2018/4/16.
 */

public class LoadRecyclerView extends RecyclerView {

    public LoadRecyclerView(Context context) {
        super(context);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new ImageAutoLoadScrollListener());
    }

    //监听滚动来对图片加载进行判断处理
    public class ImageAutoLoadScrollListener extends OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                    //当屏幕停止滚动，加载图片
                    try {
                        if (getContext() != null) Glide.with(getContext()).resumeRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                    //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                    try {
                        if (getContext() != null) Glide.with(getContext()).pauseRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under outside control.
                    //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    try {
                        if (getContext() != null) Glide.with(getContext()).pauseRequests();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
