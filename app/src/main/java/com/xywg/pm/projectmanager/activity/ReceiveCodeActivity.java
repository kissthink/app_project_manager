package com.xywg.pm.projectmanager.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.base.BaseActivity;
import com.example.common.widget.view.RTextView;
import com.xywg.pm.projectmanager.R;
import com.xywg.pm.projectmanager.constant.RouterPath;
import com.xywg.pm.projectmanager.presenter.IReceiveCodePresenter;
import com.xywg.pm.projectmanager.presenter.ReceiveCodePresenter;
import com.xywg.pm.projectmanager.view.MyHeadView;

@Route(path = RouterPath.ROUTER_PASS_RECEIVE_CODE)
public class ReceiveCodeActivity extends BaseActivity<ReceiveCodePresenter> implements View.OnClickListener, IReceiveCodePresenter.IReceiveCodeView {

    private RTextView mRTRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_code);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rt_register) {

        }
    }

    @Override
    protected ReceiveCodePresenter createPresenter() {
        return new ReceiveCodePresenter(this);
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
        mRTRegister = findViewById(R.id.rt_register);
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
