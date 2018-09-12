package com.xywg.pm.projectmanager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.base.BaseActivity;
import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.presenter.ForgetPassPresenter;
import com.xywg.pm.projectmanager.presenter.IForgetPassPresenter;
import com.xywg.pm.projectmanager.view.MyHeadView;

@Route(path = RouterPath.ROUTER_FORGET_PASS)
public class ForgetPassActivity extends BaseActivity<ForgetPassPresenter> implements View.OnClickListener, IForgetPassPresenter.IForgetPassView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected ForgetPassPresenter createPresenter() {
        return new ForgetPassPresenter(this);
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
    }

    @Override
    protected void init() {

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
}
