package com.example.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.common.dialog.LoadingDialog;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {

    public FragmentManager fragmentManager;
    protected P presenter;
    protected LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        presenter = createPresenter();
        mLoadingDialog = new LoadingDialog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(false)
                .setCancelableOutSide(false)
                .create();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        findViews();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
        if (imm.isActive()) {//如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }*/
    }

    protected abstract P createPresenter();

    protected abstract void findViews();

    protected abstract void init();

}
