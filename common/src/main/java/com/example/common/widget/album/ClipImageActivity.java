package com.example.common.widget.album;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.common.Constant;
import com.example.common.R;
import com.example.common.RouterCommonPath;
import com.example.common.base.BaseActivity;
import com.example.common.base.BasePresenter;
import com.example.common.sutils.utils.EventBusUtil;


import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by zhangyinlei on 2018/3/30 16:19
 */
@Route(path = RouterCommonPath.ROUTER_COMMON_IMAGE_CLIP)
public class ClipImageActivity extends BaseActivity implements View.OnClickListener {

    private ClipImageView mClipImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void findViews() {
        this.mClipImageView = findViewById(R.id.src_pic);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_use).setOnClickListener(this);
    }

    @Override
    protected void init() {
        Uri uri = getIntent().getParcelableExtra(Constant.EXTRA_CLIP_IMAGE);
        Glide.with(this).load(uri).apply(new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE))
                .transition(withCrossFade()).into(mClipImageView);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.tv_use) {
            Bitmap bitmap = mClipImageView.rectangleClip();
            EventBusUtil.post(new MClipImageGetBitmap(bitmap));
            finish();
        }
    }

}
