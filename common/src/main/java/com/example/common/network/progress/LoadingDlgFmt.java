package com.example.common.network.progress;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.R;
import com.example.common.dialog.base_dialog.BaseDialogFragment;


/**
 * 正在加载对话框
 */
public class LoadingDlgFmt extends BaseDialogFragment {
    private TextView tv_msg;

    private String mMessage;
    private OnCancelCallback mCancelCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_dialog_loading;
    }

    @Override
    public void bindView(View v) {
        initUI(v);
    }

    private void initUI(View view) {
        tv_msg = view.findViewById(R.id.tv_loading_msg);
        if (!TextUtils.isEmpty(mMessage)) {
            tv_msg.setText(mMessage);
        }
        if (TextUtils.isEmpty(tv_msg.getText().toString())) {
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_msg.setVisibility(View.VISIBLE);
        }
        setVisibleAlpha(0);
    }

    /**
     * 设置加载提示
     *
     * @param message 加载提示
     */
    public void setMessage(String message) {
        this.mMessage = message;
    }

    /**
     * 设置取消 dialog 时的回调
     *
     * @param callback 取消 dialog 时的回调
     */
    public void setCancelCallback(OnCancelCallback callback) {
        this.mCancelCallback = callback;
    }

    /**
     * 显示 dialog
     *
     * @param activity activity
     */
    public void show(FragmentActivity activity) {
        // 使用此方式显示 dialog，是防止在 onSaveInstanceState 后执行了 commit 抛出异常
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.add(this, getClass().getName());
        // 注意这里使用commitAllowingStateLoss()
        ft.commitAllowingStateLoss();
//        show(activity.getSupportFragmentManager(), "loading");
    }

    /**
     * 关闭 dialog
     */
    public void close() {
        dismissAllowingStateLoss();
    }

    /**
     * 取消 dialog 时的回调
     */
    public interface OnCancelCallback {
        void onCancel();
    }
}
