package com.xywg.pm.projectmanager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.base.BaseActivity;
import com.example.common.widget.view.RTextView;
import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.presenter.IRegisterPresenter;
import com.xywg.pm.projectmanager.presenter.RegisterPresenter;
import com.xywg.pm.projectmanager.view.MyHeadView;

@Route(path = RouterPath.ROUTER_REGISTER)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements View.OnClickListener, IRegisterPresenter.IRegisterView {

    private RTextView mRTRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
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
        mRTRegister = findViewById(R.id.rt_next);
        mRTRegister.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rt_next) {
            ARouter.getInstance().build(RouterPath.ROUTER_PASS_RECEIVE_CODE).navigation();
        }
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
