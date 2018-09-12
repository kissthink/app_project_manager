package com.xywg.pm.projectmanager.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.base.BaseActivity;
import com.example.common.dialog.ConfirmDialogFragment;
import com.example.common.listener.OnPositiveClickListener;
import com.example.common.sutils.utils.ToastUtil;
import com.example.common.sutils.utils.io.FileUtil;
import com.xywg.pm.projectmanager.MyApplication;
import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.constant.Const;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.presenter.ISettingPresenter;
import com.xywg.pm.projectmanager.presenter.SettingPresenter;
import com.xywg.pm.projectmanager.view.MyHeadView;

import static com.xywg.pm.projectmanager.constant.Const.ConstantPath.AUDIO_PATH;
import static com.xywg.pm.projectmanager.constant.Const.ConstantPath.PIC_PATH;

/**
 * Created by zhangyinlei on 2018/7/17
 */
@Route(path = RouterPath.ROUTER_SETTING)
public class SettingActivity extends BaseActivity<SettingPresenter> implements ISettingPresenter.ISettingView, View.OnClickListener {

    private android.widget.LinearLayout llrecommend;
    private android.widget.LinearLayout llabout;
    private android.widget.LinearLayout llclear;
    private android.widget.LinearLayout llsignout;
    private TextView mTvName;

    private TextView mTvCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected void findViews() {
        MyHeadView myHeadView = findViewById(R.id.head_view);
        myHeadView.setLeftIconClickListerner(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.llsignout = (LinearLayout) findViewById(R.id.ll_sign_out);
        this.llsignout.setOnClickListener(this);
        this.llclear = (LinearLayout) findViewById(R.id.ll_clear);
        this.llclear.setOnClickListener(this);
        this.llabout = (LinearLayout) findViewById(R.id.ll_about);
        this.llabout.setOnClickListener(this);
        this.llrecommend = (LinearLayout) findViewById(R.id.ll_recommend);
        this.llrecommend.setOnClickListener(this);
        this.mTvCache = findViewById(R.id.tv_cache);
        this.mTvName = findViewById(R.id.tv_name);
    }

    @Override
    protected void init() {
        if (getIntent() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("欢迎您");
            if (!TextUtils.isEmpty(Const.Constant.mUserName))
                stringBuilder.append(": ").append(Const.Constant.mUserName);
            mTvName.setText(stringBuilder.toString());
        }
        calcu();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_recommend) {
            ARouter.getInstance().build(RouterPath.ROUTER_RECOMMEND).navigation();
        } else if (id == R.id.ll_about) {
            ARouter.getInstance().build(RouterPath.ROUTER_ABOUT).navigation();
        } else if (id == R.id.ll_clear) {
            ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
            confirmDialogFragment.setTitle("是否删除缓存")
                    .setCancelClick("取消", null)
                    .setConfirmClick("确定", new OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick(View view, String content) {
                            clearCache();
                            calcu();
                        }
                    }).show(getSupportFragmentManager(), "");
        } else if (id == R.id.ll_sign_out) {
            ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
            confirmDialogFragment.setTitle("退出")
                    .setCancelClick("取消", null)
                    .setConfirmClick("确定", new OnPositiveClickListener() {
                        @Override
                        public void onPositiveClick(View view, String content) {
                            clearCookies(SettingActivity.this);
                            MyApplication.application.finishAllActivities();
                            ARouter.getInstance().build(RouterPath.ROUTER_LOGIN).navigation();
                        }
                    }).show(getSupportFragmentManager(), "");
        }
    }

    /**
     * 计算大小
     */
    private void calcu() {
        long all;
        long pic = FileUtil.getInstance().getFileSize(PIC_PATH);
        long audio = FileUtil.getInstance().getFileSize(AUDIO_PATH);
        all = pic + audio;
        String size = FileUtil.convertFileSize(all);
        mTvCache.setText(size);
    }

    private void clearCache() {
        boolean pic = FileUtil.getInstance().delete(PIC_PATH);
        boolean audio = FileUtil.getInstance().delete(AUDIO_PATH);
        clearCookies(this);
        if (pic && audio)
            ToastUtil.show("删除成功");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    public void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }
}
