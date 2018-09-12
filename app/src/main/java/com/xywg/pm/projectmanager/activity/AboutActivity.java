package com.xywg.pm.projectmanager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.base.BaseActivity;
import com.example.common.base.BasePresenter;
import com.example.common.sutils.utils.AppUtil;
import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.view.MyHeadView;

/**
 * Created by zhangyinlei on 2018/7/17
 */
@Route(path = RouterPath.ROUTER_ABOUT)
public class AboutActivity extends BaseActivity {

    private TextView mTvVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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
        mTvVersion = findViewById(R.id.version_info);
    }

    @Override
    protected void init() {
        String versionName = AppUtil.getAppInfo().versionName;
        mTvVersion.setText("v" + versionName);
    }

}
