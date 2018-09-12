package com.xywg.pm.projectmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.Constant;
import com.example.common.base.BaseActivity;
import com.example.common.sutils.utils.NetUtil;
import com.example.common.sutils.utils.ToastUtil;
import com.example.common.widget.album.CameraUtil;

import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.bean.UserInfoBean;
import com.xywg.pm.projectmanager.constant.Const;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.presenter.ILoginPresenter;
import com.xywg.pm.projectmanager.presenter.LoginPresenter;
import com.xywg.pm.projectmanager.view.CodeLineEditText;
import com.xywg.pm.projectmanager.view.LineEditText;

import java.util.List;

@Route(path = RouterPath.ROUTER_LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, ILoginPresenter.ILoginView {

    private TextView mTvRegister, mForgetPass;
    private LineEditText mEtName;
    private CodeLineEditText mEtPass;
    private LinearLayout mLlLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void findViews() {
        mTvRegister = findViewById(R.id.tv_register);
        mTvRegister.setOnClickListener(this);
        mForgetPass = findViewById(R.id.tv_forget_pass);
        mForgetPass.setOnClickListener(this);
        mEtName = findViewById(R.id.et_name);
        mEtPass = findViewById(R.id.et_pass);
        mLlLogin = findViewById(R.id.rt_login);
        mLlLogin.setOnClickListener(this);
        findViewById(R.id.take_photo).setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_register) {
            ARouter.getInstance().build(RouterPath.ROUTER_REGISTER).navigation();
        } else if (id == R.id.tv_forget_pass) {
            ARouter.getInstance().build(RouterPath.ROUTER_FORGET_PASS).navigation();
        } else if (id == R.id.rt_login) {
            String name = mEtName.getText().toString().trim();
            String pass = mEtPass.getString();
            if (TextUtils.isEmpty(name)) {
                ToastUtil.show("用户名不能为空！");
                return;
            }
            if (TextUtils.isEmpty(pass)) {
                ToastUtil.show("密码不能为空！");
                return;
            }
            if (!NetUtil.isConnected()) {
                ToastUtil.show("网络不可用！");
                return;
            }
            if (TextUtils.isEmpty(Const.Constant.alipushDeviceId)) {
                ToastUtil.show("阿里推送注册失败，请重新登录！");
                return;
            }
            presenter.login(name, pass, Const.Constant.alipushDeviceId);
        } else if (id == R.id.take_photo) {
            CameraUtil.startCamera(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 拍照
                case Constant.REQUEST_CODE_TAKE_PHOTO:
                    ARouter.getInstance().build(RouterPath.ROUTER_EDIT_PHOTO)
                            .withString(EditPhotoActivity.PHOTO_PATH, CameraUtil.getImageFile(CameraUtil.mImageCacheName).getAbsolutePath())
                            .navigation(this, Const.ConstantCode.EDIT_PHOTO_REQUEST_CODE);
                    break;
                // 获取编辑记号的图片地址
                case Const.ConstantCode.EDIT_PHOTO_REQUEST_CODE:
                    if (data.getStringExtra(EditPhotoActivity.PHOTO_DATA) != null) {
                        String path = data.getStringExtra(EditPhotoActivity.PHOTO_DATA);

                    }
                    break;
            }
        }
    }

    @Override
    public void onLoginResult(UserInfoBean userInfoBean) {
        if (null != userInfoBean) {
            List<UserInfoBean.UrlsBean> urlsBeanList = userInfoBean.getUrls();
            if (null != urlsBeanList) {
                for (UserInfoBean.UrlsBean urlsBean : urlsBeanList) {
                    if (null != urlsBean) {
                        if (urlsBean.getFlag() == 1) {
                            // 1 android 2 ios
                            Const.Constant.shareUrl = urlsBean.getUrl();
                        }
                    }
                }
            }
            ARouter.getInstance().build(RouterPath.ROUTER_MAIN)
                    .withSerializable(Const.ConstantKey.LOGIN_KEY, userInfoBean)
                    .navigation();
            finish();
        }
    }

    @Override
    public void showProgress() {
        if (null != mLoadingDialog && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        ToastUtil.show(message);
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

}
