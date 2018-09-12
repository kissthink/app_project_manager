package com.xywg.pm.projectmanager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.base.BaseActivity;
import com.example.common.base.BasePresenter;
import com.example.common.sutils.utils.HandlerUtil;
import com.xywg.pm.projectmanager.constant.RouterPath;

@Route(path = RouterPath.ROUTER_LAUNCH)
public class LaunchActivity extends BaseActivity {

    private static long LAUNCH_STAY_TIME = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exitLaunch();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void init() {

    }

    private void exitLaunch() {
        HandlerUtil.getInstance().getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build(RouterPath.ROUTER_LOGIN).navigation();
                LaunchActivity.this.finish();
            }
        }, LAUNCH_STAY_TIME);
    }

}
