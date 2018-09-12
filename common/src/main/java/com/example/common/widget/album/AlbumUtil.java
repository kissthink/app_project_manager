package com.example.common.widget.album;

import android.Manifest;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;

import com.example.common.Constant;
import com.example.common.sutils.utils.AppUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import io.reactivex.functions.Consumer;

/**
 * Created by zhangyinlei on 2018/5/19 13:35
 */
public class AlbumUtil {

    public static void ChooseAlbum(final Activity activity, final int maxSelect) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Matisse.from(activity)
                            .choose(MimeType.allOf())
                            .countable(false)
                            .capture(false)
                            .maxSelectable(maxSelect)
                            .captureStrategy(new CaptureStrategy(true, AppUtil.getAppInfo().packageName + ".fileprovider"))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new com.example.common.widget.album.GlideEngine())
                            .forResult(Constant.REQUEST_CODE_CHOOSE);
                }
            }
        });
    }

    public static void ChooseAlbum(final Fragment fragment, final int maxSelect) {
        RxPermissions rxPermissions = new RxPermissions(fragment.getActivity());
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Matisse.from(fragment)
                            .choose(MimeType.allOf())
                            .countable(false)
                            .capture(false)
                            .maxSelectable(maxSelect)
                            .captureStrategy(new CaptureStrategy(true, AppUtil.getAppInfo().packageName + ".fileprovider"))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new com.example.common.widget.album.GlideEngine())
                            .forResult(Constant.REQUEST_CODE_CHOOSE);
                }
            }
        });
    }

}
