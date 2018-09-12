package com.example.common.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.common.R;
import com.example.common.base.BaseApplication;
import com.example.common.sutils.utils.GlideUtil;

import java.util.List;

/**
 * ImageViewPager适配器，装载Image的
 * Created by zhangyinlei
 */
public class ImagePageAdapter extends PagerAdapter {
    private List<String> urls;

    public ImagePageAdapter(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls != null ? urls.size() : 0;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View convertView = View.inflate(BaseApplication.application, R.layout.view_image_view, null);
        ImageView iVimage = convertView.findViewById(R.id.view_image_view);
        GlideUtil.loadImageView(BaseApplication.application, urls.get(position), iVimage);
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
    }

}
