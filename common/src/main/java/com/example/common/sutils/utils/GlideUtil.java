package com.example.common.sutils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Glide v4+工具类
 */
public class GlideUtil {

    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */

    /**
     * V4版本
     * listener()
     * thumbnail()
     * load()
     * into()，使用RequestBuilder
     * centerCrop()
     * placeholder()
     * error()
     * priority()
     * diskCacheStrategy()，使用RequestOptions
     * Transformations 现在会替换之前设置的任何变换
     */

    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).into(mImageView);
    }

    //默认加载,bitmap
    public static void loadImageView(Context mContext, Bitmap bitmap, ImageView mImageView) {
        Glide.with(mContext).load(bitmap).into(mImageView);
    }

    //加载指定大小
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().override(width, height))
                .into(mImageView);
    }

    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int loadingImage, int errorImageView) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().placeholder(loadingImage).error(errorImageView))
                .into(mImageView);
    }

    //设置圆形图片
    public static void loadImageRound(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path)
                .apply(RequestOptions.circleCropTransform())
                .into(mImageView);
    }

    //设置圆形图片
    public static void loadImageRound(Context mContext, int resId, ImageView mImageView) {
        Glide.with(mContext).load(resId)
                .apply(RequestOptions.circleCropTransform())
                .into(mImageView);
    }

    //设置圆形图片跳过缓存
    public static void loadImageRoundSkipCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path)
                .apply(RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .into(mImageView);
    }

    //设置圆角图片
    public static void loadImageRoundCorner(Context mContext, String path, ImageView mImageView, int roundingRadius) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundingRadius)))
                .into(mImageView);
    }

    //设置圆角图片
    public static void loadImageRoundCorner(Context mContext, Bitmap bitmap, ImageView mImageView, int roundingRadius) {
        Glide.with(mContext).load(bitmap)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundingRadius)))
                .into(mImageView);
    }

    //设置圆角图片跳过缓存
    public static void loadImageRoundCornerSkipCache(Context mContext, String path, ImageView mImageView, int roundingRadius) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(roundingRadius)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
                .into(mImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int loadingImage, int errorImageView) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().override(width, height).placeholder(loadingImage).error(errorImageView))
                .into(mImageView);
    }

    //设置跳过内存缓存
    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().skipMemoryCache(true))
                .into(mImageView);
    }

    //设置跳过磁盘缓存
    public static void loadImageViewDisk(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(mImageView);
    }

    //设置跳过磁盘和内存缓存
    public static void loadImageViewCacheAndDisk(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(mImageView);
    }

    //设置下载优先级
    public static void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path)
                .apply(new RequestOptions().priority(Priority.NORMAL))
                .into(mImageView);
    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}
