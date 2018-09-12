package com.example.common.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.common.R;
import com.example.common.dialog.base_dialog.BaseBottomDialogFragment;
import com.example.common.widget.album.AlbumUtil;
import com.example.common.widget.album.CameraUtil;

/**
 * Created by zhangyinlei on 2018/5/21 15:26
 */
@SuppressLint("ValidFragment")
public class CameraOrChooseDialog extends BaseBottomDialogFragment implements View.OnClickListener {
    private Fragment mFragment;
    private Activity mActivity;

    private int mMaxNum;

    @Override
    public void onStart() {
        setUseSizePerEnabled(true);
        setWidthPer(1.0f);
        super.onStart();
    }

    @SuppressLint("ValidFragment")
    public CameraOrChooseDialog(Fragment fragment, int maxNum) {
        this.mFragment = fragment;
        this.mMaxNum = maxNum;
    }

    public CameraOrChooseDialog(Activity mActivity, int maxNum) {
        this.mActivity = mActivity;
        this.mMaxNum = maxNum;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_camera_or_choose_dialog;
    }

    @Override
    public void bindView(View v) {
        v.findViewById(R.id.tv_camera).setOnClickListener(this);
        v.findViewById(R.id.tv_album).setOnClickListener(this);
        v.findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_camera) {
            if (null != mFragment) {
                CameraUtil.startCamera(mFragment);
            } else if (null != mActivity) {
                CameraUtil.startCamera(mActivity);
            }
            dismiss();
        } else if (id == R.id.tv_album) {
            if (null != mFragment) {
                AlbumUtil.ChooseAlbum(mFragment, mMaxNum);
            } else if (null != mActivity) {
                AlbumUtil.ChooseAlbum(mActivity, mMaxNum);
            }
            dismiss();
        } else if (id == R.id.tv_cancel) {
            dismiss();
        }
    }

}
